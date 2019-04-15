package io.pivotal.extremerstartup.cucumbersteps

import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.assertj.core.api.Assertions.assertThat
import java.net.URL

class TeamSteps : SpringCucumberBaseSteps() {

    var playerId: Long? = null

    @When("^I submit my team name and the url of my computer\$")
    fun `I submit my team name and the url of my computer`() {
        submitTeamNameAndUrl(playerName, playerURL)
    }

    @Then("^I should see that my team was added\$")
    fun `I should see that my team was added`() {
        assertThat(elByClass("message").text).isEqualTo("Player added - Thanks")
    }

    @Then("^I should see my team on the leaderboard\$")
    fun `I should see my team on the leaderboard`() {
        goTo("/")
        assertThat(allByClass("player").size).isEqualTo(1)
        assertThat(allByClass("name")[0].text).isEqualTo("Jedi masters")
    }

    @Given("^I submitted my team info\$")
    fun `I submitted my team info`() {
        navigateToAddPlayerPage()
        submitTeamNameAndUrl(playerName, playerURL)
    }

    @Then("^I should receive a link to my market requests log\$")
    fun `I should receive a link to my market requests log`() {
        assertThat(elByTag("a").isDisplayed).isTrue()
        elByTag("a").click()
        assertThat(elByTag("h1").text).isEqualTo("Hello, Jedi masters")
    }

    @Given("^I am playing\$")
    fun `I am playing`() {
        navigateToAddPlayerPage()
        submitTeamNameAndUrl(playerName, playerURL)
    }

    @Then("^the game master should start sending me question\$")
    fun `the game master should start sending me questions`() {
        val player = PlayerServer(playerName = "A Player", serverURL = URL(this.playerURL), body = "")
        player.start()
        Thread.sleep(5000L)
        player.hasRecievedQuestions()
        player.stop()
    }

    @When("^I withdraw\$")
    fun `I withdraw`() {
        playerId = getPlayerIds()[0]
        goTo("/players/$playerId")
        elById("withdraw").click()
    }

    @Then("^my name should not be on the leaderboard anymore\$")
    fun `my name should not be on the leaderboard anymore`() {
        goTo("/")
        val allNames = allByClass("name").map { it.text }
        assertThat(allNames).doesNotContain(playerName)
    }

    @Then("^the game master should not send me anymore questions\$")
    fun `the game master should not send me anymore questions`() {
        val player = PlayerServer(playerName = "A Player", serverURL = URL(this.playerURL), body = "")
        player.start()
        Thread.sleep(5000L)
        player.hasRecievedNoQuestions()
        player.stop()
    }

    @Then("^my player page should give a nice error\$")
    fun `my player page should give a nice error`() {
        goTo("/players/$playerId")
        assertThat(elByTag("h1").text).isEqualTo("No Such Player")
        assertThat(elByTag("p").text).isEqualTo("No player is registered with that id. Perhaps the game was restarted? You may need to re-register.")
    }

}
