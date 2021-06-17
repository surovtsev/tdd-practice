package com.stringconcat.tdd

class Wallet(vararg val moneys: Money) {

    override fun equals(other: Any?): Boolean {
        if (other !is Wallet) return false
        return this.moneys.contentEquals(other.moneys)
    }

    override fun toString(): String {
        return "Wallet(money=${moneys.contentToString()})"
    }

    fun asDollars(rateForOneDollar: Double): Money {
        return asCurrency(Money.Currency.USD, rateForOneDollar)
    }

    fun asFrancs(rateForOneFranc: Double): Money {
        return asCurrency(Money.Currency.CHF, rateForOneFranc)
    }

    private fun asCurrency(otherCurrency: Money.Currency, rateForCurrency: Double): Money {
        val amount = moneys.map { money -> money.asCurrency(otherCurrency, rateForCurrency) }
            .sumBy { c-> c.amount }

        return Money(
            amount,
            otherCurrency
        )
    }

}
