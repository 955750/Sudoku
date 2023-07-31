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
import com.example.sudoku.R
import com.example.sudoku.databinding.FragmentSolverBinding


class SolverFragment : Fragment() {

    private lateinit var binding: FragmentSolverBinding

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

        val sudokuGrid = binding.sudokuGrid
        for (row in 1..9) {
            val sudokuRow = layoutInflater.inflate(
                R.layout.sudoku_row_layout,
                binding.sudokuGrid,
                false
            ) as LinearLayout
            sudokuGrid.addView(sudokuRow)
            for (column in 1..9) {
                val numberSquare = layoutInflater.inflate(
                    R.layout.number_square_layout,
                    binding.sudokuGrid,
                    false
                ) as TextView
                numberSquare.text = column.toString()
                numberSquare.setOnClickListener {
                    Log.d(TAG, "row = $row, column = $column")
                }
                setNumberSquareBorders(numberSquare, row, column)
                sudokuRow.addView(numberSquare)
            }
        }
    }

    private fun setNumberSquareBorders(numberSquare: TextView, row: Int, column: Int) {
        when (column) {
            1, 4, 7 -> {
                when (row) {
                    1, 4, 7 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_top_left_bg)
                    2, 3, 5, 6, 8 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col__top_bg)
                    9 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_bottom_left_bg)
                }
            }
            2, 3, 5, 6, 8 -> {
                when (row) {
                    1, 4, 7 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_top__middle_right_bg)
                    2, 3, 5, 6, 8 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col_bg)
                    9 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col__bottom_bg)
                }
            }
            9 -> {
                when (row) {
                    1, 4, 7 ->  numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_top_right_bg)
                    2, 3, 5, 6, 8 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(),
                            R.drawable.num_sq_middle_col__right_col_right_bg)
                    9 -> numberSquare.background = ContextCompat
                        .getDrawable(requireContext(), R.drawable.num_sq_bottom_right_bg)
                }
            }
        }
    }

    companion object {
        const val TAG = "SolverFragment"
    }
}