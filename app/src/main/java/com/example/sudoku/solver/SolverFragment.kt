package com.example.sudoku.solver

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.sudoku.R
import com.example.sudoku.databinding.FragmentSolverBinding


class SolverFragment : Fragment() {

    private lateinit var binding: FragmentSolverBinding
    private val solverViewModel: SolverViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSolverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createSudokuGrid()
    }

    private fun createSudokuGrid() {
        for (rowIndex in 0..8) {
            val sudokuRow = layoutInflater.inflate(
                R.layout.sudoku_row_layout,
                binding.sudokuGrid,
                false
            ) as LinearLayout
            binding.sudokuGrid.addView(sudokuRow)
            for (columnIndex in 0..8) {
                val numberSquare = layoutInflater.inflate(
                    R.layout.number_square_layout,
                    binding.sudokuGrid,
                    false
                ) as TextView
                numberSquare.text = solverViewModel.sudokuNumberGrid[rowIndex][columnIndex]
                    .toString()
                numberSquare.setOnClickListener {
                    onSquareClicked(
                        selectedSquareInstance = it as TextView,
                        selectedRowIndex = rowIndex,
                        selectedColumnIndex = columnIndex
                    )
                }
                setNotSelectedNumberSquareColor(
                    numberSquare = numberSquare,
                    selectedRowIndex = rowIndex,
                    selectedColumnIndex = columnIndex
                )
                sudokuRow.addView(numberSquare)
            }
        }
    }

    private fun onSquareClicked(
        selectedSquareInstance: TextView,
        selectedRowIndex: Int,
        selectedColumnIndex: Int
    ) {
        if (solverViewModel.areCoordinatesInitialised()) {
            val previousSelectedSquareCoordinates = solverViewModel
                .lastSelectedSquareCoordinates.value!!
            val lastSelectedRow = binding.sudokuGrid.getChildAt(
                previousSelectedSquareCoordinates.first
            ) as LinearLayout
            val lastSelectedSquare = lastSelectedRow.getChildAt(
                previousSelectedSquareCoordinates.second
            ) as TextView
            setNotSelectedNumberSquareColor(
                numberSquare = lastSelectedSquare,
                selectedRowIndex = previousSelectedSquareCoordinates.first,
                selectedColumnIndex = previousSelectedSquareCoordinates.second
            )
            unsetAffectedNumberSquaresColor(
                selectedRowIndex = previousSelectedSquareCoordinates.first,
                selectedColumnIndex = previousSelectedSquareCoordinates.second
            )
        }
        setSelectedNumberSquareColor(
            numberSquare = selectedSquareInstance,
            selectedRowIndex = selectedRowIndex,
            selectedColumnIndex = selectedColumnIndex
        )
        setAffectedNumberSquaresColor(
            selectedRowIndex = selectedRowIndex,
            selectedColumnIndex = selectedColumnIndex
        )
        solverViewModel.updateLastSelectedSquareCoordinates(
            row = selectedRowIndex,
            column = selectedColumnIndex
        )
        Log.d(TAG, "row = $selectedRowIndex, column = $selectedColumnIndex")
    }

    private fun setSelectedNumberSquareColor(
        numberSquare: TextView,
        selectedRowIndex: Int,
        selectedColumnIndex: Int
    ) {
        when (selectedColumnIndex) {
            0, 3, 6 -> {
                when (selectedRowIndex) {
                    0, 3, 6 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_top_left_bg_selected)
                    1, 2, 4, 5, 7 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col__top_bg_selected)
                    8 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_bottom_left_bg_selected)
                }
            }
            1, 2, 4, 5, 7 -> {
                when (selectedRowIndex) {
                    0, 3, 6 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_top__middle_right_bg_selected)
                    1, 2, 4, 5, 7 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col_bg_selected)
                    8 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col__bottom_bg_selected)
                }
            }
            8 -> {
                when (selectedRowIndex) {
                    0, 3, 6 ->  numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_top_right_bg_selected)
                    1, 2, 4, 5, 7 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col_right_bg_selected)
                    8 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_bottom_right_bg_selected)
                }
            }
        }
    }

    private fun setNotSelectedNumberSquareColor(
        numberSquare: TextView,
        selectedRowIndex: Int,
        selectedColumnIndex: Int
    ) {
        when (selectedColumnIndex) {
            0, 3, 6 -> {
                when (selectedRowIndex) {
                    0, 3, 6 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_top_left_bg)
                    1, 2, 4, 5, 7 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col__top_bg)
                    8 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_bottom_left_bg)
                }
            }
            1, 2, 4, 5, 7 -> {
                when (selectedRowIndex) {
                    0, 3, 6 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_top__middle_right_bg)
                    1, 2, 4, 5, 7 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col_bg)
                    8 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col__bottom_bg)
                }
            }
            8 -> {
                when (selectedRowIndex) {
                    0, 3, 6 ->  numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_top_right_bg)
                    1, 2, 4, 5, 7 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col_right_bg)
                    8 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_bottom_right_bg)
                }
            }
        }
    }

    private fun setAffectedNumberSquaresColor(
        selectedRowIndex: Int,
        selectedColumnIndex: Int
    ) {
        alterAffectedRow(
            selectedRowIndex = selectedRowIndex,
            selectedColumnIndex = selectedColumnIndex,
            alterAffectedRowOperation = AlterAffectedRowOperation.SET_AFFECTED_ROW
        )
        alterAffectedColumn(
            selectedRowIndex = selectedRowIndex,
            selectedColumnIndex = selectedColumnIndex,
            alterAffectedColumnOperation = AlterAffectedColumnOperation.SET_AFFECTED_COLUMN
        )
        alterAffectedSquare(
            selectedRowIndex = selectedRowIndex,
            selectedColumnIndex = selectedColumnIndex,
            alterAffectedSquareOperation = AlterAffectedSquareOperation.SET_AFFECTED_SQUARE
        )
    }

    private fun unsetAffectedNumberSquaresColor(
        selectedRowIndex: Int,
        selectedColumnIndex: Int
    ) {
        alterAffectedRow(
            selectedRowIndex = selectedRowIndex,
            selectedColumnIndex = selectedColumnIndex,
            alterAffectedRowOperation = AlterAffectedRowOperation.UNSET_AFFECTED_ROW
        )
        alterAffectedColumn(
            selectedRowIndex = selectedRowIndex,
            selectedColumnIndex = selectedColumnIndex,
            alterAffectedColumnOperation = AlterAffectedColumnOperation.UNSET_AFFECTED_COLUMN
        )
        alterAffectedSquare(
            selectedRowIndex = selectedRowIndex,
            selectedColumnIndex = selectedColumnIndex,
            alterAffectedSquareOperation = AlterAffectedSquareOperation.UNSET_AFFECTED_SQUARE
        )
    }

    private fun setAffectedNumberSquareColor(
        numberSquare: TextView,
        rowIndex: Int,
        columnIndex: Int
    ) {
        when (columnIndex) {
            0, 3, 6 -> {
                when (rowIndex) {
                    0, 3, 6 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_top_left_bg_affected)
                    1, 2, 4, 5, 7 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col__top_bg_affected)
                    8 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_bottom_left_bg_affected)
                }
            }
            1, 2, 4, 5, 7 -> {
                when (rowIndex) {
                    0, 3, 6 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_top__middle_right_bg_affected)
                    1, 2, 4, 5, 7 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col_bg_affected)
                    8 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col__bottom_bg_affected)
                }
            }
            8 -> {
                when (rowIndex) {
                    0, 3, 6 ->  numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_top_right_bg_affected)
                    1, 2, 4, 5, 7 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col_right_bg_affected)
                    8 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_bottom_right_bg_affected)
                }
            }
        }
    }

    private fun alterAffectedRow(
        selectedRowIndex: Int,
        selectedColumnIndex: Int,
        alterAffectedRowOperation: AlterAffectedRowOperation
    ) {
        val selectedRow = binding.sudokuGrid.getChildAt(selectedRowIndex) as LinearLayout
        for (columnIndex in 0..8) {
            if (columnIndex != selectedColumnIndex) {
                val currentAffectedNumberSquare = selectedRow.getChildAt(columnIndex) as TextView
                when (alterAffectedRowOperation) {
                    AlterAffectedRowOperation.SET_AFFECTED_ROW -> {
                        setAffectedNumberSquareColor(
                            numberSquare = currentAffectedNumberSquare,
                            rowIndex = selectedRowIndex,
                            columnIndex = columnIndex
                        )
                    }
                    AlterAffectedRowOperation.UNSET_AFFECTED_ROW -> {
                        setNotSelectedNumberSquareColor(
                            numberSquare = currentAffectedNumberSquare,
                            selectedRowIndex = selectedRowIndex,
                            selectedColumnIndex = columnIndex
                        )
                    }
                }
            }
        }
    }

    private fun alterAffectedColumn(
        selectedRowIndex: Int,
        selectedColumnIndex: Int,
        alterAffectedColumnOperation: AlterAffectedColumnOperation
    ) {
        for (rowIndex in 0..8) {
            if (rowIndex != selectedRowIndex) {
                val currentRow = binding.sudokuGrid.getChildAt(rowIndex) as LinearLayout
                val currentAffectedNumberSquare = currentRow.getChildAt(selectedColumnIndex)
                    as TextView
                when (alterAffectedColumnOperation) {
                    AlterAffectedColumnOperation.SET_AFFECTED_COLUMN -> {
                        setAffectedNumberSquareColor(
                            numberSquare = currentAffectedNumberSquare,
                            rowIndex = rowIndex,
                            columnIndex = selectedColumnIndex
                        )
                    }
                    AlterAffectedColumnOperation.UNSET_AFFECTED_COLUMN -> {
                        setNotSelectedNumberSquareColor(
                            numberSquare = currentAffectedNumberSquare,
                            selectedRowIndex = rowIndex,
                            selectedColumnIndex = selectedColumnIndex
                        )
                    }
                }
            }
        }
    }

    private fun alterAffectedSquare(
        selectedRowIndex: Int,
        selectedColumnIndex: Int,
        alterAffectedSquareOperation: AlterAffectedSquareOperation
    ) {
        val affectedRowIndexesRange = getAffectedIndexRangeInSquare(
            selectedRowOrColumnIndex = selectedRowIndex
        )
        val affectedColumnIndexesRange = getAffectedIndexRangeInSquare(
            selectedRowOrColumnIndex = selectedColumnIndex
        )
        for (rowIndex in affectedRowIndexesRange.first..affectedRowIndexesRange.second) {
            for (columnIndex in
                affectedColumnIndexesRange.first..affectedColumnIndexesRange.second
            ) {
                if (rowIndex != selectedRowIndex && columnIndex != selectedColumnIndex) {
                    val currentRow = binding.sudokuGrid.getChildAt(rowIndex) as LinearLayout
                    val currentAffectedNumberSquare = currentRow.getChildAt(columnIndex)
                        as TextView
                    when (alterAffectedSquareOperation) {
                        AlterAffectedSquareOperation.SET_AFFECTED_SQUARE -> {
                            setAffectedNumberSquareColor(
                                numberSquare = currentAffectedNumberSquare,
                                rowIndex = rowIndex,
                                columnIndex = columnIndex
                            )
                        }
                        AlterAffectedSquareOperation.UNSET_AFFECTED_SQUARE -> {
                            setNotSelectedNumberSquareColor(
                                numberSquare = currentAffectedNumberSquare,
                                selectedRowIndex = rowIndex,
                                selectedColumnIndex = columnIndex
                            )
                        }
                    }

                }
            }
        }
    }

    private fun getAffectedIndexRangeInSquare(
        selectedRowOrColumnIndex: Int,
    ): Pair<Int, Int> {
        return when (selectedRowOrColumnIndex) {
            0, 1, 2 -> Pair(0, 2)
            3, 4, 5 -> Pair(3, 5)
            6, 7, 8 -> Pair(6, 8)
            else -> Pair(-1, -1)
        }
    }

    companion object {
        const val TAG = "SolverFragment"
    }
}