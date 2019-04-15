package io.pivotal.extremerstartup.player

import org.springframework.stereotype.Service

@Service
class PlayerService(
        val playerRepo: PlayerRespository,
        val playerIdGenerator: PlayerIdGenerator
) {
    fun storePlayer(playerForm: PlayerForm): Player {
        val id = playerIdGenerator.nextId()
        val player = Player(id, playerForm.name, playerForm.url)
        playerRepo.save(player)
        return player
    }

    fun listAll(): List<Player> {
        return playerRepo.findAll()
    }

    fun getPlayer(playerId: Long): Player? {
        return playerRepo.findById(playerId)
    }

    fun removePlayer(playerId: Long) {
        playerRepo.deleteById(playerId)
    }

    fun changeScore(player: Player, points: Int) {
        player.score += points
        playerRepo.updateScore(player)
    }

    fun logResult(player: Player, log: Log) {
        playerRepo.addLog(player, log)
    }
}

