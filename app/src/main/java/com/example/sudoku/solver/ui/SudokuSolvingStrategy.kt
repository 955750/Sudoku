package com.example.sudoku.solver.ui

interface SudokuSolvingStrategy {

    fun getNextPlayData(sudokuGrid: List<MutableList<Int>>): NextPlayData
}
