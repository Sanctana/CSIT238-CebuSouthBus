package edu.cit.tangpos.bustransport.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.cit.tangpos.bustransport.R
import androidx.core.content.edit
import edu.cit.tangpos.bustransport.database.DBHelper

class AccountFragment : Fragment(R.layout.fragment_account) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailField = view.findViewById<TextView>(R.id.account_email)
        val usernameField = view.findViewById<TextView>(R.id.username)

        val btnLogOut = view.findViewById<LinearLayout>(R.id.btnLogOut)
        val btnAboutUs = view.findViewById<LinearLayout>(R.id.btnAboutUs)

        btnAboutUs.setOnClickListener {
            findNavController().navigate(R.id.nav_about_us)
        }

        val sharedPreferences = requireContext().getSharedPreferences("Bus Transport", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)

        if (userId == null){
            requireActivity().finish()
            return
        }

        val db = DBHelper(requireContext()).readableDatabase

        db.rawQuery("SELECT * FROM ${DBHelper.TABLE_USERS} WHERE ${DBHelper.USERS_ID} = ?", arrayOf(userId)).use { cursor ->
            if (cursor.moveToFirst()){
                emailField.text = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USERS_EMAIL))

                val firstName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USERS_FIRST_NAME))
                val middleName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USERS_MIDDLE_NAME))
                val lastName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USERS_LAST_NAME))
                usernameField.text = "$firstName $middleName $lastName"
            }
        }

        btnLogOut.setOnClickListener {
            sharedPreferences.edit { remove("userId") }
            requireActivity().finish() // Finish the current activity so it redirects to LoginActivity
        }
    }
}