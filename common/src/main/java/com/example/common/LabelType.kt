package com.example.common

enum class LabelType {
    A,
    B
}

fun labelTypeToString(type: LabelType): String {
    return when (type) {
        LabelType.A -> "A"
        LabelType.B -> "B"
    }
}

fun stringToLabelType(string: String?): LabelType {
    return when (string) {
        "A" -> LabelType.A
        "B" -> LabelType.B
        else -> LabelType.A
    }
}