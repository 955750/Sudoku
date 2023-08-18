package com.example.sudoku.solver.ui

import com.example.sudoku.solver.solvingStrategies.LastFreeCellStrategy
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SudokuSolvingStrategyModule {

    @Binds
    abstract fun bindSudokuSolvingStrategy(
        lastFreeCellStrategy: LastFreeCellStrategy
    ): SudokuSolvingStrategy

}
