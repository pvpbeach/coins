package com.pvpbeach.coins.wrapper

import com.pvpbeach.coins.currency.Currency
import com.pvpbeach.coins.player.PlayerCoinData
import com.pvpbeach.coins.player.store.PlayerCoinDataStore
import com.pvpbeach.coins.player.sync.PlayerCoinDataSync
import java.util.UUID

object CurrencyWrapper
{
    operator fun get(id: UUID): CurrencyWrapped
    {
        return CurrencyWrapped(
            PlayerCoinData[id], id
        )
    }
}

class CurrencyWrapped(val data: PlayerCoinData, val id: UUID)
{
    operator fun set(currency: Currency, value: Int)
    {
        data.currencyValues[currency] = value

        PlayerCoinDataStore
            .storage
            .store(
                id, data.wrap(id)
            )

        PlayerCoinDataSync
            .subscriber
            .publish(
                data.wrap(id)
            )
    }

    operator fun get(currency: Currency): Int
    {
        return data.currencyValues[currency]
            .also {
                PlayerCoinDataStore
                    .storage
                    .store(
                        id, data.wrap(id)
                    )

                PlayerCoinDataSync
                    .subscriber
                    .publish(
                        data.wrap(id)
                    )
            }
    }
}