package io.pivotal.extremerstartup.question

import io.pivotal.extremerstartup.player.Player
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class QuestionFactoryImplementation(val random: Random) : QuestionFactory {

    private var warmUpMode: Boolean = false

    private val questionTypes = listOf<Question>(AdditionQuestion(random),
            MaximumQuestion(random),
            MultiplicationQuestion(random),
            SquareCubeQuestion(random),
            MinimumQuestion(random),
            GeneralKnowledgeQuestion(random),
            PrimesQuestion(random),
            SubtractionQuestion(random),
            FibonacciQuestion(random),
            PowerQuestion(random),
            AdditionAdditionQuestion(random),
            AdditionMultiplicationQuestion(random),
            MultiplicationAdditionQuestion(random),
            AnagramQuestion(random),
            ScrabbleQuestion(random)
    )

    override var level: Int = 1

    override fun nextQuestion(player: Player): Question {
        return if (warmUpMode) {
            WarmUpQuestion(player)
        } else {
            val questionType = getQuestionType()
            when {
                questionType < questionTypes.size -> questionTypes[questionType]
                else -> WarmUpQuestion(player)
            }
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

