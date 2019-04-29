package io.pivotal.extremerstartup.cucumbersteps

import cucumber.api.Scenario
import cucumber.api.java.After
import cucumber.api.java.Before
import cucumber.api.java.en.Given
import org.junit.Ignore
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Ignore
class ServerSetupSteps : SpringCucumberBaseSteps() {

    @LocalServerPort
    val port = 3000

    @Before
    fun beforeScenario(scenario: Scenario) {
        resetPlayerDB()
    }

    @After
    fun afterScenario(scenario: Scenario) {
        if (scenario.isFailed) {
            println("""FAILED URL: ${driver.currentUrl}""")
            println("""PAGE SOURCE: ${driver.pageSource}""")
        }
    }

    @Given("^I want to play the game\$")
    fun `I want to play the game`() {
        navigateToAddPlayerPage()
    }
}