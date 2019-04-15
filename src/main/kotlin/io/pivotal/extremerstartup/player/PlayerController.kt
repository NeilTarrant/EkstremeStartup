package io.pivotal.extremerstartup.player

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.net.URL

@Controller
@RequestMapping("/players")
class PlayerController(val playerService: PlayerService) {

    @GetMapping
    fun addPlayerForm(): String {
        return "add_player"
    }

    @PostMapping
    fun addPlayer(
            @ModelAttribute playerForm: PlayerForm,
            model: Model
    ): String {
        val player = playerService.storePlayer(playerForm)
        model.addAttribute("player", player)
        return "player_added"
    }

    @GetMapping("/{id}")
    fun getPlayer(
            @PathVariable("id") id: Long,
            model: Model
    ): String {
        val player = playerService.getPlayer(id)
        if (player != null) {
            model.addAttribute("player", player)
            return "personal_page"
        }
        return "no_such_player"
    }

    @GetMapping("/{id}/withdraw")
    fun withdrawPlayer(
            @PathVariable("id") id: Long,
            model: Model
    ): String {
        playerService.removePlayer(id)
        return "redirect:/"
    }

}

data class PlayerForm(
        val name: String,
        val url: URL
)
