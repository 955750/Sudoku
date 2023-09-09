package com.example.sudoku.solver.solvingStrategies

import android.util.Log
import com.example.sudoku.solver.ui.LastFreeCellClueLayout
import com.example.sudoku.solver.ui.NextPlayData
import com.example.sudoku.solver.ui.SolverConstants.NOT_A_NUMBER_VALUE
import com.example.sudoku.solver.ui.SudokuSolvingStrategy
import javax.inject.Inject

class LastFreeCellStrategy @Inject constructor(): SudokuSolvingStrategy {

    override fun getNextPlayData(sudokuGrid: List<MutableList<Int>>): NextPlayData {
        Log.d(TAG, "Chosen strategy --> LastFreeCellStrategy")
        var nextPlayData = checkSudokuRows(sudokuGrid = sudokuGrid)
        Log.d(TAG, "Next play data (row) = $nextPlayData")
        if (nextPlayData.value == NOT_A_NUMBER_VALUE) {
            nextPlayData = checkSudokuColumns(sudokuGrid = sudokuGrid)
            Log.d(TAG, "Next play data (column) = $nextPlayData")
            if (nextPlayData.value == NOT_A_NUMBER_VALUE) {
                nextPlayData = checkSudokuSquares(sudokuGrid = sudokuGrid)
                Log.d(TAG, "Next play data (square) = $nextPlayData")
            }
        }
        return nextPlayData
    }

    private fun checkSudokuRows(sudokuGrid: List<MutableList<Int>>): NextPlayData {
        for ((rowIndex, row) in sudokuGrid.withIndex()) {
            val possibleNumbersInRow = (1..9).toMutableList()
            val emptySquareColumnIndexes = mutableListOf<Int>()
            for ((columnIndex, number) in row.withIndex()) {
                if (number == NOT_A_NUMBER_VALUE) {
                    emptySquareColumnIndexes.add(columnIndex)
                } else {
                    possibleNumbersInRow.remove(number)
                }
            }
            if (possibleNumbersInRow.size == 1) {
                return NextPlayData(
                    rowIndex = rowIndex,
                    columnIndex = emptySquareColumnIndexes[0],
                    value = possibleNumbersInRow[0],
                    clueLayout = LastFreeCellClueLayout.ROW
                )
            }
        }
        return NextPlayData(
            rowIndex = NOT_A_NUMBER_VALUE,
            columnIndex = NOT_A_NUMBER_VALUE,
            value = NOT_A_NUMBER_VALUE,
            clueLayout = LastFreeCellClueLayout.NO_LAYOUT
        )
    }

    private fun checkSudokuColumns(sudokuGrid: List<MutableList<Int>>): NextPlayData {
        for (columnIndex in 0..8) {
            val possibleNumbersInColumn = (1..9).toMutableList()
            val emptySquareRowIndexes = mutableListOf<Int>()
            for (rowIndex in 0..8) {
                val currentNumber = sudokuGrid[rowIndex][columnIndex]
                if (currentNumber == NOT_A_NUMBER_VALUE) {
                    emptySquareRowIndexes.add(rowIndex)
                } else {
                    possibleNumbersInColumn.remove(currentNumber)
                }
            }
            if (possibleNumbersInColumn.size == 1) {
                return NextPlayData(
                    rowIndex = emptySquareRowIndexes[0],
                    columnIndex = columnIndex,
                    value = possibleNumbersInColumn[0],
                    clueLayout = LastFreeCellClueLayout.COLUMN
                )
            }
        }
        return NextPlayData(
            rowIndex = -1,
            columnIndex = -1,
            value = -1,
            clueLayout = LastFreeCellClueLayout.NO_LAYOUT
        )
    }

    private fun checkSudokuSquares(sudokuGrid: List<MutableList<Int>>): NextPlayData {
        val indexesToIterate = listOf((0..2), (3..5), (6..8))
        for (rowIndexesRange in indexesToIterate) {
            for (columnIndexesRange in indexesToIterate) {
                val possibleNumbersInSquare = (1..9).toMutableList()
                val emptySquareRowAndColumnIndexes = mutableListOf<Pair<Int, Int>>()
                for (rowIndex in rowIndexesRange) {
                    for (columnIndex in columnIndexesRange) {
                        val currentNumber = sudokuGrid[rowIndex][columnIndex]
                        if (currentNumber == -1) {
                            emptySquareRowAndColumnIndexes.add(Pair(rowIndex, columnIndex))
                        } else {
                            possibleNumbersInSquare.remove(currentNumber)
                        }
                    }
                }
                if (possibleNumbersInSquare.size == 1) {
                    return NextPlayData(
                        rowIndex = emptySquareRowAndColumnIndexes[0].first,
                        columnIndex = emptySquareRowAndColumnIndexes[0].second,
                        value = possibleNumbersInSquare[0],
                        clueLayout = LastFreeCellClueLayout.SQUARE
                    )
                }
            }
        }
        return NextPlayData(
            rowIndex = -1,
            columnIndex = -1,
            value = -1,
            clueLayout = LastFreeCellClueLayout.NO_LAYOUT
        )
    }

    companion object {
        const val TAG = "LastFreeCellStrategy"
    }
}