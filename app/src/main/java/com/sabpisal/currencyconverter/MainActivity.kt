package com.sabpisal.currencyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import android.widget.Toast
import com.sabpisal.currencyconverter.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.convertButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(view: View?) {
                currencyConversion()
            }
        })

        binding.convertButton.setOnClickListener { view -> currencyConversion() }
    }

    private fun currencyConversion() {
        val amountOrigin =  binding.etCurrencyOrigin.text.toString().toDoubleOrNull()
        val convertFrom = binding.spinnerCurrencyConvertFrom
        val convertTo = binding.spinnerCurrencyConvertTo

        if (amountOrigin == null) {
            Toast.makeText(this, "Please enter an integer in the text field", Toast.LENGTH_SHORT)
                .show()
            displayFinalAmount(0.0,convertTo)
            return
        }
        displayFinalAmount(conversion(convertFrom, convertTo, amountOrigin), convertTo)
    }

    private fun displayFinalAmount(finalAmount: Double, convertTo: Spinner) {
        val formattedFinalAmount = NumberFormat.getCurrencyInstance(getLocale(convertTo)).format(finalAmount)
        binding.tvResult.text = getString(R.string.final_amount, formattedFinalAmount)
    }

    private fun conversion(convertFrom: Spinner, convertTo: Spinner, amountOrigin: Double): Double {
        when (convertFrom.selectedItemPosition) {
            // USD
            0 -> {
                val conversionFactor = when (convertTo.selectedItemPosition) {
                    // BAHT
                    1 -> 31.41
                    // USD
                    else -> 1.00
                }
                return amountOrigin * conversionFactor
            }
            // BAHT
            else -> {
                val conversionFactor = when (convertTo.selectedItemPosition) {
                    // USD
                    0 -> 31.41
                    // BAHT
                    else -> 1.00
                }
                return amountOrigin / conversionFactor
            }
        }
    }

    private fun getLocale(convertTo:Spinner): Locale{
        return when (convertTo.selectedItemPosition) {
            0 -> Locale("en","US")
            else -> Locale("en", "TH")
        }
    }

}