package com.stringconcat.tdd

import java.lang.IllegalArgumentException
import kotlin.math.roundToInt

open class Money(val amount: Int, val currency: Currency) {

    init {
        if (amount < 0) throw IllegalArgumentException("Amount must be positive")
    }

    companion object {
        fun dollar(amount: Int) = Money(amount, Currency.USD)
        fun franc(amount: Int) = Money(amount, Currency.CHF)
        fun euro(amount: Int) = Money(amount, Currency.EUR)
    }

    operator fun plus(other: Money): Wallet {
        return if (this.currency != other.currency) {
            Wallet(this, other)
        } else {
            Wallet(Money(this.amount + other.amount, this.currency))
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Money) return false
        return this.amount == other.amount && this.currency == other.currency
    }

    operator fun times(multiplier: Int): Money {
        return Money(amount * multiplier, currency)
    }

    override fun toString(): String {
        return "Money(amount=$amount, currency=$currency)"
    }

    fun asDollar(rateForOneDollar: Double): Money {
        return asCurrency(Currency.USD, rateForOneDollar)
    }

    fun asFranc(rateForOneFranc: Double): Money {
        return asCurrency(Currency.CHF, rateForOneFranc)
    }

    fun asCurrency(otherCurrency: Currency, rateForOneOtherCurrency: Double): Money {
        return if (currency == otherCurrency) {
            this
        } else {
            Money((amount / rateForOneOtherCurrency).roundToInt(), otherCurrency)
        }
    }

    fun asEuro(dollarsForOneEuro: Double, francsForOneEuro: Double): Money {
        return when (currency) {
            Currency.USD -> {
                asCurrency(Currency.EUR, dollarsForOneEuro)
            }
            Currency.CHF -> {
                asCurrency(Currency.EUR, francsForOneEuro)
            }
            else -> {
                this
            }
        }
    }

    override fun hashCode(): Int {
        var result = amount
        result = 31 * result + currency.hashCode()
        return result
    }

    enum class Currency {
        USD, CHF, EUR
    }
}