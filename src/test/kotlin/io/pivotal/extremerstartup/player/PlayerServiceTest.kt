package io.pivotal.extremerstartup.player

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.springframework.test.context.junit4.SpringRunner
import java.net.URL

@RunWith(SpringRunner::class)
class PlayerServiceTest {

    @Mock
    lateinit var playerRepo: PlayerRespository

    @Mock
    lateinit var playerIdGenerator: PlayerIdGenerator

    lateinit var service: PlayerService

    @Before
    fun setUp() {
        service = PlayerService(playerRepo, playerIdGenerator)
    }

    @Test
    fun `store player stores a player in the repo`() {
        whenever(playerIdGenerator.nextId()).thenReturn(101L)
        val playerForm = PlayerForm(name = "name", url = URL("http://pivotal.io"))
        val expectedPlayer = Player(101L, playerForm.name, playerForm.url)

        val actualPlayer = service.storePlayer(playerForm)

        assertThat(actualPlayer).isEqualTo(expectedPlayer)

        verify(playerIdGenerator).nextId()
        verify(playerRepo).save(expectedPlayer)
    }

    @Test
    fun `listAll gets all players from the repo`() {
        val expectedPlayers = listOf(
                Player(101L, "Name", URL("http://name.io")),
                Player(202L, "Name 2", URL("http://name2.io"))
        )

        whenever(playerRepo.findAll()).thenReturn(
                expectedPlayers
        )

        val actualPlayers = service.listAll()

        assertThat(actualPlayers).isEqualTo(expectedPlayers)
    }

    @Test
    fun `getPlayer gets the players from the repo`() {
        val playerId = 101L
        val expectedPlayer = Player(playerId, "Name", URL("http://name.io"))

        whenever(playerRepo.findById(playerId)).thenReturn(
                expectedPlayer
        )

        val actualPlayer = service.getPlayer(playerId)

        assertThat(actualPlayer).isEqualTo(expectedPlayer)
    }

    @Test
    fun `removePlayer removed the players from the repo`() {
        val playerId = 101L

        service.removePlayer(playerId)

        verify(playerRepo).deleteById(playerId)
    }

    @Test
    fun `change score updates the score in the repo`() {
        val playerId = 101L
        val player = Player(playerId, "Name", URL("http://name.io"), 100)

        service.changeScore(player, -20)

        verify(playerRepo).updateScore(Player(playerId, "Name", URL("http://name.io"), 100), -20)
    }

    @Test
    fun `log result adds a result in the repo`() {
        val playerId = 101L
        val player = Player(playerId, "Name", URL("http://name.io"))

        service.logResult(player, Log("QUESTION", "ANSWER", "RESPONSE", "RESULT", 0))

        verify(playerRepo).addLog(player, Log("QUESTION", "ANSWER", "RESPONSE", "RESULT", 0))
    }
}