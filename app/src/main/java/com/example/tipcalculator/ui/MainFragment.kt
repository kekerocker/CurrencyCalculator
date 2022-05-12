package com.example.tipcalculator.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tipcalculator.databinding.FragmentMainBinding
import com.example.tipcalculator.ui.viewmodel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel by viewModels<CurrencyViewModel>()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMainBinding.inflate(inflater, container, false).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            fragment = this@MainFragment
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }

    fun calculateTip() {
        with(binding) {
            viewModel.calculateTip(tvCount.text.toString(), spinner.selectedItem.toString())
            tvResult.text = viewModel.resultLiveData.value
        }
    }
}