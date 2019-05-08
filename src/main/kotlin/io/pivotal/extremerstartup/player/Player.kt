package io.pivotal.extremerstartup.player

import java.net.URL

data class Player(
        val id: Long,
        val name: String,
        val url: URL,
        val score: Int = 0,
        val logs: MutableList<Log> = mutableListOf()
)

data class Log(
        val question: String,
        val answer: Any,
        val response: String,
        val result: String,
        val points: Int
)
