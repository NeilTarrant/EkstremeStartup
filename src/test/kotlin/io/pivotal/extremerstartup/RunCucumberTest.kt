package io.pivotal.extremerstartup

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.BeforeClass
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(plugin = ["pretty"])
class RunCucumberTest {

    companion object {
        @JvmStatic
        @BeforeClass
        fun setup() {
            WebDriverManager.chromedriver().setup()
        }
    }

}