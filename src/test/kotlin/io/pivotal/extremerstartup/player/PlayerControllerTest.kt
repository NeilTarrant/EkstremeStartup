package io.pivotal.extremerstartup.player

import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.CoreMatchers.containsString
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.io.UnsupportedEncodingException
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@RunWith(SpringRunner::class)
@WebMvcTest(controllers = [PlayerController::class])
@AutoConfigureWebClient
class PlayerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var playerService: PlayerService

    @Test
    fun `getting players shows the add player form`() {
        this.mockMvc
                .perform(get("/players"))
                .andExpect(status().isOk)
                .andExpect(view().name("add_player"))
                .andExpect(content().string(containsString("Name:")))
                .andExpect(content().string(containsString("URL:")))
    }

    @Test
    fun `posting to players adds a player`() {
        val playerForm = PlayerForm(name = "name", url = URL("http://pivotal.io"))
        whenever(playerService.storePlayer(playerForm)).thenReturn(
                Player(101L, playerForm.name, playerForm.url)
        )

        this.mockMvc
                .perform(post("/players")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(
                                buildUrlEncodedFormEntity(
                                        "name", "name",
                                        "url", "http://pivotal.io"
                                )
                        ))
                .andExpect(status().isOk)
                .andExpect(view().name("player_added"))
                .andExpect(content().string(containsString("Player added - Thanks")))

        verify(playerService).storePlayer(playerForm)
    }

    @Test
    fun `getting a valid player id shows the player data`() {
        val playerId = 1234L
        whenever(playerService.getPlayer(playerId)).thenReturn(
                Player(playerId, "Team A", URL("http://team-a.io"))
        )

        this.mockMvc
                .perform(get("/players/" + playerId))
                .andExpect(status().isOk)
                .andExpect(view().name("personal_page"))
                .andExpect(content().string(containsString("Hello, Team A")))

        verify(playerService).getPlayer(playerId)
    }

    @Test
    fun `getting an invalid player id shows the no such player page`() {
        val playerId = 1234L
        whenever(playerService.getPlayer(playerId)).thenReturn(null)

        this.mockMvc
                .perform(get("/players/" + playerId))
                .andExpect(view().name("no_such_player"))

        verify(playerService).getPlayer(playerId)
    }

    @Test
    fun `getting a player withdrawl page removes the player data`() {
        val playerId = 1234L

        this.mockMvc
                .perform(get("/players/$playerId/withdraw"))
                .andExpect(status().is3xxRedirection)
                .andExpect(view().name("redirect:/"))

        verify(playerService).removePlayer(playerId)
    }

    private fun buildUrlEncodedFormEntity(vararg params: String): String {
        if (params.size % 2 > 0) {
            throw IllegalArgumentException("Need to give an even number of parameters")
        }
        val result = StringBuilder()
        var i = 0
        while (i < params.size) {
            if (i > 0) {
                result.append('&')
            }
            try {
                result.append(URLEncoder.encode(params[i], StandardCharsets.UTF_8.name())).append('=').append(URLEncoder.encode(params[i + 1], StandardCharsets.UTF_8.name()))
            } catch (e: UnsupportedEncodingException) {
                throw RuntimeException(e)
            }

            i += 2
        }
        return result.toString()
    }
}