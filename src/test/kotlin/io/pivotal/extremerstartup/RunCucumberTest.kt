package io.pivotal.extremerstartup

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener
import org.springframework.test.context.TestExecutionListeners

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