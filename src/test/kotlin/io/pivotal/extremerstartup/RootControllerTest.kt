package io.pivotal.extremerstartup

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.pivotal.extremerstartup.player.Player
import io.pivotal.extremerstartup.player.PlayerService
import org.hamcrest.CoreMatchers.containsString
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.net.URL

@RunWith(SpringRunner::class)
@WebMvcTest(controllers = [RootController::class])
@AutoConfigureWebClient
class RootControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var playerService: PlayerService

    @Test
    fun `shows the leaderboard`() {
        whenever(playerService.listAll()).thenReturn(listOf(
                Player(0L, "Team A", URL("http://team-a.io")),
                Player(1L, "Team B", URL("http://team-b.io"))
        ))

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk)
                .andExpect(view().name("leaderboard"))
                .andExpect(content().string(containsString("Team A")))
                .andExpect(content().string(containsString("Team B")))

        verify(playerService).listAll()
    }

}