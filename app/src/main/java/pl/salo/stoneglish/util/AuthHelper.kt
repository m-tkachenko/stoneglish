package pl.salo.stoneglish.util

import android.text.TextUtils

class AuthHelper

fun isValidEmail(email: String): Boolean {
    return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email)
        .matches()
}

fun isValidPassword(password: String): Boolean {
    return password.length >= 8
}