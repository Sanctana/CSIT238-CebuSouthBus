package edu.cit.tangpos.bustransport.fragments

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import edu.cit.tangpos.bustransport.R
import edu.cit.tangpos.bustransport.Utility
import edu.cit.tangpos.bustransport.database.DBHelper
import androidx.core.content.edit

class ProfileEdit : Fragment(R.layout.fragment_profile_edit) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etFullName = view.findViewById<EditText>(R.id.etFullName)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etCurrentPassword = view.findViewById<EditText>(R.id.etCurrentPassword)
        val etNewPassword = view.findViewById<EditText>(R.id.etNewPassword)
        val etConfirmPassword = view.findViewById<EditText>(R.id.etConfirmPassword)
        val btnSave = view.findViewById<TextView>(R.id.btnSave)

        val sharedPreferences = requireContext().getSharedPreferences(Utility.APP_SHARED_PREFERENCES, MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)

        if (userId == null){
            requireActivity().finish()
            return
        }

        val writableDatabase = DBHelper(requireContext()).writableDatabase

        loadUserData(view)

        btnSave.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val currentPw = etCurrentPassword.text.toString().trim()
            val newPw = etNewPassword.text.toString().trim()
            val confirmPw = etConfirmPassword.text.toString().trim()

            val values = ContentValues()
            if (email.isNotEmpty()) values.put(DBHelper.USERS_EMAIL, email)

            // Handle password updates if a current password is provided
            if (currentPw.isNotEmpty()) {
                val storedPw = writableDatabase.rawQuery(
                    "SELECT ${DBHelper.USERS_PASSWORD} FROM ${DBHelper.TABLE_USERS} WHERE ${DBHelper.USERS_ID} = ?",
                    arrayOf(userId)
                ).use { if (it.moveToFirst()) it.getString(0) else null }

                if (storedPw == null) {
                    sharedPreferences.edit { remove("userId") }
                    requireActivity().finish()
                    return@setOnClickListener
                }

                if (Utility.hashPassword(currentPw) != storedPw) {
                    Toast.makeText(requireContext(), "Incorrect current password", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (newPw.isNotEmpty()) {
                    if (newPw != confirmPw) {
                        Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    values.put(DBHelper.USERS_PASSWORD, Utility.hashPassword(newPw))
                }
            }

            if (values.size() == 0) {
                Toast.makeText(requireContext(), "No changes to save", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val success = writableDatabase.update(DBHelper.TABLE_USERS, values, "${DBHelper.USERS_ID} = ?", arrayOf(userId)) > 0
            if (success) {
                loadUserData(view)
                Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Update failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadUserData(view: View){
        val etFullName = view.findViewById<EditText>(R.id.etFullName)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val sharedPreferences = requireContext().getSharedPreferences(Utility.APP_SHARED_PREFERENCES, MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null) ?: return

        DBHelper(requireContext()).readableDatabase.rawQuery(
            "SELECT * FROM ${DBHelper.TABLE_USERS} WHERE ${DBHelper.USERS_ID} = ?",
            arrayOf(userId)
        ).use {
            if (it.moveToFirst()){
                val firstName = it.getString(it.getColumnIndexOrThrow(DBHelper.USERS_FIRST_NAME))
                val middleName = it.getString(it.getColumnIndexOrThrow(DBHelper.USERS_MIDDLE_NAME))
                val lastName = it.getString(it.getColumnIndexOrThrow(DBHelper.USERS_LAST_NAME))

                etFullName.setText("$firstName $middleName $lastName")
                etEmail.setText(it.getString(it.getColumnIndexOrThrow(DBHelper.USERS_EMAIL)))
            }
        }
    }
}