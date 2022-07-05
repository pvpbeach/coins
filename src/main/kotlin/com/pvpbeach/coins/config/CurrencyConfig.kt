package com.pvpbeach.coins.config

import com.pvpbeach.coins.currency.Currency
import com.pvpbeach.coins.currency.CurrencyFormatType
import xyz.mkotb.configapi.comment.Comment

class CurrencyConfig
{
    @Comment("The active currency types used, you can change the icon etc, which will automatically update the placeholders etc.")
    val currencies = mutableListOf(
        Currency(
            name = "Coin",
            icon = "⛃",
            formatType = CurrencyFormatType.AfterName
        )
    )

    operator fun get(name: String): Currency?
    {
        return currencies.firstOrNull {
            it.name.equals(name, true)
        }
    }
}