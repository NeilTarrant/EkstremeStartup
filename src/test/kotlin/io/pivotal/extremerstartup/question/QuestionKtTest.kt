package io.pivotal.extremerstartup.question

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class QuestionKtTest {
    @Test
    fun `Int isPrime returns whether number is prime`() {
        assertThat(1.isPrime()).isFalse()
        assertThat(2.isPrime()).isTrue()
        assertThat(3.isPrime()).isTrue()
        assertThat(4.isPrime()).isFalse()
        assertThat(5.isPrime()).isTrue()
        assertThat(6.isPrime()).isFalse()
        assertThat(7.isPrime()).isTrue()
        assertThat(8.isPrime()).isFalse()
        assertThat(9.isPrime()).isFalse()
        assertThat(10.isPrime()).isFalse()
    }

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
    }

    @Test
    fun `determines the correct (English) scrabble score for a work`() {
        assertThat("a".map(Char::scrabbleScore).sum()).isEqualTo(1)
        assertThat("neil".map(Char::scrabbleScore).sum()).isEqualTo(4)
        assertThat("zax".map(Char::scrabbleScore).sum()).isEqualTo(19)
        assertThat("banana".map(Char::scrabbleScore).sum()).isEqualTo(8)
        assertThat("september".map(Char::scrabbleScore).sum()).isEqualTo(15)
        assertThat("cloud".map(Char::scrabbleScore).sum()).isEqualTo(8)
        assertThat("zoo".map(Char::scrabbleScore).sum()).isEqualTo(12)
        assertThat("ruby".map(Char::scrabbleScore).sum()).isEqualTo(9)
        assertThat("buzzword".map(Char::scrabbleScore).sum()).isEqualTo(32)
    }
}