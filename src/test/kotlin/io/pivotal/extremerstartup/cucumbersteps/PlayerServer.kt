package io.pivotal.extremerstartup.cucumbersteps

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import java.net.URL

class PlayerServer(val playerName: String, val serverURL: URL, val body: String = "TEST", val status: Int = 200) {

    private var server: WireMockServer

    private lateinit var wireMockClient: WireMock

    init {
        server = WireMockServer(serverURL.port)
    }

    fun stop() {
        server.stop()
    }

    fun start() {
        server.start()

        wireMockClient = WireMock(serverURL.port)

        wireMockClient.register(
                WireMock.get(
                        WireMock.anyUrl())
                        .willReturn(WireMock
                                .aResponse()
                                .withStatus(status)
                                .withBody(body)
                        )
        )

    }

    fun hasRecievedQuestions() {
        wireMockClient.verifyThat(WireMock.moreThan(0), WireMock.getRequestedFor(WireMock.anyUrl()))
    }

    fun hasRecievedNoQuestions() {
        wireMockClient.verifyThat(0, WireMock.getRequestedFor(WireMock.anyUrl()))
    }
}