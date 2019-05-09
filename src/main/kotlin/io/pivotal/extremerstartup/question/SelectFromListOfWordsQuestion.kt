package io.pivotal.extremerstartup.question

abstract class SelectFromListOfWordsQuestion(
        private val numOptions: Int,
        val groupBy: (String) -> Any = { it: String -> it },
        val selectAnswer: (List<String>) -> String = { it -> it.first() }
) : Question() {

    val words: List<String>
    final override val answer: String

    init {
        val groupedWords = ENGLISH_WORD_LIST.groupBy(groupBy)

        val toIndex = if (numOptions < groupedWords.size) numOptions else groupedWords.size
        val selectedKeys = groupedWords.keys.shuffled().subList(0, toIndex)
        words = selectedKeys.map { groupedWords.getValue(it).random() }

        answer = selectAnswer(words)
    }
}

class LongestWordQuestion() : SelectFromListOfWordsQuestion(
        5,
        String::length,
        { it.longest() }) {
    override val question = "which of these words is longest: ${words.joinToString(", ")}"
}

class ShortestWordQuestion() : SelectFromListOfWordsQuestion(
        5,
        String::length,
        { it.shortest() }) {
    override val question = "which of these words is shortest: ${words.joinToString(", ")}"
}

class HighestScoringWord() : SelectFromListOfWordsQuestion(
        5,
        { it.map { it.englishScrabbleScore() }.sum() },
        { it.sortedBy { it.map { it.englishScrabbleScore() }.sum() }.last() }) {
    override val question = "which of these words is worth most in English scrabble: ${words.joinToString(", ")}"
}

class LowestScoringWord() : SelectFromListOfWordsQuestion(
        5,
        { it.map { it.englishScrabbleScore() }.sum() },
        { it.sortedBy { it.map { it.englishScrabbleScore() }.sum() }.first() }) {
    override val question = "which of these words is worth least in English scrabble: ${words.joinToString(", ")}"
}

class MostVowels() : SelectFromListOfWordsQuestion(
        5,
        { it.vowelCount() },
        { it.sortedBy { it.vowelCount() }.last() }) {
    override val question = "which of these words has most vowels: ${words.joinToString(", ")}"
}

class LeastVowels() : SelectFromListOfWordsQuestion(
        5,
        { it.vowelCount() },
        { it.sortedBy { it.vowelCount() }.first() }) {
    override val question = "which of these words has least vowels: ${words.joinToString(", ")}"
}

class FirstAlphabetically() : SelectFromListOfWordsQuestion(
        5,
        { it },
        { it.sorted().first() }
) {
    override val question = "which of these words is first alphabetically: ${words.joinToString(", ")}"
}

class LastAlphabetically() : SelectFromListOfWordsQuestion(
        5,
        { it },
        { it.sorted().first() }
) {
    override val question = "which of these words is last alphabetically: ${words.joinToString(", ")}"
}

fun List<String>.longest(): String = this.sortedBy(String::length).last()
fun List<String>.shortest(): String = this.sortedBy(String::length).first()

fun String.vowelCount(): Int {
    return this.count { it.isVowel() }
}

fun Char.isVowel(): Boolean {
    return when (this) {
        'a', 'e', 'i', 'o', 'u' -> true
        else -> false
    }
}