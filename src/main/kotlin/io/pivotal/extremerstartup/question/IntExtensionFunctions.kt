package io.pivotal.extremerstartup.question

import kotlin.math.sqrt

fun Int.primeFactors(): List<Int> {
    val max = sqrt(this.toDouble()).toInt()

    return when {
        this < 3 -> listOf(this)
        else -> listOf(listOf(1, 2), (3..max step 2).toList())
                .flatten()
                .filter { this.rem(it) == 0 }
    }
}

fun Int.isPrime(): Boolean {
    return when {
        this == 0 -> false
        this == 1 -> false
        else -> this.primeFactors().size == 1
    }
}

fun Int.digitSum(): Int {
    var num = this
    var sum = 0

    while (num > 0) {
        sum += num.rem(10)
        num = num.div(10)
    }

    return sum
}

fun Int.digitProduct(): Int {
    if (this == 0) return 0
    var num = this
    var product = 1

    while (num > 0) {
        product *= num.rem(10)
        num = num.div(10)
    }

    return product
}

fun Int.ordinal() =
        "$this${englishOrdinalExtension()}"

private fun Int.englishOrdinalExtension(): String {
    return when (this) {
        11 -> "th"
        12 -> "th"
        13 -> "th"
        else -> when (this.rem(10)) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }
}