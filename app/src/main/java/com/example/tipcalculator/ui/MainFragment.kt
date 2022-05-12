package com.example.tipcalculator.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tipcalculator.databinding.FragmentMainBinding
import com.example.tipcalculator.model.Currency
import com.example.tipcalculator.ui.viewmodel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel by viewModels<CurrencyViewModel>()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.readAllData.collect {
                bindCurrencyValue(it)
            }
        }

        lifecycleScope.launch {
            viewModel.result.collect {
                binding.tvResult.text = it
            }
        }

        binding.calculateButton.setOnClickListener {
            calculateTip()
        }

        binding.deleteData.setOnClickListener {
            viewModel.deleteAllCurrencies()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindCurrencyValue(currency: Currency?) {
        with(binding) {
            tvUsd1.text = currency?.usd ?: "0"
            tvEur1.text = currency?.eur ?: "0"
        }
    }

    private fun calculateTip() {
        with(binding) {
            lifecycleScope.launch {
                Log.d("QQQ", "Started calculating")
                viewModel.calculateTip(tvCount.text.toString(), spinner.selectedItem.toString())
            }
        }
    }
}