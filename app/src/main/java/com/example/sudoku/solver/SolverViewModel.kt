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

    val sudokuNumberGrid: List<List<Int>> = listOf(
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    )

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

    companion object {
        const val TAG = "SolverViewModel"
    }
}