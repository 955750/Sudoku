package com.example.sudoku.solver.ui

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.sudoku.R

class NumberSquareBackgroundFactory {

    companion object {
        fun createDrawableFromSquareTypeAndCoordinates(
            squareType: SquareType,
            rowIndex: Int,
            columnIndex: Int,
            context: Context
        ): Drawable? =
            when (squareType) {
                SquareType.NORMAL_SQUARE -> {
                    when (columnIndex) {
                        0, 3, 6 ->
                            when (rowIndex) {
                                0, 3, 6 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_border__black_top_left_bg
                                    )
                                1, 2, 4, 5, 7 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_border__black_left__grey_top_bg
                                    )
                                8 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_border__black_left_bottom__grey_top_bg
                                    )
                                else -> null
                            }
                        1, 2, 4, 5, 7 ->
                            when (rowIndex) {
                                0, 3, 6 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_border__black_top__grey_left_bg
                                    )
                                1, 2, 4, 5, 7 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_border__grey_top_left_bg
                                    )
                                8 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_border__black_bottom__grey_top_left_bg
                                    )
                                else -> null
                            }
                        8 ->
                            when (rowIndex) {
                                0, 3, 6 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_border__black_top_right__grey_left_bg
                                    )
                                1, 2, 4, 5, 7 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_border__black_right__grey_top_left_bg
                                    )
                                8 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable
                                            .num_sq_border__black_bottom_right__grey_top_left_bg
                                    )
                                else -> null
                            }
                        else -> null
                    }
                }
                SquareType.SELECTED_SQUARE ->
                    when (columnIndex) {
                        0, 3, 6 ->
                            when (rowIndex) {
                                0, 3, 6 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_border__black_top_left_bg_selected
                                    )
                                1, 2, 4, 5, 7 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_border__black_left__grey_top_bg_selected
                                    )
                                8 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable
                                            .num_sq_border__black_left_bottom__grey_top_bg_selected
                                    )
                                else -> null
                            }
                        1, 2, 4, 5, 7 ->
                            when (rowIndex) {
                                0, 3, 6 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_border__black_top__grey_left_bg_selected
                                    )
                                1, 2, 4, 5, 7 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_border__grey_top_left_bg_selected
                                    )
                                8 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable
                                            .num_sq_border__black_bottom__grey_top_left_bg_selected
                                    )
                                else -> null
                            }
                        8 ->
                            when (rowIndex) {
                                0, 3, 6 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable
                                            .num_sq_border__black_top_right__grey_left_bg_selected
                                    )
                                1, 2, 4, 5, 7 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable
                                            .num_sq_border__black_right__grey_top_left_bg_selected
                                    )
                                8 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable
                                            .num_sq_border__black_bottom_right__grey_top_left_bg_selected
                                    )
                                else -> null
                            }
                        else -> null
                    }
                SquareType.AFFECTED_SQUARE ->
                    when (columnIndex) {
                        0, 3, 6 ->
                            when (rowIndex) {
                                0, 3, 6 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_border__black_top_left_bg_affected
                                    )
                                1, 2, 4, 5, 7 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_border__black_left__grey_top_bg_affected
                                    )
                                8 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable
                                            .num_sq_border__black_left_bottom__grey_top_bg_affected
                                    )
                                else -> null
                            }
                        1, 2, 4, 5, 7 ->
                            when (rowIndex) {
                                0, 3, 6 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_border__black_top__grey_left_bg_affected
                                    )
                                1, 2, 4, 5, 7 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_border__grey_top_left_bg_affected
                                    )
                                8 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable
                                            .num_sq_border__black_bottom__grey_top_left_bg_affected
                                    )
                                else -> null
                            }
                        8 ->
                            when (rowIndex) {
                                0, 3, 6 -> ContextCompat.getDrawable(
                                        context,
                                        R.drawable
                                            .num_sq_border__black_top_right__grey_left_bg_affected
                                )
                                1, 2, 4, 5, 7 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable
                                            .num_sq_border__black_right__grey_top_left_bg_affected
                                    )
                                8 -> ContextCompat.getDrawable(
                                        context,
                                        R.drawable
                                            .num_sq_border__black_bottom_right__grey_top_left_bg_affected
                                    )
                                else -> null
                            }
                        else -> null
                    }
                SquareType.ROW_CLUE_SQUARE_TOP ->
                    when (columnIndex) {
                        0 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top_left_bg
                        )
                        1, 2, 4, 5, 7 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top__grey_left_bg
                        )
                        3, 6 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top__black_left_bg
                        )
                        8 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top_right__grey_left_bg
                        )
                        else -> null
                    }
                SquareType.ROW_CLUE_SQUARE_BOTTOM ->
                    when (columnIndex) {
                        0, 3, 6 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top__black_left_bg
                        )
                        1, 2, 4, 5, 7 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top__grey_left_bg
                        )
                        8 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top__grey_left__black_right_bg
                        )
                        else -> null
                    }
                SquareType.ROW_CLUE_SQUARE_LAST_ROW ->
                    when (columnIndex) {
                        0 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top_bottom_left_bg
                        )
                        1, 2, 4, 5, 7 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top_bottom__grey_left_bg
                        )
                        3, 6 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top_bottom__black_left_bg
                        )
                        8 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top_bottom_right__grey_left_bg
                        )
                        else -> null
                    }
                SquareType.HIGHLIGHTED_ORANGE_SQUARE_ROW_TOP ->
                    when (columnIndex) {
                        0 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top_left_bg__orange_highlighted
                        )
                        1, 2, 4, 5, 7 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top__grey_left_bg__orange_highlighted
                        )
                        3, 6 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top__black_left_bg__orange_highlighted
                        )
                        8 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top_right__grey_left_bg__orange_highlighted
                        )
                        else -> null
                    }
                SquareType.HIGHLIGHTED_ORANGE_SQUARE_LAST_ROW ->
                    when (columnIndex) {
                        0 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top_bottom_left_bg__orange_highlighted
                        )
                        1, 2, 4, 5, 7 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top_bottom__grey_left_bg__orange_highlighted
                        )
                        3, 6 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top_bottom__black_left_bg__orange_highlighted
                        )
                        8 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top_bottom_right__grey_left_bg__orange_highlighted
                        )
                        else -> null
                    }
                SquareType.COLUMN_CLUE_SQUARE_LEFT ->
                    when (rowIndex) {
                        0 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top_left_bg
                        )
                        1, 2, 4, 5, 7 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_left__grey_top_bg
                        )
                        3, 6 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_left__black_top_bg
                        )
                        8 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_bottom_left__grey_top_bg
                        )
                        else -> null
                    }
                SquareType.COLUMN_CLUE_SQUARE_RIGHT ->
                    when (rowIndex) {
                        0, 3, 6 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_left__black_top_bg
                        )
                        1, 2, 4, 5, 7 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_left__grey_top_bg
                        )
                        8 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_left__black_bottom__grey_top_bg
                        )
                        else -> null
                    }
                SquareType.COLUMN_CLUE_SQUARE_LAST_COLUMN ->
                    when (rowIndex) {
                        0 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top_left_right_bg
                        )
                        1, 2, 4, 5, 7 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_left_right__grey_top_bg
                        )
                        3, 6 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_left_right__black_top_bg
                        )
                        8 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_bottom_left_right__grey_top_bg
                        )
                        else -> null
                    }
                SquareType.HIGHLIGHTED_ORANGE_SQUARE_COLUMN_LEFT ->
                    when (rowIndex) {
                        0 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top_left_bg__orange_highlighted
                        )
                        1, 2, 4, 5, 7 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_left__grey_top_bg__orange_highlighted
                        )
                        3, 6 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_left__black_top_bg__orange_highlighted
                        )
                        8 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_bottom_left__grey_top_bg__orange_highlighted
                        )
                        else -> null
                    }
                SquareType.HIGHLIGHTED_ORANGE_SQUARE_LAST_COLUMN ->
                    when (rowIndex) {
                        0 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_top_left_right_bg__orange_highlighted
                        )
                        1, 2, 4, 5, 7 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_left_right__grey_top_bg__orange_highlighted
                        )
                        3, 6 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_left_right__black_top_bg__orange_highlighted
                        )
                        8 -> ContextCompat.getDrawable(
                            context,
                            R.drawable.num_sq_border__darkblue_bottom_left__grey_top_bg__orange_highlighted
                        )
                        else -> null
                    }
            }
    }
}