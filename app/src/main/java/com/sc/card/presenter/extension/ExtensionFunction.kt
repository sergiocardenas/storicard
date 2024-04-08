package com.sc.card.presenter.extension

import android.text.TextUtils
import java.util.regex.Pattern

val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)
fun String.validateEmail(): Boolean{
    if (TextUtils.isEmpty(this)) {
        return false;
    } else {
        return EMAIL_ADDRESS_PATTERN.matcher(this).matches();
    }
}