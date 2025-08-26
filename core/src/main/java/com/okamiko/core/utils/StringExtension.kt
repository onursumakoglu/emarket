package com.okamiko.core.utils

fun String.toTurkishPrice(): String? {
    return this.plus(" ₺")
}