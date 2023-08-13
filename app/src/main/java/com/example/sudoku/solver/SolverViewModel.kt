package com.example.sudoku.solver

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SolverViewModel : ViewModel() {

    private val _lastSelectedLastSquareCoordinates: MutableLiveData<Pair<Int, Int>> =
        MutableLiveData(Pair(-1, -1))
    val lastSelectedSquareCoordinates: LiveData<Pair<Int, Int>> =
        _lastSelectedLastSquareCoordinates

    private val _sudokuNumberGrid: MutableLiveData<List<MutableList<Int>>> = MutableLiveData(listOf(
        mutableListOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
        mutableListOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
        mutableListOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
        mutableListOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
        mutableListOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
        mutableListOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
        mutableListOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
        mutableListOf(-1, -1, -1, -1, -1, -1, -1, -1, -1),
        mutableListOf(-1, -1, -1, -1, -1, -1, -1, -1, -1)
    ))
    val sudokuNumberGrid: LiveData<List<MutableList<Int>>> = _sudokuNumberGrid

    init {
        _sudokuNumberGrid.value = listOf(
            mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
            mutableListOf(1, -1, 3, 4, 5, 6, 7, 8, 9),
            mutableListOf(1, 2, 3, 4, 5, 6, -1, 8, 9),
            mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
            mutableListOf(1, 2, -1, 4, 5, 6, -1, 8, 9),
            mutableListOf(1, 2, 3, 4, -1, 6, 7, 8, 9),
            mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
            mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
            mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
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
            newSudokuGrid += oldSudokuRow
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

    companion object {
        const val TAG = "SolverViewModel"
    }
}