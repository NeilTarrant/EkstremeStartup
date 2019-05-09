package io.pivotal.extremerstartup.question

import kotlin.random.Random

fun Int.nameInGerman(): String = German.inGerman(this)
fun Int.nameInFrench(): String = French.inFrench(this)

object German {
    fun inGerman(num: Int): String {
        val tens = num.div(10)
        val units = num.rem(10)

        return when {
            num <= 12 -> uniqueNumbers[num].orEmpty()
            num > 19 -> twoDigitNumber(units, tens)
            else -> teens(num)
        }
    }

    private val uniqueNumbers = mapOf(
            0 to "null",
            1 to "eins",
            2 to "zwei",
            3 to "drei",
            4 to "vier",
            5 to "fünf",
            6 to "sechs",
            7 to "sieben",
            8 to "acht",
            9 to "neun",
            10 to "zehn",
            11 to "elf",
            12 to "zwolf"
    )

    private fun twoDigitNumber(units: Int, tens: Int) = when (units) {
        0 -> tens(tens)
        else -> "${uniqueNumbers[units]}und${tens(tens)}"
    }

    private fun teens(num: Int): String {
        return "${when (num) {
            17 -> "sieb"
            else -> uniqueNumbers[num.rem(10)].orEmpty()
        }}zehn"
    }

    private fun tens(tens: Int) = when {
        tens == 2 -> "zwanzig"
        tens == 3 -> "dreißigs"
        tens == 7 -> "siebzig"
        tens > 2 -> "${uniqueNumbers[tens]}zig"
        else -> "ERROR"
    }
}

object French {
    fun inFrench(num: Int): String {
        return "Le ${num}"
    }

}

class GermanTranslationQuestion(random: Random) : Question() {
    val number = random.nextInt(1, 100)
    override val question = "what is ${number} in German"
    override val answer = number.nameInGerman()
    override val points = 50
}