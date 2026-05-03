package edu.cit.tangpos.bustransport.utilities

import android.app.Activity
import android.widget.TextView
import android.widget.Toast

fun Activity.getTextValue(id: Int): String {
    return findViewById<TextView>(id).text.toString()
}

fun Activity.setTextValue(id: Int, text: String) {
    findViewById<TextView>(id).text = text
}

fun Activity.showShortToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
