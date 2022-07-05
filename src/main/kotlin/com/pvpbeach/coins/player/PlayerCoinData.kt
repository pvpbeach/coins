package com.pvpbeach.coins.player

import com.pvpbeach.coins.currency.Currency
import com.pvpbeach.coins.util.notAbsentMapOf
import java.util.UUID

class PlayerCoinData
{
    companion object
    {
        val playerMap =
            notAbsentMapOf<UUID, PlayerCoinData>(PlayerCoinData())

        operator fun get(key: UUID): PlayerCoinData
        {
            return playerMap[key]
        }

        operator fun set(id: UUID, value: PlayerCoinData)
        {
            playerMap[id] = value
        }
    }

    val currencyValues = notAbsentMapOf<Currency, Int>(0)

    fun wrap(id: UUID): PlayerCoinDataWrapper
    {
        return PlayerCoinDataWrapper(this, id)
    }
}

data class PlayerCoinDataWrapper(
    val coinData: PlayerCoinData,
    val id: UUID
)