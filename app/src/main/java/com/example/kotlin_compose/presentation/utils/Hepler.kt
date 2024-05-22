package com.example.kotlin_compose.presentation.utils

import android.util.Log


fun isEmpty(value: Any?): Boolean {
    val identifier = "[IsEmpty Checking]"
    Log.i(
        identifier,
        "${DebuggingIdentifiers.actionOrEventSucceded} value: $value is empty",
    )
    if (value == null) return true
    return when (value) {
        is String -> value.isEmpty()
        is Collection<*> -> value.isEmpty()
        else -> false
    }
}
fun getButtonProps(
    variant: ButtonVariant = ButtonVariant.SOLID,
    color: ButtonColor = ButtonColor.DEFAULT,
    size: ButtonSize = ButtonSize.MD,
    radius: ButtonRadius = ButtonRadius.NONE
): Map<String, String> {

    val props = mutableMapOf<String, String>()

    props["variant"] = variant.name
    props["color"] = color.name
    props["size"] = size.name
    props["radius"] = radius.name

    return props.toMap()
}