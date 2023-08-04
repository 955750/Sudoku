package com.example.sudoku.solver

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.sudoku.R
import com.example.sudoku.databinding.FragmentSolverBinding


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
    }

    private fun createSudokuGrid() {
        for (row in 0..8) {
            val sudokuRow = layoutInflater.inflate(
                R.layout.sudoku_row_layout,
                binding.sudokuGrid,
                false
            ) as LinearLayout
            binding.sudokuGrid.addView(sudokuRow)
            for (column in 0..8) {
                val numberSquare = layoutInflater.inflate(
                    R.layout.number_square_layout,
                    binding.sudokuGrid,
                    false
                ) as TextView
                numberSquare.text = (column + 1).toString()
                numberSquare.setOnClickListener {
                    onSquareClicked(it as TextView, row, column)
                }
                setNotSelectedNumberSquareColor(numberSquare, row, column)
                sudokuRow.addView(numberSquare)
            }
        }
    }

    private fun onSquareClicked(
        selectedSquareInstance: TextView,
        selectedRow: Int,
        selectedColumn: Int
    ) {
        if (solverViewModel.areCoordinatesInitialised()) {
            val lastSelectedSquareCoordinates = solverViewModel
                .lastSelectedSquareCoordinates.value!!
            val lastSelectedRow = binding.sudokuGrid.getChildAt(
                lastSelectedSquareCoordinates.first
            ) as LinearLayout
            val lastSelectedSquare = lastSelectedRow.getChildAt(
                lastSelectedSquareCoordinates.second
            ) as TextView
            setNotSelectedNumberSquareColor(
                numberSquare = lastSelectedSquare,
                row = lastSelectedSquareCoordinates.first,
                column = lastSelectedSquareCoordinates.second
            )
        }
        setSelectedNumberSquareColor(selectedSquareInstance, selectedRow, selectedColumn)
        solverViewModel.updateLastSelectedSquareCoordinates(selectedRow, selectedColumn)
        Log.d(TAG, "row = $selectedRow, column = $selectedColumn")
    }

    private fun setNotSelectedNumberSquareColor(numberSquare: TextView, row: Int, column: Int) {
        when (column) {
            0, 3, 6 -> {
                when (row) {
                    0, 3, 6 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_top_left_bg)
                    1, 2, 4, 5, 7 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col__top_bg)
                    8 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_bottom_left_bg)
                }
            }
            1, 2, 4, 5, 7 -> {
                when (row) {
                    0, 3, 6 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_top__middle_right_bg)
                    1, 2, 4, 5, 7 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col_bg)
                    8 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col__bottom_bg)
                }
            }
            8 -> {
                when (row) {
                    0, 3, 6 ->  numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_top_right_bg)
                    1, 2, 4, 5, 7 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col_right_bg)
                    8 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_bottom_right_bg)
                }
            }
        }
    }

    private fun setSelectedNumberSquareColor(numberSquare: TextView, row: Int, column: Int) {
        when (column) {
            0, 3, 6 -> {
                when (row) {
                    0, 3, 6 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_top_left_bg_selected)
                    1, 2, 4, 5, 7 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col__top_bg_selected)
                    8 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_bottom_left_bg_selected)
                }
            }
            1, 2, 4, 5, 7 -> {
                when (row) {
                    0, 3, 6 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_top__middle_right_bg_selected)
                    1, 2, 4, 5, 7 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col_bg_selected)
                    8 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col__bottom_bg_selected)
                }
            }
            8 -> {
                when (row) {
                    0, 3, 6 ->  numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_top_right_bg_selected)
                    1, 2, 4, 5, 7 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col_right_bg_selected)
                    8 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_bottom_right_bg_selected)
                }
            }
        }
    }

    companion object {
        const val TAG = "SolverFragment"
    }
}