package com.pvpbeach.coins.config

import com.pvpbeach.coins.currency.Currency
import com.pvpbeach.coins.currency.CurrencyFormatType
import io.github.nosequel.config.Configuration
import io.github.nosequel.config.ConfigurationFile
import io.github.nosequel.config.annotation.Configurable

class CurrencyConfig(file: ConfigurationFile) : Configuration(file)
{
    @field:Configurable(path = "currencies")
    val currencies: Array<Currency> = arrayOf(
        Currency(
            name = "Coin",
            icon = " &e⛃",
            formatType = CurrencyFormatType.AfterName
        )
    )

    operator fun get(name: String): Currency?
    {
        return currencies.firstOrNull {
            it.name.equals(name, true)
        }
    }

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CurrencyConfig

        if (!currencies.contentEquals(other.currencies)) return false

        return true
    }

    override fun hashCode(): Int
    {
        return currencies.contentHashCode()
    }
}