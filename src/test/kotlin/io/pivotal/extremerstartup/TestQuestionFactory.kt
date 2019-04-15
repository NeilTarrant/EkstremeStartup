package io.pivotal.extremerstartup

import io.pivotal.extremerstartup.player.Player
import io.pivotal.extremerstartup.question.Question
import io.pivotal.extremerstartup.question.QuestionFactory
import io.pivotal.extremerstartup.question.QuestionFactoryImplementation

class TestQuestionFactory : QuestionFactory {

    val defaultQuestionFactory = QuestionFactoryImplementation()

    var testMode = false;

    var questionText = "TEST QUESTION"
    var answer = "TEST"

    override fun nextQuestion(player: Player): Question {
        return when {
            testMode -> Question(questionText, answer)
            else -> defaultQuestionFactory.nextQuestion(player)
        }
    }

    fun setWarmUpMode() {
        this.defaultQuestionFactory.setWarmUpMode()
    }

    fun reset() {
        testMode = false
    }

}
