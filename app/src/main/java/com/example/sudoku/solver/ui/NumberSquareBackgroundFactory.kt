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
                                    .getDrawable(context, R.drawable.num_sq_top_left_bg)
                                1, 2, 4, 5, 7 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_middle_col__right_col__top_bg
                                    )
                                8 -> ContextCompat
                                    .getDrawable(context, R.drawable.num_sq_bottom_left_bg)
                                else -> null
                            }
                        1, 2, 4, 5, 7 ->
                            when (rowIndex) {
                                0, 3, 6 -> ContextCompat
                                    .getDrawable(context, R.drawable.num_sq_top__middle_right_bg)
                                1, 2, 4, 5, 7 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_middle_col__right_col_bg
                                    )
                                8 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_middle_col__right_col__bottom_bg
                                    )
                                else -> null
                            }
                        8 -> {
                            when (rowIndex) {
                                0, 3, 6 -> ContextCompat
                                    .getDrawable(context, R.drawable.num_sq_top_right_bg)
                                1, 2, 4, 5, 7 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_middle_col__right_col_right_bg
                                    )
                                8 -> ContextCompat
                                    .getDrawable(context, R.drawable.num_sq_bottom_right_bg)
                                else -> null
                            }
                        }
                        else -> null
                    }
                }
                SquareType.SQUARE_SELECTED ->
                    when (columnIndex) {
                        0, 3, 6 ->
                            when (rowIndex) {
                                0, 3, 6 -> ContextCompat
                                    .getDrawable(context, R.drawable.num_sq_top_left_bg_selected)
                                1, 2, 4, 5, 7 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_middle_col__right_col__top_bg_selected
                                    )
                                8 -> ContextCompat
                                    .getDrawable(context, R.drawable.num_sq_bottom_left_bg_selected)
                                else -> null
                            }
                        1, 2, 4, 5, 7 ->
                            when (rowIndex) {
                                0, 3, 6 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_top__middle_right_bg_selected
                                    )
                                1, 2, 4, 5, 7 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_middle_col__right_col_bg_selected
                                    )
                                8 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_middle_col__right_col__bottom_bg_selected
                                    )
                                else -> null
                            }
                        8 -> {
                            when (rowIndex) {
                                0, 3, 6 -> ContextCompat
                                    .getDrawable(context, R.drawable.num_sq_top_right_bg_selected)
                                1, 2, 4, 5, 7 -> ContextCompat
                                    .getDrawable(
                                        context,
                                        R.drawable.num_sq_middle_col__right_col_right_bg_selected
                                    )
                                8 -> ContextCompat
                                    .getDrawable(context, R.drawable.num_sq_bottom_right_bg_selected)
                                else -> null
                            }
                        }
                        else -> null
                    }
                SquareType.SQUARE_AFFECTED ->
                    when (columnIndex) {
                        0, 3, 6 ->
                            when (rowIndex) {
                                0, 3, 6 -> ContextCompat
                                    .getDrawable(context, R.drawable.num_sq_top_left_bg_affected)
                                1, 2, 4, 5, 7 -> ContextCompat
                                    .getDrawable(context,
                                        R.drawable.num_sq_middle_col__right_col__top_bg_affected)
                                8 -> ContextCompat
                                    .getDrawable(context, R.drawable.num_sq_bottom_left_bg_affected)
                                else -> null
                            }
                        1, 2, 4, 5, 7 ->
                            when (rowIndex) {
                                0, 3, 6 -> ContextCompat
                                    .getDrawable(context, R.drawable.num_sq_top__middle_right_bg_affected)
                                1, 2, 4, 5, 7 -> ContextCompat
                                    .getDrawable(context,
                                        R.drawable.num_sq_middle_col__right_col_bg_affected)
                                8 -> ContextCompat
                                    .getDrawable(context,
                                        R.drawable.num_sq_middle_col__right_col__bottom_bg_affected)
                                else -> null
                            }
                        8 -> {
                            when (rowIndex) {
                                0, 3, 6 -> ContextCompat
                                    .getDrawable(context, R.drawable.num_sq_top_right_bg_affected)
                                1, 2, 4, 5, 7 -> ContextCompat
                                    .getDrawable(context,
                                        R.drawable.num_sq_middle_col__right_col_right_bg_affected)
                                8 -> ContextCompat
                                    .getDrawable(context, R.drawable.num_sq_bottom_right_bg_affected)
                                else -> null
                            }
                        }
                        else -> null
                    }
            }
    }
}