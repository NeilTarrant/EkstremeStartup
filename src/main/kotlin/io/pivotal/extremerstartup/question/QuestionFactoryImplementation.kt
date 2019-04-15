package io.pivotal.extremerstartup.question

import io.pivotal.extremerstartup.player.Player
import org.springframework.stereotype.Component

@Component
class QuestionFactoryImplementation : QuestionFactory {

    private var warmUpMode: Boolean = false

    override fun nextQuestion(player: Player): Question {
        if (warmUpMode) {
            return Question("what is your name", player.name)
        } else return Question("what is your name", player.name)
    }

    fun setWarmUpMode() {
        this.warmUpMode = true
    }

}

