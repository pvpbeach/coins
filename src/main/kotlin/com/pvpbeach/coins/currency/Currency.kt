package com.pvpbeach.coins.currency

data class Currency(
    val name: String,
    val icon: String,
    val formatType: CurrencyFormatType
)
{
    fun format(amount: Int): String
    {
        return when (formatType)
        {
            CurrencyFormatType.BeforeName -> "${icon}${amount}"
            CurrencyFormatType.AfterName -> "${amount}${icon}"
        }
    }

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