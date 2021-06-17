package com.stringconcat.tdd

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class WalletTest {

    @Test
    fun `wallet containing 2 dollars is another wallet containing 2 dollars`() {
        Wallet(Money.dollar(2)) shouldBe Wallet(Money.dollar(2))
    }

    @Test
    fun `wallet that contains 2 CHF returns 1 dollars if rate 2 to 1`() {
        Wallet(Money.franc(2)).asDollars(2.0) shouldBe Money.dollar(1)
    }

    // 2 CHF + 4 USD = 5 USD (if rate 4:1)
    @Test
    fun `wallet that contains 2 franc and 4 dollars returns 5 dollars if rate 4 to 1`() {
        Wallet(Money.franc(2), Money.dollar(4)).asDollars(4.0) shouldBe Money.dollar(5)
    }

    // 2 CHF + 4 USD = 10 CHF (if rate 2:1)
    @Test
    fun `wallet that contains 2 franc and 4 dollars returns 10 francs if rate 2 to 1`() {
        Wallet(Money.franc(2), Money.dollar(4)).asFrancs(0.5) shouldBe Money.franc(10)
    }

    // 2 EUR + 4 USD = 8 USD
    @Test
    fun `wallet that contains 2 euro and 4 dollars returns 8 dollars if rate 2 to 1`() {
        Wallet(Money.euro(2), Money.dollar(4)).asDollars(0.5) shouldBe Money.dollar(8)
    }

    @Test
    fun `wallet that contains 2 euro and 4 dollars returns 4 euro if rate 2 to 1`() {
        Wallet(Money.euro(2), Money.dollar(4)).asEuro(2.0) shouldBe Money.euro(4)
    }

    @Test
    fun `wallet that contains 1 euro and 2 dollars and 4 francs returns 3 euro if rate 2-1 for dollar-euro and 4-1 for euro-franc`() {
        Wallet(Money.euro(1), Money.dollar(2), Money.franc(4))
            .asEuro(2.0, 4.0) shouldBe Money.euro(3)
    }

    @Test
    fun `wallet that contains 30 dollars and 20 francs + 20 francs is same as another wallet with 30 dollars and 40 francs`() {
        Wallet(Money.dollar(30), Money.franc(20)) + Money.franc(20) shouldBe Wallet(Money.dollar(30), Money.franc(40))
    }
}