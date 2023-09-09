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
                val selectedNumberSquare = getNumberSquareInstanceByRowAndColumnIndexes(
                    rowIndex = selectedNumberSquareCoordinates.first,
                    columnIndex = selectedNumberSquareCoordinates.second
                )
                setValueAndFormatToNumberSquare(
                    numberSquare = selectedNumberSquare,
                    value = newNumber
                )
            }
        }
    }

    private fun getNumberSquareInstanceByRowAndColumnIndexes(
        rowIndex: Int,
        columnIndex: Int
    ): TextView {
        val selectedRow = binding.sudokuGrid.getChildAt(rowIndex) as LinearLayout
        return selectedRow.getChildAt(columnIndex) as TextView
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
                setupNumberSquare(
                    numberSquare = numberSquare,
                    rowIndex = rowIndex,
                    columnIndex = columnIndex
                )
                sudokuRow.addView(numberSquare)
            }
        }
    }

    private fun setValueAndFormatToNumberSquare(
        numberSquare: TextView,
        value: Int
    ) {
        if (solverViewModel.sudokuNumberGrid.value != null) {
            if (value == SolverConstants.NOT_A_NUMBER_VALUE) {
                numberSquare.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.purple_500)
                )
            } else {
                numberSquare.text = value.toString()
            }
        }
    }

    private fun setupNumberSquare(
        numberSquare: TextView,
        rowIndex: Int,
        columnIndex: Int
    ) {
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
                selectedRowIndex = rowIndex,
                selectedColumnIndex = columnIndex
            )
        }
    }

    private fun onSquareClicked(
        selectedRowIndex: Int,
        selectedColumnIndex: Int
    ) {
        solverViewModel.setClueRecentlyAsked(false)
        resetPreviouslyAffectedSquaresBackground()
        setSelectedSquareBackground(
            rowIndex = selectedRowIndex,
            columnIndex = selectedColumnIndex
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

    private fun resetSelectedSquareBackground(
        previousSelectedSquareCoordinates: Pair<Int, Int>
    ) {
        alterNumberSquareBackground(
            rowIndex = previousSelectedSquareCoordinates.first,
            columnIndex = previousSelectedSquareCoordinates.second,
            squareType = SquareType.NORMAL_SQUARE
        )
    }

    private fun setSelectedSquareBackground(
        rowIndex: Int,
        columnIndex: Int
    ) {
        alterNumberSquareBackground(
            rowIndex = rowIndex,
            columnIndex = columnIndex,
            squareType = SquareType.SELECTED_SQUARE
        )
    }

    private fun alterNumberSquareBackground(
        rowIndex: Int,
        columnIndex: Int,
        squareType: SquareType
    ) {
        val numberSquare = getNumberSquareInstanceByRowAndColumnIndexes(
            rowIndex = rowIndex,
            columnIndex = columnIndex
        )
        numberSquare.background = NumberSquareBackgroundFactory
            .createDrawableFromSquareTypeAndCoordinates(
                squareType = squareType,
                rowIndex = rowIndex,
                columnIndex = columnIndex,
                context = requireContext()
            )
    }

    private fun setAffectedNumberSquaresColor(
        selectedRowIndex: Int,
        selectedColumnIndex: Int
    ) {
        alterRowBackground(
            selectedRowIndex = selectedRowIndex,
            selectedColumnIndex = selectedColumnIndex,
            squareType = SquareType.AFFECTED_SQUARE
        )
        alterColumnBackground(
            selectedRowIndex = selectedRowIndex,
            selectedColumnIndex = selectedColumnIndex,
            squareType = SquareType.AFFECTED_SQUARE
        )
        alterSquareBackground(
            selectedRowIndex = selectedRowIndex,
            selectedColumnIndex = selectedColumnIndex,
            squareType = SquareType.AFFECTED_SQUARE
        )
    }

    private fun resetAffectedNumberSquaresColor(
        selectedRowIndex: Int,
        selectedColumnIndex: Int
    ) {
        alterRowBackground(
            selectedRowIndex = selectedRowIndex,
            selectedColumnIndex = selectedColumnIndex,
            squareType = SquareType.NORMAL_SQUARE
        )
        alterColumnBackground(
            selectedRowIndex = selectedRowIndex,
            selectedColumnIndex = selectedColumnIndex,
            squareType = SquareType.NORMAL_SQUARE
        )
        alterSquareBackground(
            selectedRowIndex = selectedRowIndex,
            selectedColumnIndex = selectedColumnIndex,
            squareType = SquareType.NORMAL_SQUARE
        )
    }

    private fun alterRowBackground(
        selectedRowIndex: Int,
        selectedColumnIndex: Int,
        squareType: SquareType
    ) {
        for (columnIndex in 0..8) {
            if (columnIndex != selectedColumnIndex) {
                alterNumberSquareBackground(
                    rowIndex = selectedRowIndex,
                    columnIndex = columnIndex,
                    squareType = squareType
                )
            }
        }
    }

    private fun alterColumnBackground(
        selectedRowIndex: Int,
        selectedColumnIndex: Int,
        squareType: SquareType
    ) {
        for (rowIndex in 0..8) {
            if (rowIndex != selectedRowIndex) {
                alterNumberSquareBackground(
                    rowIndex = rowIndex,
                    columnIndex = selectedColumnIndex,
                    squareType = squareType
                )
            }
        }
    }

    private fun alterSquareBackground(
        selectedRowIndex: Int,
        selectedColumnIndex: Int,
        squareType: SquareType
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
                    alterNumberSquareBackground(
                        rowIndex = rowIndex,
                        columnIndex = columnIndex,
                        squareType = squareType
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
            else -> Pair(SolverConstants.NOT_A_NUMBER_VALUE, SolverConstants.NOT_A_NUMBER_VALUE)
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
                updateSelectedNumberSquareValue(newNumber = numberIndex)
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
                "No square has been selected",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupNextClueButton() {
        binding.nextClueButton.setOnClickListener {
            if (solverViewModel.clueRecentyAsked.value!!) {
                resetPreviousClueSquaresBackground()
            } else {
                resetPreviouslyAffectedSquaresBackground()
            }

            solverViewModel.setClueRecentlyAsked(true)
            val nextPlayData = solverViewModel.getNextPlayData()
            when (nextPlayData.clueLayout) {
                LastFreeCellClueLayout.ROW -> {
                    setRowClueBackground(
                        nextPlayRowIndex = nextPlayData.rowIndex,
                        nextPlayColumnIndex = nextPlayData.columnIndex
                    )
                }
                LastFreeCellClueLayout.COLUMN -> {
                    setColumnClueBackground(
                        nextPlayRowIndex = nextPlayData.rowIndex,
                        nextPlayColumnIndex = nextPlayData.columnIndex
                    )
                }
                LastFreeCellClueLayout.SQUARE -> {}
                LastFreeCellClueLayout.NO_LAYOUT -> {}
            }
            solverViewModel.updateLastSelectedSquareCoordinates(
                row = nextPlayData.rowIndex,
                column = nextPlayData.columnIndex
            )
            updateSelectedNumberSquareValue(newNumber = nextPlayData.value)
            Toast.makeText(
                requireContext(),
                "$nextPlayData",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun resetPreviouslyAffectedSquaresBackground() {
        if (solverViewModel.areCoordinatesInitialised()) {
            val previousSelectedSquareCoordinates = solverViewModel
                .lastSelectedSquareCoordinates.value!!
            resetSelectedSquareBackground(
                previousSelectedSquareCoordinates = previousSelectedSquareCoordinates
            )
            resetAffectedNumberSquaresColor(
                selectedRowIndex = previousSelectedSquareCoordinates.first,
                selectedColumnIndex = previousSelectedSquareCoordinates.second
            )
        }
    }

    /*private fun resetPreviousClueSquaresBackground() {
        val previousSelectedSquareCoordinates = solverViewModel
            .lastSelectedSquareCoordinates.value!!
        if (previousSelectedSquareCoordinates.first != 8) {
            alterRowBackground(
                selectedRowIndex = previousSelectedSquareCoordinates.first + 1,
                selectedColumnIndex = previousSelectedSquareCoordinates.second,
                squareType = SquareType.NORMAL_SQUARE
            )
        }
        alterRowBackground(
            selectedRowIndex = previousSelectedSquareCoordinates.first,
            selectedColumnIndex = previousSelectedSquareCoordinates.second,
            squareType = SquareType.NORMAL_SQUARE
        )
        alterNumberSquareBackground(
            rowIndex = previousSelectedSquareCoordinates.first,
            columnIndex = previousSelectedSquareCoordinates.second,
            squareType = SquareType.NORMAL_SQUARE
        )
    }*/

    /* PARA SIMPLIFICAR LA LÓGICA -> SE PODRÍA REFINAR/OPTIMIZAR MÁS ADELANTE */
    private fun resetPreviousClueSquaresBackground() {
        for (rowIndex in 0..8) {
            for (columnIndex in 0..8) {
                alterNumberSquareBackground(
                    rowIndex = rowIndex,
                    columnIndex = columnIndex,
                    squareType = SquareType.NORMAL_SQUARE
                )
            }
        }
    }

    private fun setRowClueBackground(
        nextPlayRowIndex: Int,
        nextPlayColumnIndex: Int
    ) {
        if (nextPlayRowIndex == 8) {
            alterRowBackground(
                selectedRowIndex = nextPlayRowIndex,
                selectedColumnIndex = nextPlayColumnIndex,
                squareType = SquareType.ROW_CLUE_SQUARE_LAST_ROW
            )
            alterNumberSquareBackground(
                rowIndex = nextPlayRowIndex,
                columnIndex = nextPlayColumnIndex,
                squareType = SquareType.HIGHLIGHTED_ORANGE_SQUARE_LAST_ROW
            )
        } else {
            alterRowBackground(
                selectedRowIndex = nextPlayRowIndex,
                selectedColumnIndex = nextPlayColumnIndex,
                squareType = SquareType.ROW_CLUE_SQUARE_TOP
            )
            alterNumberSquareBackground(
                rowIndex = nextPlayRowIndex,
                columnIndex = nextPlayColumnIndex,
                squareType = SquareType.HIGHLIGHTED_ORANGE_SQUARE_ROW_TOP
            )
            alterRowBackground(
                selectedRowIndex = nextPlayRowIndex + 1,
                selectedColumnIndex = nextPlayColumnIndex,
                squareType = SquareType.ROW_CLUE_SQUARE_BOTTOM
            )
            alterNumberSquareBackground(
                rowIndex = nextPlayRowIndex + 1,
                columnIndex = nextPlayColumnIndex,
                squareType = SquareType.ROW_CLUE_SQUARE_BOTTOM
            )
        }
    }

    private fun setColumnClueBackground(
        nextPlayRowIndex: Int,
        nextPlayColumnIndex: Int
    ) {
        if (nextPlayColumnIndex == 8) {
            alterColumnBackground(
                selectedRowIndex = nextPlayRowIndex,
                selectedColumnIndex = nextPlayColumnIndex,
                squareType = SquareType.COLUMN_CLUE_SQUARE_LAST_COLUMN
            )
            alterNumberSquareBackground(
                rowIndex = nextPlayRowIndex,
                columnIndex = nextPlayColumnIndex,
                squareType = SquareType.HIGHLIGHTED_ORANGE_SQUARE_LAST_COLUMN
            )
        } else {
            alterColumnBackground(
                selectedRowIndex = nextPlayRowIndex,
                selectedColumnIndex = nextPlayColumnIndex,
                squareType = SquareType.COLUMN_CLUE_SQUARE_LEFT
            )
            alterNumberSquareBackground(
                rowIndex = nextPlayRowIndex,
                columnIndex = nextPlayColumnIndex,
                squareType = SquareType.HIGHLIGHTED_ORANGE_SQUARE_COLUMN_LEFT
            )
            alterColumnBackground(
                selectedRowIndex = nextPlayRowIndex,
                selectedColumnIndex = nextPlayColumnIndex + 1,
                squareType = SquareType.COLUMN_CLUE_SQUARE_RIGHT
            )
            alterNumberSquareBackground(
                rowIndex = nextPlayRowIndex,
                columnIndex = nextPlayColumnIndex + 1,
                squareType = SquareType.COLUMN_CLUE_SQUARE_RIGHT
            )
        }
    }

    companion object {
        const val TAG = "SolverFragment"
    }
}