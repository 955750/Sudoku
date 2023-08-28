package com.example.sudoku.solver.ui

enum class AlterRowBackgroundOperation {
    SET_AFFECTED_ROW,
    UNSET_AFFECTED_ROW,
    SET_CLUE_ROW_TOP,
    SET_CLUE_ROW_BOTTOM,
    SET_LAST_CLUE_ROW
}

enum class AlterAffectedColumnOperation {
    SET_AFFECTED_COLUMN,
    UNSET_AFFECTED_COLUMN
}

enum class AlterAffectedSquareOperation {
    SET_AFFECTED_SQUARE,
    UNSET_AFFECTED_SQUARE
}

enum class SquareType {
    NORMAL_SQUARE,
    SELECTED_SQUARE,
    AFFECTED_SQUARE,
    ROW_CLUE_SQUARE_TOP,
    ROW_CLUE_SQUARE_BOTTOM,
    ROW_CLUE_SQUARE_LAST_ROW,
    HIGHLIGHTED_ORANGE_SQUARE_TOP,
    HIGHLIGHTED_ORANGE_SQUARE_LAST_ROW
}