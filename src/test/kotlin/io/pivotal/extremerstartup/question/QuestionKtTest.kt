package io.pivotal.extremerstartup.question

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class QuestionKtTest {
    @Test
    fun `Int isPrime returns whether number is prime`() {
        assertThat(0.isPrime()).isFalse()
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

    @Test
    fun `determines the correct digitSum for a number`() {
        assertThat(0.digitSum()).isEqualTo(0)
        assertThat(1.digitSum()).isEqualTo(1)
        assertThat(12.digitSum()).isEqualTo(3)
        assertThat(123.digitSum()).isEqualTo(6)
        assertThat(1234.digitSum()).isEqualTo(10)
        assertThat(12345.digitSum()).isEqualTo(15)
        assertThat(123456.digitSum()).isEqualTo(21)
        assertThat(1234567.digitSum()).isEqualTo(28)
        assertThat(12345678.digitSum()).isEqualTo(36)
        assertThat(123456789.digitSum()).isEqualTo(45)
        assertThat(1234567890.digitSum()).isEqualTo(45)
    }

    @Test
    fun `determines the correct digitProduct for a number`() {
        assertThat(0.digitProduct()).isEqualTo(0)
        assertThat(1.digitProduct()).isEqualTo(1)
        assertThat(12.digitProduct()).isEqualTo(2)
        assertThat(123.digitProduct()).isEqualTo(6)
        assertThat(1234.digitProduct()).isEqualTo(24)
        assertThat(12345.digitProduct()).isEqualTo(120)
        assertThat(123456.digitProduct()).isEqualTo(720)
        assertThat(1234567.digitProduct()).isEqualTo(5040)
        assertThat(12345678.digitProduct()).isEqualTo(40320)
        assertThat(123456789.digitProduct()).isEqualTo(362880)
        assertThat(1234567890.digitProduct()).isEqualTo(0)
    }
}