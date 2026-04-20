package edu.cit.tangpos.bustransport.fragments.account

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.cit.tangpos.bustransport.R
import edu.cit.tangpos.bustransport.Utility
import edu.cit.tangpos.bustransport.database.DBHelper

class ProfileView : Fragment(R.layout.fragment_profile_view) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvFullName = view.findViewById<TextView>(R.id.tvFullName)
        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
        val tvUsername = view.findViewById<TextView>(R.id.tvUserName)

        val sharedPreferences = requireContext().getSharedPreferences(Utility.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)

        if (userId == null){
            requireActivity().finish()
            return
        }


        DBHelper(requireContext()).readableDatabase.rawQuery("SELECT * FROM ${DBHelper.TABLE_USERS} WHERE ${DBHelper.USERS_ID} = ?", arrayOf(userId)).use { cursor ->
            if (cursor.moveToFirst()){
                tvEmail.text = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USERS_EMAIL))

                val firstName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USERS_FIRST_NAME))
                val middleName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USERS_MIDDLE_NAME))
                val lastName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USERS_LAST_NAME))
                tvFullName.text = "$firstName $middleName $lastName"
                tvUsername.text = "$firstName $middleName $lastName"
            }
        }

        // Handle the back button inside the About Us layout
        view.findViewById<View>(R.id.btnBack).setOnClickListener {
            findNavController().popBackStack()
        }
        view.findViewById<TextView>(R.id.btnEdit).setOnClickListener {
            findNavController().navigate(R.id.nav_profile_edit)
        }
    }
}