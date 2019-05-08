package io.pivotal.extremerstartup.question

import kotlin.random.Random

abstract class SelectFromListOfNumbersQuestion(
        private val random: Random,
        private val numOptions: Int,
        val filter: (Int) -> Boolean = { _: Int -> true }
) : Question() {

    val numbers: MutableList<Int>

    init {
        numbers = (0 until numOptions).map {
            var num = random.nextInt(0, 1000)
            while (!filter(num)) {
                num = random.nextInt(0, 1000)
            }
            num
        }.toMutableList()
    }

    fun setCorrectAnswer(correctAnswer: Int) {
        numbers.add(correctAnswer)
        numbers.shuffle()
    }
}

class SquareCubeQuestion(random: Random) : SelectFromListOfNumbersQuestion(random, 4, { it != 1 }) {
    override val question get() = "which of the following numbers is both a square and a cube: ${numbers.joinToString(", ")}"
    override val answer = if (random.nextBoolean()) "64" else "729"
    override val points = 30

    init {
        setCorrectAnswer(answer.toInt())
    }
}

class MaximumQuestion(random: Random) : SelectFromListOfNumbersQuestion(
        random,
        random.nextInt(3, 5)
) {
    override val question get() = "which of the following numbers is the largest: ${numbers.joinToString(", ")}"
    override val answer = numbers.max().toString()
    override val points = 40
}

class MinimumQuestion(random: Random) : SelectFromListOfNumbersQuestion(
        random,
        random.nextInt(3, 5)
) {
    override val question get() = "which of the following numbers is the smallest: ${numbers.joinToString(", ")}"
    override val answer = numbers.min().toString()
    override val points = 40
}

class PrimesQuestion(random: Random) : SelectFromListOfNumbersQuestion(
        random,
        5,
        { !it.isPrime() }
) {
    override val question get() = "which of the following numbers is prime: ${numbers.joinToString(", ")}"
    override val answer: String
    override val points = 60

    init {
        var num = random.nextInt(0, 1000)
        while (!num.isPrime()) {
            num = random.nextInt(0, 1000)
        }
        setCorrectAnswer(num)
        answer = num.toString()
    }
}