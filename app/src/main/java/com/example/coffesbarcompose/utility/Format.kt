package com.example.coffesbarcompose.utility

class Format {
    companion object  {
        fun formatDoubleToMoneyReal(value: Double): String {
            val formatTwoDigits = String.format("%.2f",value)
            val splitValue = formatTwoDigits.split(".")
            return "${splitValue[0]},${splitValue[1]}"
        }
    }
}