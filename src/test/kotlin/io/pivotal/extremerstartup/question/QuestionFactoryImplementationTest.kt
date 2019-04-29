package io.pivotal.extremerstartup.question

import io.pivotal.extremerstartup.player.Player
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.net.URL
import kotlin.random.Random

@RunWith(SpringRunner::class)
@SpringBootTest
class QuestionFactoryImplementationTest {

    lateinit var factory: QuestionFactory

    val player = Player(0L, "Test Player", URL("http://example.org"))

    @Before
    fun setUp() {
        factory = QuestionFactoryImplementation()
    }

    @Test
    fun `When running in warm-up mode, question factory returns only 'what is your name'`() {
        factory.setWarmUpMode()

        val questions = (0..9).map { _ -> factory.nextQuestion(player) }

        assertThat(questions.any { q -> q is WarmUpQuestion }).isTrue()
        assertThat(questions.all { q -> q is WarmUpQuestion }).isTrue()
    }

    @Test
    fun `in the first round, creates both addition and maximum question`() {
        factory.level = 1

        val questions = (0..9).map { _ -> factory.nextQuestion(player) }
        assertThat(questions.any { q -> q is AdditionQuestion }).isTrue()
        assertThat(questions.any { q -> q is MaximumQuestion }).isTrue()
        assertThat(questions.all { q -> q is AdditionQuestion || q is MaximumQuestion }).isTrue()
    }

    @Test
    fun `in the second round, creates four different types of question`() {
        factory.level = 2

        val questions = (0..19).map { _ -> factory.nextQuestion(player) }
        assertThat(questions.any { q -> q is AdditionQuestion }).isTrue()
        assertThat(questions.any { q -> q is MaximumQuestion }).isTrue()
        assertThat(questions.any { q -> q is MultiplicationQuestion }).isTrue()
        assertThat(questions.any { q -> q is SquareCubeQuestion }).isTrue()
        assertThat(questions.all { q -> q is AdditionQuestion || q is MaximumQuestion || q is MultiplicationQuestion || q is SquareCubeQuestion }).isTrue()
    }

    @Test
    fun `in the third round, moves a sliding window forward, keeping 5 question types, so AdditionQuestions no longer appear`() {
        factory.level = 3

        val questions = (0..29).map { _ -> factory.nextQuestion(player) }
        assertThat(questions.any { q -> q is AdditionQuestion }).isFalse()
        assertThat(questions.any { q -> q is MaximumQuestion }).isTrue()
        assertThat(questions.any { q -> q is MultiplicationQuestion }).isTrue()
        assertThat(questions.any { q -> q is SquareCubeQuestion }).isTrue()
        assertThat(questions.any { q -> q is MinimumQuestion }).isTrue()
        assertThat(questions.any { q -> q is GeneralKnowledgeQuestion }).isTrue()
        assertThat(questions.all { q -> q is MaximumQuestion || q is MultiplicationQuestion || q is SquareCubeQuestion || q is MinimumQuestion || q is GeneralKnowledgeQuestion }).isTrue()
    }
}