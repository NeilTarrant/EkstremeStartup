package io.pivotal.extremerstartup.player

import org.springframework.stereotype.Repository
import java.net.URL

@Repository
class PlayerRespository {

    private val players: MutableList<Player> = mutableListOf(
            Player(0L, "Jax2019", URL("http://localhost:5000"))
    )

//            mutableListOf(
//            Player(0L, "Neil", URL("http://localhost:5000")),
//            Player(0L, "Aarti", URL("http://localhost:5001")),
//            Player(0L, "Kaveh", URL("http://localhost:5002")),
//            Player(0L, "Andreas", URL("http://localhost:5003")),
//            Player(0L, "Nikhil", URL("http://localhost:5004")),
//            Player(0L, "Max", URL("http://localhost:5005")),
//            Player(0L, "Oleksii", URL("http://localhost:5006")),
//            Player(0L, "Robert", URL("http://localhost:5007"))
//    )

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

    fun updateScore(player: Player, points: Int) {
        val storedPlayer = players.find { it.id == player.id }

        if (storedPlayer != null) {
            players.remove(storedPlayer)
            players.add(storedPlayer.copy(score = player.score + points))
        }
    }

    fun addLog(player: Player, log: Log) {
        players.find { it.id == player.id }?.also {
            if (it.logs.size > 19) {
                it.logs.removeAt(19)
            }
            it.logs.add(0, log)
        }
    }
}
