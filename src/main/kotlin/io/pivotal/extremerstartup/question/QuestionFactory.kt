package io.pivotal.extremerstartup.question

import io.pivotal.extremerstartup.player.Player

interface QuestionFactory {

    val levels: List<Int>

    var level: Int

    fun nextQuestion(player: Player): Question

    fun setWarmUpMode()

}

