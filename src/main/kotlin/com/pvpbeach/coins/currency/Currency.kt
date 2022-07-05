package com.pvpbeach.coins.currency

data class Currency(
    val name: String,
    val icon: String,
    val formatType: CurrencyFormatType
)
{
    override fun equals(other: Any?): Boolean
    {
        return other is Currency && other.name == name
    }

    override fun hashCode(): Int
    {
        var result = name.hashCode()
        result = 31 * result + icon.hashCode()
        result = 31 * result + formatType.hashCode()
        return result
    }
}

enum class CurrencyFormatType
{
    BeforeName,
    AfterName
}