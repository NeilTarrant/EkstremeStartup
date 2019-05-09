package io.pivotal.extremerstartup.question

import org.junit.Test
import org.assertj.core.api.Assertions.assertThat

class TranslationQuestionKtTest {

    @Test
    fun `translates numbers less than 100 to German`() {
        assertThat(0.nameInGerman()).isEqualTo("null")
        assertThat(1.nameInGerman()).isEqualTo("eins")
        assertThat(2.nameInGerman()).isEqualTo("zwei")
        assertThat(3.nameInGerman()).isEqualTo("drei")
        assertThat(4.nameInGerman()).isEqualTo("vier")
        assertThat(5.nameInGerman()).isEqualTo("fünf")
        assertThat(6.nameInGerman()).isEqualTo("sechs")
        assertThat(7.nameInGerman()).isEqualTo("sieben")
        assertThat(8.nameInGerman()).isEqualTo("acht")
        assertThat(9.nameInGerman()).isEqualTo("neun")
        assertThat(10.nameInGerman()).isEqualTo("zehn")
        assertThat(11.nameInGerman()).isEqualTo("elf")
        assertThat(12.nameInGerman()).isEqualTo("zwolf")
        assertThat(13.nameInGerman()).isEqualTo("dreizehn")
        assertThat(17.nameInGerman()).isEqualTo("siebzehn")
        assertThat(20.nameInGerman()).isEqualTo("zwanzig")
        assertThat(25.nameInGerman()).isEqualTo("fünfundzwanzig")
        assertThat(35.nameInGerman()).isEqualTo("fünfunddreißig")
        assertThat(75.nameInGerman()).isEqualTo("fünfundsiebzig")
    }

    @Test
    fun `translates numbers less than 100 to French`() {
        assertThat(0.nameInFrench()).isEqualTo("zéro")
        assertThat(1.nameInFrench()).isEqualTo("un")
        assertThat(2.nameInFrench()).isEqualTo("deux")
        assertThat(3.nameInFrench()).isEqualTo("trois")
        assertThat(4.nameInFrench()).isEqualTo("quatre")
        assertThat(5.nameInFrench()).isEqualTo("cinq")
        assertThat(6.nameInFrench()).isEqualTo("six")
        assertThat(7.nameInFrench()).isEqualTo("sept")
        assertThat(8.nameInFrench()).isEqualTo("huit")
        assertThat(9.nameInFrench()).isEqualTo("neuf")
        assertThat(10.nameInFrench()).isEqualTo("dix")
        assertThat(11.nameInFrench()).isEqualTo("onze")
        assertThat(12.nameInFrench()).isEqualTo("douze")
        assertThat(13.nameInFrench()).isEqualTo("treize")
        assertThat(14.nameInFrench()).isEqualTo("quatorze")
        assertThat(15.nameInFrench()).isEqualTo("quinze")
        assertThat(16.nameInFrench()).isEqualTo("seize")
        assertThat(17.nameInFrench()).isEqualTo("dix-sept")
        assertThat(18.nameInFrench()).isEqualTo("dix-huit")
        assertThat(19.nameInFrench()).isEqualTo("dix-neuf")
        assertThat(20.nameInFrench()).isEqualTo("vingt")
    }
}