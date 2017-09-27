// Copyright © FunctionalKotlin.com 2017. All rights reserved.

import com.beust.klaxon.JsonArray
import com.beust.klaxon.Parser
import java.text.NumberFormat
import java.util.*

fun main(args: Array<String>) {
    val prices = "[\"10\", \"5\", \"null\", \"20\", \"0\"]"

    val formatAllToEuro = formatAll(Locale("es", "ES"))

    val formatPrices = ::parseJson andThen ::getValidPrices andThen formatAllToEuro

    println(prices.let(formatPrices))
}

fun parseJson(json: String): List<String> =
    (Parser().parse(StringBuilder(json)) as JsonArray<String>).toList()

fun getValidPrices(values: List<String>): List<Int> = values.mapNotNull { it.toIntOrNull() }

fun getFormatter(locale: Locale): NumberFormat = NumberFormat.getCurrencyInstance(locale)

fun formatPrice(locale: Locale): (Int) -> String = { price ->
    if (price == 0) {
        "Free"
    } else {
        getFormatter(locale).format(price)
    }
}

fun formatAll(locale: Locale): (List<Int>) -> List<String> = { it.map(formatPrice(locale)) }

infix fun <T, U, V> ((T) -> U).andThen(after: (U) -> V): (T) -> V = { t -> after(this(t))}