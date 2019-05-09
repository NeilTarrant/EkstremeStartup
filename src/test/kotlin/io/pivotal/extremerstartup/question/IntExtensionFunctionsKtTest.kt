package io.pivotal.extremerstartup.question

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

import org.junit.Assert.*

class IntExtensionFunctionsKtTest {

    @Test
    fun ordinal() {
        assertThat(0.ordinal()).isEqualTo("0th")
        assertThat(1.ordinal()).isEqualTo("1st")
        assertThat(2.ordinal()).isEqualTo("2nd")
        assertThat(3.ordinal()).isEqualTo("3rd")
        assertThat(4.ordinal()).isEqualTo("4th")
        assertThat(10.ordinal()).isEqualTo("10th")
        assertThat(11.ordinal()).isEqualTo("11th")
        assertThat(12.ordinal()).isEqualTo("12th")
        assertThat(13.ordinal()).isEqualTo("13th")
        assertThat(14.ordinal()).isEqualTo("14th")
        assertThat(20.ordinal()).isEqualTo("20th")
        assertThat(21.ordinal()).isEqualTo("21st")
        assertThat(22.ordinal()).isEqualTo("22nd")
        assertThat(23.ordinal()).isEqualTo("23rd")
        assertThat(24.ordinal()).isEqualTo("24th")
    }

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
}