package com.example.sudoku.solver.ui

enum class AlterAffectedRowOperation {
    SET_AFFECTED_ROW, UNSET_AFFECTED_ROW
}

enum class AlterAffectedColumnOperation {
    SET_AFFECTED_COLUMN, UNSET_AFFECTED_COLUMN
}

enum class AlterAffectedSquareOperation {
    SET_AFFECTED_SQUARE, UNSET_AFFECTED_SQUARE
}

enum class SquareType {
    NORMAL_SQUARE, SQUARE_SELECTED, SQUARE_AFFECTED
}