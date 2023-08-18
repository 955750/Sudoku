package com.example.sudoku.solver.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SolverViewModel @Inject constructor(
    private var sudokuSolvingStrategyContext: SudokuSolvingStrategyContext
) : ViewModel() {

    private val _lastSelectedLastSquareCoordinates: MutableLiveData<Pair<Int, Int>> =
        MutableLiveData(Pair(-1, -1))
    val lastSelectedSquareCoordinates: LiveData<Pair<Int, Int>> =
        _lastSelectedLastSquareCoordinates

    private val _sudokuNumberGrid: MutableLiveData<List<MutableList<Int>>> = MutableLiveData(
        listOf(
            mutableListOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
            mutableListOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
            mutableListOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
            mutableListOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
            mutableListOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
            mutableListOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
            mutableListOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
            mutableListOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
            mutableListOf(-1, -1, -1, -1, -1, -1, -1, -1, -1)
        )
    )
    val sudokuNumberGrid: LiveData<List<MutableList<Int>>> = _sudokuNumberGrid

    init {
        // context = SudokuSolvingStrategyContext(LastFreeCellStrategy())

        // Versión 1
        /*_sudokuNumberGrid.value = listOf(
            mutableListOf(1, 2, 3, 4, 5, -1, 7, 8, -1),
            mutableListOf(2, 3, 4, 5, 6, 7, 8, 9, 1),
            mutableListOf(3, 4, 5, -1, 7, 8, -1, 1, 2),
            mutableListOf(4, 5, 6, 7, 8, 9, 1, 2, 3),
            mutableListOf(5, 6, -1, 8, 9, 1, -1, 3, 4),
            mutableListOf(6, 7, 8, 9, -1, 2, -1, 4, 5),
            mutableListOf(7, 8, 9, 1, 2, 3, 4, 5, 6),
            mutableListOf(8, 9, 1, 2, 3, 4, 5, 6, 7),
            mutableListOf(9, 1, 2, 3, 4, 5, 6, 7, 8)
        )*/

        // Versión 2
        /*_sudokuNumberGrid.value = listOf(
            mutableListOf(2, -1, 6, -1, -1, -1, 1, 9, -1),
            mutableListOf(3, 4, 9, 8, -1, -1, 6, -1, -1),
            mutableListOf(8, -1, -1, 9, -1, 6, 5, 4, 3),
            mutableListOf(1, -1, -1, -1, -1, -1, -1, 7, -1),
            mutableListOf(-1, 9, -1, -1, 3, -1, -1, -1, -1),
            mutableListOf(7, 6, -1, -1, 1, -1, -1, -1, 5),
            mutableListOf(-1, 2, -1, 5, -1, -1, -1, -1, 9),
            mutableListOf(-1, -1, 5, -1, 4, 2, 7, -1, -1),
            mutableListOf(6, 8, 3, -1, 9, 7, 4, 5, 2)
        )*/

        // Versión 3
        _sudokuNumberGrid.value = listOf(
            mutableListOf(2, -1, 6, -1, -1, -1, 1, 9, 8),
            mutableListOf(3, 4, 9, 8, -1, -1, 6, 2, -1),
            mutableListOf(8, -1, -1, 9, -1, 6, 5, 4, 3),
            mutableListOf(1, -1, -1, -1, -1, -1, -1, 7, -1),
            mutableListOf(-1, 9, -1, -1, 3, -1, -1, -1, -1),
            mutableListOf(7, 6, -1, -1, 1, -1, -1, -1, 5),
            mutableListOf(-1, 2, -1, 5, -1, -1, -1, -1, 9),
            mutableListOf(-1, -1, 5, -1, 4, 2, 7, -1, -1),
            mutableListOf(6, 8, 3, -1, 9, 7, 4, 5, 2)
        )
    }

    fun updateLastSelectedSquareCoordinates(row: Int, column: Int) {
        _lastSelectedLastSquareCoordinates.value = Pair(row, column)
        Log.d(
            TAG,
            "row = ${_lastSelectedLastSquareCoordinates.value?.first}, " +
                 "column = ${_lastSelectedLastSquareCoordinates.value?.second}"
        )
    }

    fun areCoordinatesInitialised(): Boolean {
        if (_lastSelectedLastSquareCoordinates.value != null)
            return _lastSelectedLastSquareCoordinates.value?.first != -1 &&
                _lastSelectedLastSquareCoordinates.value?.second != -1
        return false
    }

    fun updateSudokuNumberGrid(rowIndex: Int, columnIndex: Int, newNumber: Int) {
        val newSudokuGrid: MutableList<MutableList<Int>> = mutableListOf()
        val oldSudokuGrid = sudokuNumberGrid.value!!
        for (oldSudokuRow in oldSudokuGrid) {
            newSudokuGrid.add(oldSudokuRow)
        }
        newSudokuGrid[rowIndex][columnIndex] = newNumber
        _sudokuNumberGrid.value = newSudokuGrid
        debugSudokuNumberGrid()
    }

    private fun debugSudokuNumberGrid() {
        val sudokuGrid = sudokuNumberGrid.value!!
        for (rowIndex in sudokuGrid.indices) {
            Log.d(TAG, "Row $rowIndex --> ${sudokuGrid[rowIndex]}")
        }
    }

    fun getNextPlayData(): NextPlayData {
        // sudokuSolvingStrategyContext.changeStrategy(MockStrategy())
        return sudokuSolvingStrategyContext.executeStrategy(_sudokuNumberGrid.value!!)
    }

    companion object {
        const val TAG = "SolverViewModel"
    }
}