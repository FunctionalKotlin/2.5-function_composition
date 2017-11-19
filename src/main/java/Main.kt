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

fun getValidPrices(values: List<String>): List<Int> {
    val prices = mutableListOf<Int>()

    for (value in values) {
        val price = value.toIntOrNull()

        if (price != null) {
            prices.add(price)
        }
    }

    return prices
}


fun formatPrices(json: String): List<String> {
    val jsonArray = parseJson(json)

    val prices = getValidPrices(jsonArray)

    val labels = mutableListOf<String>()

    for (price in prices) {
        var label: String = ""

        if (price == 0) {
            label = "Free"
        } else {
            val formatter = NumberFormat.getCurrencyInstance(Locale("es", "ES"))

            label = formatter.format(price)
        }

        labels.add(label)
    }

    return labels
}