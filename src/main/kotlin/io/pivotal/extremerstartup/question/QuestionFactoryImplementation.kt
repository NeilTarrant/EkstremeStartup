package io.pivotal.extremerstartup.question

import io.pivotal.extremerstartup.player.Player
import org.springframework.stereotype.Component
import java.time.Instant
import kotlin.random.Random
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.starProjectedType

@Component
class QuestionFactoryImplementation : QuestionFactory {

    val maxLevel: Int
        get() = questionTypes.size / 2

    override val levels: List<Int>
        get() = (0..maxLevel).toList()

    final var random: Random = Random(Instant.now().toEpochMilli())

    private var warmUpMode: Boolean = false

    private val questionTypes = listOf(
            GermanTranslationQuestion::class,
            LongestWordQuestion::class,
            ShortestWordQuestion::class,
            HighestScoringWord::class,
            LowestScoringWord::class,
            MostVowels::class,
            LeastVowels::class,
            FirstAlphabetically::class,
            LastAlphabetically::class
//            WarmUpQuestion::class,
//            AdditionQuestion::class,
//            TrickQuestion::class,
//            SquareQuestion::class,
//            DigitSumQuestion::class,
//            LetterOfAlphabetQuestion::class,
//            BinaryQuestion::class,
//            DigitProductQuestion::class,
//            HexQuestion::class,
//            GermanScrabbleQuestion::class,
//            LetterOfWordQuestion::class,
//            BaseQuestion::class,
//            WordInSentenceQuestion::class,
//            WarmUpQuestion::class,
//            SubtractionQuestion::class,
//            MinimumQuestion::class,
//            MultiplicationQuestion::class,
//            AdditionQuestion::class,
//            DigitSumQuestion::class,
//            SquareQuestion::class,
//            GeneralKnowledgeQuestion::class,
//            SquareCubeQuestion::class,
//            PrimesQuestion::class,
//            DigitProductQuestion::class,
//            MaximumQuestion::class,
//            FibonacciQuestion::class,
//            PowerQuestion::class,
//            AdditionAdditionQuestion::class,
//            AdditionMultiplicationQuestion::class,
//            MultiplicationAdditionQuestion::class,
//            AnagramQuestion::class,
//            EnglishScrabbleQuestion::class,
//            GermanScrabbleQuestion::class
    )

    override var level: Int = 0

    override fun nextQuestion(player: Player): Question {
        return if (warmUpMode) {
            WarmUpQuestion(player)
        } else {
            val questionType = getQuestionType()
            when {
                questionType < questionTypes.size -> makeQuestion(questionType, player)
                else -> WarmUpQuestion(player)
            }
        }
    }

    private fun makeQuestion(questionType: Int, player: Player): Question {
        val kClass = questionTypes[questionType]

        val parameters = kClass.primaryConstructor!!.parameters
        return when {
            parameters.isEmpty() -> kClass.primaryConstructor!!.call()
            parameters.first().type == Player::class.starProjectedType -> kClass.primaryConstructor!!.call(player)
            parameters.first().type == Random::class.starProjectedType -> kClass.primaryConstructor!!.call(random)
            parameters.first().type == Question::class.starProjectedType -> kClass.primaryConstructor!!.call(this.nextQuestion(player), random)
            else -> throw UnknownParameterInQuestionConstructor()
        }
    }

    override fun setWarmUpMode() {
        this.warmUpMode = true
    }

    private fun getQuestionType(): Int {
        return when {
            level == 1 -> random.nextInt(0, 2)
            level == 2 -> random.nextInt(0, 4)
            level > 2 -> random.nextInt(level * 2 - 5, level * 2)
            else -> 0
        }
    }
}

class UnknownParameterInQuestionConstructor : Throwable() {

}

