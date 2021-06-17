package com.stringconcat.tdd

class Wallet(vararg val moneys: Money) {

    override fun equals(other: Any?): Boolean {
        if (other !is Wallet) return false
        return groupByCurrency(this.moneys.asList()).equals(groupByCurrency(other.moneys.asList()))
    }

    private fun groupByCurrency(money: List<Money>): List<Money> {
        return money.groupBy { m -> m.currency }
            .entries.map { (currency, moneyOfCurreny)
                -> Money(moneyOfCurreny.sumBy { money -> money.amount }, currency)
            }
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

    fun asEuro(rateForOneEuro: Double): Money {
        return asCurrency(Money.Currency.EUR, rateForOneEuro)
    }

    fun asEuro(dollarsForOneEuro: Double, francsForOneEuro: Double): Money {
        val amount = moneys.map { money -> money.asEuro(dollarsForOneEuro, francsForOneEuro) }
            .sumBy { euro -> euro.amount }

        return Money.euro(amount)
    }

    operator fun plus(money: Money): Wallet {
        return Wallet(*moneys, money)
    }

}
