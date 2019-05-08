package io.pivotal.extremerstartup.question

import kotlin.random.Random

class EnglishScrabbleQuestion(random: Random) : Question() {
    override val question: String
    override val answer: String
    override val points = 80

    init {
        val num = random.nextInt(ENGLISH_WORD_LIST.size)
        val word = ENGLISH_WORD_LIST[num]
        question = "what is the English scrabble score of '${word}'"
        answer = word.map { it.englishScrabbleScore() }.sum().toString()
    }
}

class GermanScrabbleQuestion(random: Random) : Question() {
    override val question: String
    override val answer: String
    override val points = 80

    init {
        val num = random.nextInt(GERMAN_WORD_LIST.size)
        val word = GERMAN_WORD_LIST[num]
        question = "what is the German scrabble score of '${word}'"
        answer = word.map { it.germanScrabbleScore() }.sum().toString()
    }
}

fun Char.englishScrabbleScore(): Int {
    return when (this) {
        'e', 'a', 'i', 'o', 'n', 'r', 't', 'l', 's', 'u' -> 1
        'd', 'g' -> 2
        'b', 'c', 'm', 'p' -> 3
        'f', 'h', 'v', 'w', 'y' -> 4
        'k' -> 5
        'j', 'x' -> 8
        'q', 'z' -> 10
        else -> 0
    }
}

fun Char.germanScrabbleScore(): Int {
    return when (this) {
        'd', 'a', 'i', 'r', 't', 'u', 's', 'n', 'e' -> 1
        'g', 'l', 'o', 'h' -> 2
        'w', 'z', 'b', 'm' -> 3
        'p', 'c', 'f', 'k' -> 4
        'ä', 'j', 'ü', 'v' -> 5
        'ö', 'x' -> 8
        'q', 'y' -> 10
        else -> 0
    }
}