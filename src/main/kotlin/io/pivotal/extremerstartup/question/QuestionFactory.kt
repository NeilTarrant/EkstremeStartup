package io.pivotal.extremerstartup.question

import io.pivotal.extremerstartup.player.Player

interface QuestionFactory {

    var level: Int

    fun nextQuestion(player: Player): Question

    fun setWarmUpMode()

}

