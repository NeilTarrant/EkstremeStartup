package io.pivotal.extremerstartup.player

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.net.URL

class PlayerRespositoryTest {

    lateinit var repo: PlayerRespository

    @Before
    fun setUp() {
        repo = PlayerRespository()
    }

    @Test
    fun `A player saved with the save function, is returned when found by ID`() {
        val expectedPlayer = Player(0L, "name", URL("http://example.org"))

        repo.save(expectedPlayer)

        val actualPlayer = repo.findById(0L)

        assertThat(actualPlayer).isEqualTo(expectedPlayer)
    }

    @Test
    fun `A player saved with the save function, is returned with the listAll`() {
        val expectedPlayer = Player(0L, "name", URL("http://example.org"))

        repo.save(expectedPlayer)

        val actualPlayers = repo.findAll()

        assertThat(actualPlayers).hasSize(1)
        assertThat(actualPlayers[0]).isEqualTo(expectedPlayer)
    }

    @Test
    fun `Saving an updated player (with the same ID) does not create a duplicate player`() {
        val initialPlayer = Player(0L, "name", URL("http://example.org"))
        val updatedPlayer = Player(0L, "updated name", URL("http://example.org"))

        repo.save(initialPlayer)

        repo.save(updatedPlayer)

        val actualPlayers = repo.findAll()
        assertThat(actualPlayers).hasSize(1)
        assertThat(actualPlayers[0]).isEqualTo(updatedPlayer)
    }

    @Test
    fun `Deleting all removes all players`() {
        val player1 = Player(0L, "name", URL("http://example.org"))
        val player2 = Player(1L, "another name", URL("http://another.org"))

        repo.save(player1)
        repo.save(player2)

        repo.deleteAll()

        val actualPlayers = repo.findAll()
        assertThat(actualPlayers).hasSize(0)
    }

    @Test
    fun `Deleting by id removes that players`() {
        val player1 = Player(0L, "name", URL("http://example.org"))
        val player2 = Player(1L, "another name", URL("http://another.org"))

        repo.save(player1)
        repo.save(player2)

        repo.deleteById(0L)

        val actualPlayers = repo.findAll()
        assertThat(actualPlayers).hasSize(1)
        assertThat(actualPlayers[0]).isEqualTo(player2)
    }

    @Test
    fun `Updating a player updates in the repo`() {
        val player = Player(0L, "name", URL("http://example.org"), 0)
        repo.save(player)

        val updatedPlayer = Player(0L, "name", URL("http://example.org"), 50)
        repo.updateScore(updatedPlayer)

        val actualUpdatedPlayer = repo.findById(0L)
        assertThat(actualUpdatedPlayer).isEqualTo(updatedPlayer)
    }

    @Test
    fun `Adding a log adds the log in the repo`() {
        val player = Player(0L, "name", URL("http://example.org"), 0, mutableListOf<Log>(
                Log("QUESTION", "ANSWER", "RESPONSE", "RESULT", 1000)
        ))
        repo.save(player)

        val newLog = Log("NEXT QUESTION", "NEXT ANSWER", "NEXT RESPONSE", "NEXT RESULT", 500)
        repo.addLog(player, newLog)

        val updatedLogs = repo.findById(0L)?.logs
        assertThat(updatedLogs).hasSize(2)
        assertThat(updatedLogs?.get(1)).isEqualTo(newLog)
    }
}