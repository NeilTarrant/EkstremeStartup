package io.pivotal.extremerstartup.cucumbersteps

import cucumber.api.java.After
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import io.pivotal.extremerstartup.question.TestQuestionFactory
import org.assertj.core.api.Assertions.assertThat
import java.net.URL


class Steps : SpringCucumberBaseSteps() {

    @After
    fun tearDown() {
        (questionFactory as TestQuestionFactory).testMode = false
    }

    var players: MutableList<PlayerServer> = mutableListOf()

    @Given("^a player \"([^\"]*)\" who replies \"([^\"]*)\" with a status of \"(\\d+)\":\$")
    fun `Given a player who`(playerName: String, content: String, status: Int) {
        val port = playerPort + players.size
        val serverURL = URL("http://$playerHost:$port/")

        players.add(PlayerServer(playerName, serverURL, content, status))
    }

    @Given("the correct answer to every question is '(\\d+)'\$")
    fun `the correct answer to every question is`(answer: String) {
        (questionFactory as TestQuestionFactory).answer = answer
    }

    @Given("the correct answer to every question is '(\\d+)' worth (\\d+) points\$")
    fun `the correct answer to every question is`(answer: String, points: Int) {
        val testQuestionFactory = questionFactory as TestQuestionFactory
        testQuestionFactory.testMode = true
        testQuestionFactory.answer = answer
        testQuestionFactory.points = points
    }

    @When("^the (?:player is|players are) entered\$")
    fun `the player is entered`() {
        players.forEach {
            it.start()
            goTo("/players")
            submitTeamNameAndUrl(it.playerName, it.serverURL.toString())
        }
    }

    @When("^the game is played for (\\d+) second\$")
    fun `the game is played for seconds`(numSeconds: Long) {
        Thread.sleep(numSeconds * 1000)
        players.forEach { it.stop() }
    }

    @Then("^the scores should be:\$")
    fun `the scores should be`(expectedScores: List<Score>) {
        goTo("/")
        val displayedScores = allByClass("player").map {
            Score(
                    it.elByClass("name").text,
                    it.elByClass("score").text
            )
        }

        assertThat(displayedScores).hasSize(expectedScores.size)
        expectedScores.forEach { assertThat(displayedScores).contains(it) }
    }

    data class Score(val player: String, val score: String)

    @Then("^the log for (.+) should show:\$")
    fun `the log should show`(playerName: String, expectedLogs: List<LogLine>) {
        val playerId = playerRespository.findAll().find { it.name == playerName }?.id
        goTo("/players/${playerId}")
        val actualLogs = allByClass("log").map {
            println(it)
            println(it.text)
            LogLine(
                    it.elByClass("question").text,
                    it.elByClass("response").text,
                    it.elByClass("points").text
            )
        }

        assertThat(actualLogs.size).isEqualTo(expectedLogs.size)
        actualLogs.forEachIndexed { index, log ->
            assertThat(log).isEqualTo(expectedLogs[index])
        }
    }

}

data class LogLine(
        val question: String,
        val response: String,
        val points: String
)
