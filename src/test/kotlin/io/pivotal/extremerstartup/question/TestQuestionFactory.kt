package io.pivotal.extremerstartup.question

import io.pivotal.extremerstartup.player.Player
import org.springframework.stereotype.Component

@Component
class TestQuestionFactory : QuestionFactory {
    override val levels: List<Int>
        get() = defaultQuestionFactory.levels

    final val defaultQuestionFactory: QuestionFactory

    var testMode = false

    var questionText = "TEST QUESTION"
    var answer = "TEST"
    var points = 10

    override var level = 0

    override fun nextQuestion(player: Player): Question {
        return when {
            testMode -> BasicQuestion(questionText, answer, points)
            else -> defaultQuestionFactory.nextQuestion(player)
        }
    }

    override fun setWarmUpMode() {
        this.defaultQuestionFactory.setWarmUpMode()
    }

    fun reset() {
        testMode = false
    }

    init {
        defaultQuestionFactory = QuestionFactoryImplementation()
    }

}
