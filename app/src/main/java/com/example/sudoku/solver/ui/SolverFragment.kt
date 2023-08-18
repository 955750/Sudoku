package com.example.sudoku.solver.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.sudoku.R
import com.example.sudoku.databinding.FragmentSolverBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        createNumberButtons()
        setupNextClueButton()

        solverViewModel.sudokuNumberGrid.observe(viewLifecycleOwner) {
            if (solverViewModel.areCoordinatesInitialised()) {
                val selectedNumberSquareCoordinates = solverViewModel
                    .lastSelectedSquareCoordinates.value!!
                val newNumber = it[selectedNumberSquareCoordinates
                    .first][selectedNumberSquareCoordinates.second]
                val selectedRow = binding.sudokuGrid.getChildAt(
                    selectedNumberSquareCoordinates.first
                ) as LinearLayout
                val selectedNumberSquare = selectedRow.getChildAt(
                    selectedNumberSquareCoordinates.second
                ) as TextView
                setValueAndFormatToNumberSquare(
                    numberSquare = selectedNumberSquare,
                    value = newNumber
                )
            }
        }
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
                if (solverViewModel.sudokuNumberGrid.value != null) {
                    val currentNumber = solverViewModel.sudokuNumberGrid
                        .value!![rowIndex][columnIndex]
                    setValueAndFormatToNumberSquare(
                        numberSquare = numberSquare,
                        value = currentNumber
                    )
                }
                numberSquare.background =
                    NumberSquareBackgroundFactory.createDrawableFromSquareTypeAndCoordinates(
                        squareType = SquareType.NORMAL_SQUARE,
                        rowIndex = rowIndex,
                        columnIndex = columnIndex,
                        context = requireContext()
                    )
                numberSquare.setOnClickListener {
                    onSquareClicked(
                        selectedSquareInstance = it as TextView,
                        selectedRowIndex = rowIndex,
                        selectedColumnIndex = columnIndex
                    )
                }
                sudokuRow.addView(numberSquare)
            }
        }
    }

    private fun setValueAndFormatToNumberSquare(
        numberSquare: TextView,
        value: Int
    ) {
        if (solverViewModel.sudokuNumberGrid.value != null) {
            if (value != -1) {
                numberSquare.text = value.toString()
            } else {
                numberSquare.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.purple_500)
                )
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
            lastSelectedSquare.background =
                NumberSquareBackgroundFactory.createDrawableFromSquareTypeAndCoordinates(
                    squareType = SquareType.NORMAL_SQUARE,
                    rowIndex = previousSelectedSquareCoordinates.first,
                    columnIndex = previousSelectedSquareCoordinates.second,
                    context = requireContext()
                )
            unsetAffectedNumberSquaresColor(
                selectedRowIndex = previousSelectedSquareCoordinates.first,
                selectedColumnIndex = previousSelectedSquareCoordinates.second
            )
        }
        selectedSquareInstance.background =
            NumberSquareBackgroundFactory.createDrawableFromSquareTypeAndCoordinates(
                squareType = SquareType.SQUARE_SELECTED,
                rowIndex = selectedRowIndex,
                columnIndex = selectedColumnIndex,
                context = requireContext()
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

    private fun alterAffectedRow(
        selectedRowIndex: Int,
        selectedColumnIndex: Int,
        alterAffectedRowOperation: AlterAffectedRowOperation
    ) {
        val selectedRow = binding.sudokuGrid.getChildAt(selectedRowIndex) as LinearLayout
        for (columnIndex in 0..8) {
            if (columnIndex != selectedColumnIndex) {
                val currentAffectedNumberSquare = selectedRow.getChildAt(columnIndex) as TextView
                val squareType = when (alterAffectedRowOperation) {
                    AlterAffectedRowOperation.SET_AFFECTED_ROW -> SquareType.SQUARE_AFFECTED
                    AlterAffectedRowOperation.UNSET_AFFECTED_ROW -> SquareType.NORMAL_SQUARE
                }
                currentAffectedNumberSquare.background =
                    NumberSquareBackgroundFactory.createDrawableFromSquareTypeAndCoordinates(
                        squareType = squareType,
                        rowIndex = selectedRowIndex,
                        columnIndex = columnIndex,
                        context = requireContext()
                    )
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
                val squareType = when (alterAffectedColumnOperation) {
                    AlterAffectedColumnOperation.SET_AFFECTED_COLUMN -> SquareType.SQUARE_AFFECTED
                    AlterAffectedColumnOperation.UNSET_AFFECTED_COLUMN -> SquareType.NORMAL_SQUARE
                }
                currentAffectedNumberSquare.background =
                    NumberSquareBackgroundFactory.createDrawableFromSquareTypeAndCoordinates(
                        squareType = squareType,
                        rowIndex = rowIndex,
                        columnIndex = selectedColumnIndex,
                        context = requireContext()
                    )
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
                    val squareType = when (alterAffectedSquareOperation) {
                        AlterAffectedSquareOperation.SET_AFFECTED_SQUARE -> SquareType.SQUARE_AFFECTED
                        AlterAffectedSquareOperation.UNSET_AFFECTED_SQUARE -> SquareType.NORMAL_SQUARE
                    }
                    currentAffectedNumberSquare.background =
                        NumberSquareBackgroundFactory.createDrawableFromSquareTypeAndCoordinates(
                            squareType = squareType,
                            rowIndex = rowIndex,
                            columnIndex = columnIndex,
                            context = requireContext()
                        )
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

    private fun createNumberButtons() {
        for (numberIndex in 1..9) {
            val numberTextView = layoutInflater.inflate(
                R.layout.numbers_pane_text_view_layout,
                binding.numbersPaneLayout,
                false
            ) as TextView
            numberTextView.text = numberIndex.toString()
            numberTextView.setOnClickListener {
                updateSelectedNumberSquareValue(numberIndex)
            }
            binding.numbersPaneLayout.addView(numberTextView)
        }
    }

    private fun updateSelectedNumberSquareValue(newNumber: Int) {
        val selectedNumberSquareCoordinates = solverViewModel.lastSelectedSquareCoordinates.value!!
        if (solverViewModel.areCoordinatesInitialised()) {
            solverViewModel.updateSudokuNumberGrid(
                rowIndex = selectedNumberSquareCoordinates.first,
                columnIndex = selectedNumberSquareCoordinates.second,
                newNumber = newNumber
            )
        } else {
            Toast.makeText(
                requireContext(),
                "No se ha pulsado ninguna casilla",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupNextClueButton() {
        binding.nextClueButton.setOnClickListener {
            val nextPlayData = solverViewModel.getNextPlayData()
            Toast.makeText(
                requireContext(),
                "$nextPlayData",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        const val TAG = "SolverFragment"
    }
}