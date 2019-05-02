package io.pivotal.extremerstartup.question

import io.pivotal.extremerstartup.player.Player
import org.springframework.stereotype.Component
import java.time.Instant
import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

@Component
class QuestionFactoryImplementation() : QuestionFactory {

    val maxLevel: Int
        get() = questionTypes.size / 2

    override val levels: List<Int>
        get() = (0..maxLevel).toList()

    final var random: Random = Random(Instant.now().toEpochMilli())

    private var warmUpMode: Boolean = false

    private val questionTypes = listOf<KClass<out Question>>(AdditionQuestion::class,
            MaximumQuestion::class,
            MultiplicationQuestion::class,
            SquareCubeQuestion::class,
            MinimumQuestion::class,
            GeneralKnowledgeQuestion::class,
            PrimesQuestion::class,
            SubtractionQuestion::class,
            FibonacciQuestion::class,
            PowerQuestion::class,
            AdditionAdditionQuestion::class,
            AdditionMultiplicationQuestion::class,
            MultiplicationAdditionQuestion::class,
            AnagramQuestion::class,
            ScrabbleQuestion::class
    )

    override var level: Int = 1

    override fun nextQuestion(player: Player): Question {
        return if (warmUpMode) {
            WarmUpQuestion(player)
        } else {
            val questionType = getQuestionType()
            when {
                questionType < questionTypes.size -> (questionTypes[questionType]).primaryConstructor!!.call(random)
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

