package com.example.sudoku.solver.ui

data class NextPlayData(
    val rowIndex: Int,
    val columnIndex: Int,
    val value: Int,
    val clueLayout: LastFreeCellClueLayout
)
