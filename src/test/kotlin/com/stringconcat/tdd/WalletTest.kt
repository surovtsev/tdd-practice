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

}