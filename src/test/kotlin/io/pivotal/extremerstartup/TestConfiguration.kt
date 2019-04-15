package io.pivotal.extremerstartup

import io.pivotal.extremerstartup.question.QuestionFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class testConfiguration() {

    @Bean
    fun WebDriver(): WebDriver {
        return ChromeDriver()
    }

    @Bean
    @Primary
    fun questionFactory(): QuestionFactory {
        return TestQuestionFactory()
    }
}