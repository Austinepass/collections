package com.example.task5


import androidx.core.util.PatternsCompat

/**
 * Phone number validation using Regex to check if the number is
 * starting with 0 or 234, as well as the remaining digits of
 * 8
 */
fun validNumber(number: String): Boolean {
    val reg = """(^[0][7-9][0-1]\d{8}$)|(^234[7-9][0-1]\d{8}$)""".toRegex()
    return reg.matches(number)
}

/**
 * Android system regex for validating email addresses
 */
fun validEmail(email: String): Boolean {
    return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
}