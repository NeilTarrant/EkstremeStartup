package io.pivotal.extremerstartup

import io.pivotal.extremerstartup.player.Player
import io.pivotal.extremerstartup.player.PlayerService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/")
class RootController(val playerService: PlayerService) {

    @GetMapping
    fun showLeaderboard(): String {
        return "leaderboard"
    }

    @ModelAttribute("allPlayers")
    fun populatePlayers(): List<Player> {
        return this.playerService.listAll()
    }
}