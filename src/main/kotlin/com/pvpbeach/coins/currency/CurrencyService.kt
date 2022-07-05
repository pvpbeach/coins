package com.pvpbeach.coins.currency

object CurrencyService
{
    val currencies = mutableListOf(
        Currency(
            name = "Coin",
            icon = "⛃",
            formatType = CurrencyFormatType.AfterName
        )
    )
}