package com.example.tipcalculator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tipcalculator.R
import com.example.tipcalculator.databinding.FragmentMainBinding
import com.example.tipcalculator.ui.viewmodel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel by viewModels<CurrencyViewModel>()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        viewModel.readAllData.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.tvEur1.text = ""
                binding.tvUsd1.text = ""
                return@observe
            }
            binding.tvEur1.text = it.eur
            binding.tvUsd1.text = it.usd
        }

        binding.run {
            deleteData.setOnClickListener { viewModel.deleteAllCurrencies() }
            calculateButton.setOnClickListener {
                viewModel.calculateTip(binding.tvCount.text.toString(), binding.spinner.selectedItem.toString())
                tvResult.text = viewModel.resultLiveData.value
                tvSymbol.text = viewModel.symbolLiveData.value
            }
        }
    }
}