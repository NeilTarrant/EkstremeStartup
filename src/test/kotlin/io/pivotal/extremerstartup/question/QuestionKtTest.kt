package io.pivotal.extremerstartup.question

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class QuestionKtTest {

    @Test
    fun `nthFibonnaciNumber returns correct result`() {
        assertThat(nthFibonacciNumber(1)).isEqualTo(1)
        assertThat(nthFibonacciNumber(2)).isEqualTo(1)
        assertThat(nthFibonacciNumber(3)).isEqualTo(2)
        assertThat(nthFibonacciNumber(4)).isEqualTo(3)
        assertThat(nthFibonacciNumber(5)).isEqualTo(5)
        assertThat(nthFibonacciNumber(6)).isEqualTo(8)
        assertThat(nthFibonacciNumber(7)).isEqualTo(13)
        assertThat(nthFibonacciNumber(8)).isEqualTo(21)
        assertThat(nthFibonacciNumber(9)).isEqualTo(34)
        assertThat(nthFibonacciNumber(10)).isEqualTo(55)
        assertThat(nthFibonacciNumber(20)).isEqualTo(6765)
        assertThat(nthFibonacciNumber(30)).isEqualTo(832040)
        assertThat(nthFibonacciNumber(60)).isEqualTo(1548008755920)
        assertThat(nthFibonacciNumber(80)).isEqualTo(23416728348467685)
    }

    @Test
    fun `determines the correct (English) scrabble score for a work`() {
        assertThat("a".map(Char::englishScrabbleScore).sum()).isEqualTo(1)
        assertThat("neil".map(Char::englishScrabbleScore).sum()).isEqualTo(4)
        assertThat("zax".map(Char::englishScrabbleScore).sum()).isEqualTo(19)
        assertThat("banana".map(Char::englishScrabbleScore).sum()).isEqualTo(8)
        assertThat("september".map(Char::englishScrabbleScore).sum()).isEqualTo(15)
        assertThat("cloud".map(Char::englishScrabbleScore).sum()).isEqualTo(8)
        assertThat("zoo".map(Char::englishScrabbleScore).sum()).isEqualTo(12)
        assertThat("ruby".map(Char::englishScrabbleScore).sum()).isEqualTo(9)
        assertThat("buzzword".map(Char::englishScrabbleScore).sum()).isEqualTo(32)
    }


}