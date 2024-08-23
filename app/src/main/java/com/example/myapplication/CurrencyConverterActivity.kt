package com.example.myapplication

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class CurrencyConverterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_converter)

        // Initialize UI elements
        val spinnerFrom: Spinner = findViewById(R.id.spinner_from_currency)
        val spinnerTo: Spinner = findViewById(R.id.spinner_to_currency)
        val editTextAmount: EditText = findViewById(R.id.edit_text_amount)
        val buttonConvert: Button = findViewById(R.id.button_convert)
        val textViewResult: TextView = findViewById(R.id.text_view_result)

        // Define the currency pairs
        val currencies = arrayOf("USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY")

        // Set up the spinners with the currency options
        val adapter = ArrayAdapter(this, R.layout.spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        // Set up the convert button click listener
        buttonConvert.setOnClickListener {
            val fromCurrency = spinnerFrom.selectedItem.toString()
            val toCurrency = spinnerTo.selectedItem.toString()
            val amount = editTextAmount.text.toString().toDoubleOrNull()

            if (amount != null) {
                val result = convertCurrency(fromCurrency, toCurrency, amount)
                textViewResult.text = "$amount $fromCurrency = $result $toCurrency"
            } else {
                textViewResult.text = "Please enter a valid amount."
            }
        }
    }

    // Function to convert currency
    private fun convertCurrency(fromCurrency: String, toCurrency: String, amount: Double): Double {
        val conversionRates = mapOf(
            "USD" to mapOf("EUR" to 0.85, "GBP" to 0.75, "JPY" to 110.0, "AUD" to 1.35, "CAD" to 1.25, "CHF" to 0.92, "CNY" to 6.5),
            "EUR" to mapOf("USD" to 1.18, "GBP" to 0.88, "JPY" to 129.5, "AUD" to 1.6, "CAD" to 1.47, "CHF" to 1.08, "CNY" to 7.65),
            // Add other conversion rates as needed
        )

        return if (fromCurrency == toCurrency) {
            amount
        } else {
            val rate = conversionRates[fromCurrency]?.get(toCurrency) ?: 1.0
            amount * rate
        }
    }
}
