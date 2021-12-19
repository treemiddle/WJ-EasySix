package com.example.sample.utils

enum class MarkerButtonType {
    NON_SELECTED,
    AREA_A_SELECTED,
    AREA_B_SELECTED,
    BOTH_SELECTED
}

enum class CurrentTextType {
    V_TEXT,
    SET_B_TEXT,
    BOOK_TEXT
}

enum class MapLabelClick {
    EMPTY,
    LABEL_A,
    LABEL_B,
    BOOK,
    LAST_SCREEN
}

enum class LabelType {
    A,
    B
}

enum class StackManager {
    NORMAL,
    MAP_TO_MAP,
    INFO_TO_MAP,
    BOOK_TO_MAP
}

fun labelTypeToString(type: LabelType): String {
    return when (type) {
        LabelType.A -> "A"
        LabelType.B -> "B"
    }
}

fun stringToLabelType(string: String): LabelType {
    return when (string) {
        "A" -> LabelType.A
        "B" -> LabelType.B
        else -> LabelType.A
    }
}