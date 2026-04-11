package edu.cit.tangpos.bustransport

import java.security.MessageDigest

object Utility {
    const val APP_SHARED_PREFERENCES = "Bus Transport"

    fun hashPassword(password: String): String {
        return MessageDigest.getInstance("SHA-256").digest(password.toByteArray()).joinToString("") { "%02x".format(it) }
    }
}