package io.pivotal.extremerstartup

import io.pivotal.extremerstartup.player.Player
import io.pivotal.extremerstartup.player.PlayerService
import io.pivotal.extremerstartup.question.QuestionFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/admin")
class AdminController(
        val playerService: PlayerService,
        val questionFactory: QuestionFactory
) {

    @GetMapping
    fun showLeaderboard(): String {
        return "admin"
    }

    @PostMapping("level")
    fun setLevel(
            @ModelAttribute levelForm: LevelForm,
            model: Model
    ): String {
        println("setting level to ${levelForm.level}")
        this.questionFactory.level = levelForm.level
        return "redirect:/admin"
    }

    @ModelAttribute("questionLevel")
    fun questionLevel(): Int {
        return this.questionFactory.level
    }

    @ModelAttribute("levels")
    fun getLevels(): List<Int> {
        return this.questionFactory.levels
    }

    @ModelAttribute("allPlayers")
    fun populatePlayers(): List<Player> {
        return this.playerService.listAll().sortedByDescending { it.score }
    }
}

data class LevelForm(
        val level: Int
)

