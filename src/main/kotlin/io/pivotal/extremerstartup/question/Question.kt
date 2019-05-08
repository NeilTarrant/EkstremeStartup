package io.pivotal.extremerstartup.question

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.pivotal.extremerstartup.player.Player
import org.springframework.core.io.ClassPathResource
import kotlin.random.Random

abstract class Question(
        open val question: String = "TEST QUESTION",
        open val answer: Any = "TEST ANSWER",
        open val points: Int = 10
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Question

        if (question != other.question) return false
        if (answer != other.answer) return false
        if (points != other.points) return false

        return true
    }

    override fun hashCode(): Int {
        var result = question.hashCode()
        result = 31 * result + answer.hashCode()
        result = 31 * result + points
        return result
    }

    override fun toString(): String {
        return "Question(question='$question', answer='$answer', points=$points)"
    }
}

class BasicQuestion(
        override val question: String,
        override val answer: String,
        override val points: Int
) : Question(
        question,
        answer,
        points
)

class WarmUpQuestion(val player: Player) : Question(
        "what is your name",
        player.name,
        1
)

class GeneralKnowledgeQuestion(random: Random) : Question() {
    val questions: List<ExternalQuestion>
    override val question: String
    override val answer: String
    override val points = 45

    init {
        questions = loadGeneralKnowledgeFromFile()
        val num = random.nextInt(questions.size)
        question = questions[num].question
        answer = questions[num].answer
    }
}

class FibonacciQuestion(random: Random) : Question() {
    val index = random.nextInt(1, 92)
    override val question = "what is the ${index.ordinal()} number in the Fibonacci sequence"
    override val answer = nthFibonacciNumber(index)
    override val points = 50
}

class BinaryQuestion(random: Random) : Question() {
    val num = random.nextInt(0, 256)
    override val question = "what is ${num} in binary"
    override val answer = num.toString(2)
    override val points = 10
}

class HexQuestion(random: Random) : Question() {
    val num = random.nextInt(0, 256)
    override val question = "what is ${num} in hexadecimal"
    override val answer = num.toString(16)
    override val points = 10
}

class BaseQuestion(random: Random) : Question() {
    val num = random.nextInt(0, 256)
    val radix = random.nextInt(2, 10)
    override val question = "what is ${num} in base ${radix}"
    override val answer = num.toString(radix)
    override val points = 30
}

class SquareQuestion(random: Random) : Question() {
    val num = random.nextInt(1, 92)
    override val question = "what is ${num} squared"
    override val answer = num.times(num)
    override val points = 10
}

class DigitSumQuestion(random: Random) : Question() {
    val num = random.nextInt(1, 1_000_000_000)
    override val question = "what is the sum of the digits in ${num} "
    override val answer = num.digitSum()
    override val points = 20
}

class DigitProductQuestion(random: Random) : Question() {
    val num = random.nextInt(1, 1_000_000_000)
    override val question = "what is the product of the digits in ${num} "
    override val answer = num.digitProduct()
    override val points = 20
}

class AnagramQuestion(random: Random) : Question() {
    override val question: String
    override val answer: String
    override val points = 45

    init {
        val anagrams = loadAnagramsFromFile()
        val num = random.nextInt(anagrams.size)
        val anagram = anagrams[num]
        val answers = anagram.incorrect.plus(anagram.correct).shuffled()
        question = "which of the following is an anagram of ${anagram.anagram}: ${answers.joinToString(", ")}"
        answer = anagram.correct
    }
}

class WordInSentenceQuestion(random: Random) : Question() {
    override val question: String
    override val answer: String
    override val points = 30

    init {
        val index = random.nextInt(8)
        question = "what is the ${(index + 1).ordinal()} word in this sentence"
        answer = question.split(' ')[index]
    }
}

class LetterOfAlphabetQuestion(random: Random) : Question() {
    val index = random.nextInt(0, 26)
    override val question = "what is the ${(index + 1).ordinal()} letter of the alphabet"
    override val answer = "abcdefghijklmnopqrstuvwxyz".toList()[index]
    override val points = 10
}

class LetterOfWordQuestion(random: Random) : Question() {
    val num = random.nextInt(ENGLISH_WORD_LIST.size)
    val word = ENGLISH_WORD_LIST[num]
    val index = random.nextInt(0, word.length)
    override val question = "what is the ${(index + 1).ordinal()} letter of the word ${word}"
    override val answer = word.toList()[index]
    override val points = 10
}

val fibs = mutableMapOf<Int, Long>()

fun nthFibonacciNumber(n: Int): Long {
    return when (n) {
        1 -> 1L
        2 -> 1L
        else -> fibs.getOrPut(n - 1) { nthFibonacciNumber(n - 1) } + fibs.getOrPut(n - 2) { nthFibonacciNumber(n - 2) }
    }
}

fun loadGeneralKnowledgeFromFile(): List<ExternalQuestion> {
    val mapper = ObjectMapper(YAMLFactory()) // Enable YAML parsing
    mapper.registerModule(KotlinModule()) // Enable Kotlin support

    val ref = mapper.typeFactory.constructCollectionType(MutableList::class.java, ExternalQuestion::class.java)

    return ClassPathResource("questions/general-knowledge.yaml").file.bufferedReader().use { mapper.readValue<List<ExternalQuestion>>(it, ref) }
}

fun loadAnagramsFromFile(): List<Anagram> {
    val mapper = ObjectMapper(YAMLFactory()) // Enable YAML parsing
    mapper.registerModule(KotlinModule()) // Enable Kotlin support

    val ref = mapper.typeFactory.constructCollectionType(MutableList::class.java, Anagram::class.java)

    return ClassPathResource("questions/anagrams.yaml").file.bufferedReader().use { mapper.readValue<List<Anagram>>(it, ref) }
}


data class ExternalQuestion(
        val question: String,
        val answer: String
)

data class Anagram(
        val anagram: String,
        val correct: String,
        val incorrect: List<String>
)