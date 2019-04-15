package io.pivotal.extremerstartup.player

import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class PlayerIdGenerator {

    private val random = Random.Default

    fun nextId(): Long {
        return random.nextLong()
    }

}
