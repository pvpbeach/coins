package com.pvpbeach.coins.player.sync

import com.pvpbeach.coins.player.PlayerCoinData
import com.pvpbeach.coins.player.PlayerCoinDataWrapper
import io.github.nosequel.data.DataHandler
import io.github.nosequel.data.DataStoreType
import io.github.nosequel.data.sync.PubSubType

object PlayerCoinDataSync
{
    lateinit var subscriber: PubSubType<PlayerCoinDataWrapper>

    fun sync()
    {
        subscriber = DataHandler.createPubSubType(DataStoreType.REDIS) {
            PlayerCoinData[it.id] = it.coinData
        }
    }
}