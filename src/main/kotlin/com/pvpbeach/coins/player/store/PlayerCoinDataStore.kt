package com.pvpbeach.coins.player.store

import com.pvpbeach.coins.player.PlayerCoinData
import com.pvpbeach.coins.player.PlayerCoinDataWrapper
import com.pvpbeach.coins.player.sync.PlayerCoinDataSync
import io.github.nosequel.data.DataHandler
import io.github.nosequel.data.DataStoreType
import io.github.nosequel.data.store.StoreType
import java.util.UUID

object PlayerCoinDataStore
{
    lateinit var storage: StoreType<UUID, PlayerCoinDataWrapper>

    fun init()
    {
        storage = DataHandler.createStoreType(DataStoreType.MONGO)

        storage.retrieveAll {
            PlayerCoinData[it.id] = it.coinData
        }
    }
}