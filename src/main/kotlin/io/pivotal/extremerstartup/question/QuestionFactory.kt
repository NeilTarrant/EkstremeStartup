package io.pivotal.extremerstartup.question

import io.pivotal.extremerstartup.player.Player

interface QuestionFactory {

    fun nextQuestion(player: Player): Question

}

