package com.example.sudoku.solver.solvingStrategies

import android.util.Log
import com.example.sudoku.solver.ui.LastFreeCellClueLayout
import com.example.sudoku.solver.ui.NextPlayData
import com.example.sudoku.solver.ui.SudokuSolvingStrategy
import javax.inject.Inject

class MockStrategy @Inject constructor(): SudokuSolvingStrategy {

    override fun getNextPlayData(sudokuGrid: List<MutableList<Int>>): NextPlayData {
        Log.d(TAG, "Chosen strategy --> MockStrategy")
        return NextPlayData(
            rowIndex = -1,
            columnIndex = -1,
            value = -1,
            clueLayout = LastFreeCellClueLayout.NO_LAYOUT
        )
    }

    companion object {
        const val TAG = "MockStrategy"
    }
}