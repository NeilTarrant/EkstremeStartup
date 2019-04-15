package io.pivotal.extremerstartup.player

import org.springframework.stereotype.Repository

@Repository
class PlayerRespository {

    private val players: MutableList<Player> = mutableListOf()

    fun save(player: Player) {
        removePlayerWithId(player.id)
        players.add(player)
    }

    private fun removePlayerWithId(playerId: Long) {
        val existingPlayer = players.find { it.id == playerId }
        players.remove(existingPlayer)
    }

    fun findAll(): List<Player> {
        return players
    }

    fun deleteAll() {
        players.clear()
    }

    fun findById(playerId: Long): Player? {
        return players.find { it.id == playerId }
    }

    fun deleteById(playerId: Long) {
        players.removeIf { it.id == playerId }
    }

    fun updateScore(player: Player) {
        players.find { it.id == player.id }.also { it?.score = player.score }
    }

    fun addLog(player: Player, log: Log) {
        players.find { it.id == player.id }.also { it?.logs?.add(log) }
    }
}
