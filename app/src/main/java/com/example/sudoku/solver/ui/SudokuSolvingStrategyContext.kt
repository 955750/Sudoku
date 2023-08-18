package com.example.sudoku.solver.ui

import javax.inject.Inject


class SudokuSolvingStrategyContext @Inject constructor(
    private var strategy: SudokuSolvingStrategy
) {

    fun changeStrategy(strategy: SudokuSolvingStrategy) {
        this.strategy = strategy
    }

    fun executeStrategy(sudokuGrid: List<MutableList<Int>>): NextPlayData {
        return strategy.getNextPlayData(sudokuGrid = sudokuGrid)
    }
}