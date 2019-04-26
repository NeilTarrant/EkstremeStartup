package io.pivotal.extremerstartup

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.http.client.ClientHttpResponse
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.client.DefaultResponseErrorHandler
import org.springframework.web.client.RestTemplate
import java.time.Instant
import kotlin.random.Random


@SpringBootApplication
@EnableScheduling
class ExtremerstartupApplication {
    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder
                .errorHandler(NoOpDefaultResponseErrorHandler())
                .build()
    }

    @Bean
    fun random(): Random {
        return Random(Instant.now().toEpochMilli())
    }
}

fun main(args: Array<String>) {
    runApplication<ExtremerstartupApplication>(*args)
}

class NoOpDefaultResponseErrorHandler : DefaultResponseErrorHandler() {
    override fun handleError(response: ClientHttpResponse) {
    }
}
