package dev.claucookielabs.pasbuk.common.presentation.utils

import android.os.Build
import android.os.Build.VERSION


fun isAtLeastM(): Boolean {
    return VERSION.SDK_INT >= Build.VERSION_CODES.M
}


fun isAtLeastN(): Boolean {
    return VERSION.SDK_INT >= Build.VERSION_CODES.N
}


fun isAtLeastO(): Boolean {
    return VERSION.SDK_INT >= Build.VERSION_CODES.O
}


fun isAtLeastP(): Boolean {
    return VERSION.SDK_INT >= Build.VERSION_CODES.P
}
