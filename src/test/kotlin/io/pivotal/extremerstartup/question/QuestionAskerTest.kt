package io.pivotal.extremerstartup.question

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.pivotal.extremerstartup.player.Log
import io.pivotal.extremerstartup.player.Player
import io.pivotal.extremerstartup.player.PlayerService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriUtils
import java.net.URI
import java.net.URL
import java.nio.charset.StandardCharsets

@RunWith(SpringRunner::class)
@SpringBootTest()
class QuestionAskerTest {

    lateinit var asker: QuestionAsker

    @MockBean
    lateinit var restTemplate: RestTemplate

    @MockBean
    lateinit var playerService: PlayerService

    @MockBean
    lateinit var questionFactory: QuestionFactory

    val playerOneUrl = "http://example.org"
    val playerTwoUrl = "http://google.com"
    val playerThreeUrl = "http://altavista.com"
    val playerFourUrl = "http://404.com"
    val playerFiveUrl = "http://500.com"

    lateinit var playerOne: Player
    lateinit var playerTwo: Player
    lateinit var playerThree: Player
    lateinit var playerFour: Player
    lateinit var playerFive: Player

    @Before
    fun setUp() {
        asker = QuestionAsker(restTemplate, playerService, questionFactory)

        playerOne = Player(0L, "Team A", URL(playerOneUrl), 100)
        playerTwo = Player(1L, "Team B", URL(playerTwoUrl), 80)
        playerThree = Player(2L, "Team C", URL(playerThreeUrl), 60)
        playerFour = Player(3L, "Team D", URL(playerFourUrl), 40)
        playerFive = Player(4L, "Team E", URL(playerFiveUrl), 20)
    }

    @Test
    fun `Sends a GET request with a question to each player`() {
        val questionText = "what is your name"
        setupMocks(questionText)

        asker.questionPlayers()

        val encodedQuestion = UriUtils.encode(questionText, StandardCharsets.UTF_8)

        verify(questionFactory).nextQuestion(playerOne)
        verify(questionFactory).nextQuestion(playerTwo)
        verify(questionFactory).nextQuestion(playerThree)
        verify(questionFactory).nextQuestion(playerFour)
        verify(questionFactory).nextQuestion(playerFive)

        verify(restTemplate).getForEntity(URI(playerOneUrl + "?q=" + encodedQuestion), String::class.java)
        verify(restTemplate).getForEntity(URI(playerTwoUrl + "?q=" + encodedQuestion), String::class.java)
        verify(restTemplate).getForEntity(URI(playerThreeUrl + "?q=" + encodedQuestion), String::class.java)
        verify(restTemplate).getForEntity(URI(playerFourUrl + "?q=" + encodedQuestion), String::class.java)
        verify(restTemplate).getForEntity(URI(playerFiveUrl + "?q=" + encodedQuestion), String::class.java)
    }

    @Test
    fun `Increments player score by 10 for a correct answer`() {
        setupMocks()

        asker.questionPlayers()

        verify(playerService).changeScore(playerOne, 10)
    }

    @Test
    fun `Decrements player score based on position for an incorrect answer`() {
        setupMocks()

        asker.questionPlayers()

        verify(playerService).changeScore(playerTwo, -10 / 2)
        verify(playerService).changeScore(playerThree, -10 / 3)
    }

    @Test
    fun `Decrements player score based on error for an http error response`() {
        setupMocks()

        asker.questionPlayers()

        verify(playerService).changeScore(playerFour, -40)
        verify(playerService).changeScore(playerFive, -50)
    }


    @Test
    fun `Logs the results for the player`() {
        setupMocks()

        asker.questionPlayers()

        verify(playerService).logResult(playerOne, Log("Test Question", "Correct", "Correct", "CORRECT", 10))
        verify(playerService).logResult(playerTwo, Log("Test Question", "Correct", "Incorrect", "INCORRECT", -10 / 2))
        verify(playerService).logResult(playerThree, Log("Test Question", "Correct", "Incorrect", "INCORRECT", -10 / 3))
        verify(playerService).logResult(playerFour, Log("Test Question", "Correct", "NONE", "ERROR_4XX", -40))
        verify(playerService).logResult(playerFive, Log("Test Question", "Correct", "NONE", "ERROR_5XX", -50))
    }

    private fun setupMocks(questionText: String = "Test Question") {
        whenever(playerService.listAll()).thenReturn(listOf(playerOne, playerTwo, playerThree, playerFour, playerFive))
        whenever(questionFactory.nextQuestion(any<Player>())).thenReturn(BasicQuestion(questionText, "Correct", 10))


        val encodedQuestion = UriUtils.encode(questionText, StandardCharsets.UTF_8)

        whenever(restTemplate.getForEntity(eq(URI("$playerOneUrl?q=$encodedQuestion")), eq(String::class.java))).thenReturn(ResponseEntity.ok("Correct"))
        whenever(restTemplate.getForEntity(eq(URI("$playerTwoUrl?q=$encodedQuestion")), eq(String::class.java))).thenReturn(ResponseEntity.ok("Incorrect"))
        whenever(restTemplate.getForEntity(eq(URI("$playerThreeUrl?q=$encodedQuestion")), eq(String::class.java))).thenReturn(ResponseEntity.ok("Incorrect"))
        whenever(restTemplate.getForEntity(eq(URI("$playerFourUrl?q=$encodedQuestion")), eq(String::class.java))).thenReturn(ResponseEntity.notFound().build())
        whenever(restTemplate.getForEntity(eq(URI("$playerFiveUrl?q=$encodedQuestion")), eq(String::class.java))).thenReturn(ResponseEntity.status(INTERNAL_SERVER_ERROR).build())
    }
}