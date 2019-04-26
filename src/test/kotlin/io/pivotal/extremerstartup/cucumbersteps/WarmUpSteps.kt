package io.pivotal.extremerstartup.cucumbersteps

import cucumber.api.java.en.Given
import io.pivotal.extremerstartup.question.TestQuestionFactory


class WarmUpSteps : SpringCucumberBaseSteps() {
    @Given("^the server is running in warm\\-up mode\$")
    fun `the server is running in warm-up mode`() {
        (questionFactory as TestQuestionFactory).setWarmUpMode()
    }
}