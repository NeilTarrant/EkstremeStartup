package io.pivotal.extremerstartup.question

import io.pivotal.extremerstartup.player.Player
import org.springframework.stereotype.Component

@Component
class TestQuestionFactory : QuestionFactory {

    final val defaultQuestionFactory: QuestionFactory

    var testMode = false

    var questionText = "TEST QUESTION"
    var answer = "TEST"

    override var level = 0

    override fun nextQuestion(player: Player): Question {
        return when {
            testMode -> BasicQuestion(questionText, answer, 10)
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
