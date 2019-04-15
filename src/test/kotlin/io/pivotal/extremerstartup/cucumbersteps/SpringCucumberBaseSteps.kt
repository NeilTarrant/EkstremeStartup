package io.pivotal.extremerstartup.cucumbersteps

import io.pivotal.extremerstartup.player.Player
import io.pivotal.extremerstartup.player.PlayerRespository
import io.pivotal.extremerstartup.question.QuestionFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.springframework.beans.factory.annotation.Autowired

abstract class SpringCucumberBaseSteps {
    @Autowired
    lateinit var playerRespository: PlayerRespository

    @Autowired
    lateinit var driver: WebDriver

    @Autowired
    lateinit var questionFactory: QuestionFactory

    private val baseUrl = "http://localhost:3000"

    val playerName = "Jedi masters"
    val playerHost = "localhost"
    val playerPort = 9001

    val playerURL = "http://$playerHost:$playerPort/"

    fun resetPlayerDB() {
        playerRespository.deleteAll()
    }

    fun getPlayerIds(): List<Long> {
        return playerRespository.findAll().map(Player::id)
    }

    fun navigateToAddPlayerPage() {
        goTo()
        elById("add_player").click()
    }

    fun submitTeamNameAndUrl(teamName: String, teamURL: String) {
        elById("name").sendKeys(teamName)
        elById("url").sendKeys(teamURL)
        elById("add-player-submit").click()
    }

    fun goTo(suffix: String = "") = driver.get(baseUrl + suffix)
    fun elById(id: String) = driver.findElement(By.id(id))
    fun elByClass(className: String) = driver.findElement(By.className(className))
    fun allByClass(className: String) = driver.findElements(By.className(className))
    fun elByTag(tagName: String) = driver.findElement(By.tagName(tagName))
    fun allByTag(tagName: String) = driver.findElements(By.tagName(tagName))

    fun WebElement.elByClass(className: String) = this.findElement(By.className(className))
}