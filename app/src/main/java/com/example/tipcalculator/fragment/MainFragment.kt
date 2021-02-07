package com.example.tipcalculator.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tipcalculator.R
import com.example.tipcalculator.databinding.FragmentMainBinding
import com.example.tipcalculator.viewmodel.CurrencyViewModel

class MainFragment : Fragment() {

    private lateinit var mCurrencyViewModel: CurrencyViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        //Currency View Model
        mCurrencyViewModel = ViewModelProvider(this).get(CurrencyViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        binding.viewmodel = mCurrencyViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.refreshData.setOnClickListener {
            mCurrencyViewModel.deleteAllCurrencies()
            mCurrencyViewModel.downloadCurrency()
        }

        binding.calculateButton.setOnClickListener {
            mCurrencyViewModel.resultTest.setValue(calculateTip())
        }
    }

    private fun calculateTip(): String {
        var mathResult: Float? = null

        val numberInTextField = binding.tvCount.text.toString()
        val number = numberInTextField.toFloat()
        val usd = mCurrencyViewModel.getCurrencies.value?.usd?.toFloat()
        val eur = mCurrencyViewModel.getCurrencies.value?.eur?.toFloat()

        when (binding.spinner.selectedItem) {
            "RUB-USD" -> mathResult = number / usd!!
            "RUB-EUR" -> mathResult = number / eur!!
            "EUR-RUB" -> mathResult = number * eur!!
            "USD-RUB" -> mathResult = number * usd!!
        }

        when (binding.spinner.selectedItem) {
            "RUB-EUR" -> binding.tvSymbol.text = "€"
            "RUB-USD" -> binding.tvSymbol.text = "$"
            else -> binding.tvSymbol.text = "₽"
        }
        return String.format("%.2f", mathResult)
    }
}
