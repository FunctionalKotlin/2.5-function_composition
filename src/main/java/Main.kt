// Copyright © FunctionalKotlin.com 2017. All rights reserved.

import com.beust.klaxon.JsonArray
import com.beust.klaxon.Parser
import java.text.NumberFormat
import java.util.*

fun main(args: Array<String>) {
    val prices = "[\"10\", \"5\", \"null\", \"20\", \"0\"]"

    val formattedPrices = formatPrices(prices)

    println("The list of prices is: $formattedPrices")
}

fun parseJson(json: String): List<String> =
    (Parser().parse(StringBuilder(json)) as JsonArray<String>).toList()

fun getValidPrices(values: List<String>): List<Int> = values.mapNotNull { it.toIntOrNull() }

fun getFormatter(locale: Locale): NumberFormat = NumberFormat.getCurrencyInstance(locale)

fun formatPrice(price: Int): String {
    if (price == 0) {
        return "Free"
    } else {
        return getFormatter(Locale("es", "ES")).format(price)
    }
}

fun formatAll(prices: List<Int>): List<String> = prices.map(::formatPrice)

fun parseAndGetValid(json: String): List<Int> = (::parseJson combine ::getValidPrices)(json)

fun formatPrices(json: String): List<String> = (::parseAndGetValid combine ::formatAll)(json)

infix fun <T, U, V> ((T) -> U).combine(secondStep: (U) -> V): (T) -> V = { t -> secondStep(this(t))}