package com.example.coffesbarcompose.utility

import android.util.Log
import java.util.Locale
import kotlin.math.roundToInt

class Format {
    companion object {
        fun formatDoubleToMoneyReal(value: Double): String {
            //String.format vai pegar a localizacao do usaurio e tentar converter
            //entao pode gerar bug como virgula ou ponto
            //https://stackoverflow.com/questions/8601011/string-format-uses-comma-instead-of-point
            //refrencia do erro
            //https://webapps.stackexchange.com/questions/103958/change-google-sheets-default-date-format-with-other-locale/106243#106243
            //acima a tabela qual usa , ou ponto
            return "R$ ${String.format(locale = Locale.FRANCE, "%.2f", value)}"
        }
    }
}