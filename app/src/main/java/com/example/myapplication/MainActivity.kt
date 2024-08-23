package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var inputTextView:TextView
    private lateinit var outputTextView:TextView

    private var input:String=""
    private  var opera1: Double=0.0
    private  var opera2: Double=0.0
    private  var operator :String=""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        inputTextView = findViewById(R.id.input)
        outputTextView= findViewById(R.id.output)

        val buttons = listOf<Button>(
            findViewById(R.id.button_0),
            findViewById(R.id.button_1),
            findViewById(R.id.button_2),
            findViewById(R.id.button_3),
            findViewById(R.id.button_4),
            findViewById(R.id.button_5),
            findViewById(R.id.button_6),
            findViewById(R.id.button_7),
            findViewById(R.id.button_8),
            findViewById(R.id.button_9),
            findViewById(R.id.button_bracket_right),
            findViewById(R.id.button_bracket_left),
            findViewById(R.id.button_dot),
            findViewById(R.id.button_addition),
            findViewById(R.id.button_subtract),
            findViewById(R.id.button_multiply),
            findViewById(R.id.button_division),
            findViewById(R.id.button_equals),
            findViewById(R.id.button_clear)

        )

        buttons.forEach{ button ->

            button.setOnClickListener(View.OnClickListener {

                handleButtonClick(button.text.toString())
            })

        }

    }
    private fun  appendInput(value:String){
        input +=value
        inputTextView.text=input
    }

    private fun appendDecimal(){
        if(!input.contains(".")){
            input +="."
            inputTextView.text=input
        }
    }

    private fun handleOperator(op:String){
        if(input.isNotBlank()){
            opera1 =input.toDouble()
            operator =op
            input =""
            inputTextView.text=""
        }
    }
    private fun calculateResult(){
        if(input.isNotEmpty() && operator.isNotEmpty()) {
            opera2 = input.toDouble()
            var result = when (operator) {
                "+" -> opera1 + opera2
                "-" -> opera1 - opera2
                "×" -> opera1 * opera2
                "÷" -> {
                    if (opera2 != 0.0) {
                        opera1 / opera2
                    } else {
                        outputTextView.text = "Error"

                        return

                    }
                }

                else -> throw IllegalStateException("inalid operator")

            }

            outputTextView.text = result.toString()
            input = result.toString()
            inputTextView.text = input
        }else{
            outputTextView.text ="Error"

        }
    }

    private  fun clearinput(){
        input =""
        opera1 =0.0
        opera2 =0.0
        operator =""

        inputTextView.text =""
        outputTextView.text=""


    }
    private fun handlePercentage(){
        if(input.isNotEmpty()){
            try{
                val value =input.toDouble()/100
                input=value.toString()
                inputTextView.text = input
            }catch (e:NumberFormatException){
                inputTextView.text="Error"

            }
        }
    }
    private fun toogleSign(){
        if(input.isNotEmpty() && input!="0"){
            val value = input.toDouble()*-1
            input=value.toString()
            inputTextView.text =input
        }
    }
    private  fun String.isNumeris():Boolean{
        return try {
            this.toDouble()
            true

        }catch (e:NumberFormatException){
            false
        }
    }
    private fun handleButtonClick(value: String){
        when{
            value.isNumeris() -> appendInput(value)
            value =="." -> appendDecimal()
            value in setOf("+","-","×","÷") -> handleOperator(value)
            value == "=" ->calculateResult()
            value == "C" -> clearinput()
            value == "%" -> handlePercentage()
            value == "+/-" -> toogleSign()
        }
    }


}
