package com.pvpbeach.coins.player.serializer

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.pvpbeach.coins.CoinsPlugin
import com.pvpbeach.coins.currency.Currency
import com.pvpbeach.coins.player.PlayerCoinData
import com.pvpbeach.coins.player.PlayerCoinDataWrapper
import com.pvpbeach.coins.util.notAbsentMapOf
import io.github.nosequel.data.serializer.Serializer
import org.bukkit.Bukkit
import java.util.UUID

class PlayerCoinDataWrapperSerializer : Serializer<PlayerCoinDataWrapper>()
{
    override val type = PlayerCoinDataWrapper::class.java

    override fun deserialize(value: String): PlayerCoinDataWrapper
    {
        val json = JsonParser()
            .parse(value)
            .asJsonObject

        val currencies = json
            .get("currencies")
            .asJsonObject

        val data = notAbsentMapOf<Currency, Int>(0)
        val id = UUID.fromString(json["id"].asString)

        currencies.entrySet().forEach {
            val currency = CoinsPlugin
                .instance
                .currencies[it.key]

            if (currency == null)
            {
                Bukkit.getLogger()
                    .warning("Could not find stored currency by id ${it.key}")
            } else
            {
                data[currency] = it.value.asInt
            }
        }

        return PlayerCoinDataWrapper(
            PlayerCoinData()
                .apply {
                    this.currencyValues = data
                }, id
        )
    }

    override fun serialize(value: PlayerCoinDataWrapper): String
    {
        return JsonObject()
            .also {
                it.add("currencies", JsonObject().apply {
                    for (currencyValue in value.coinData.currencyValues)
                    {
                        this.addProperty(
                            currencyValue
                                .key
                                .name, currencyValue.value
                        )
                    }
                })

                it.addProperty("id", value.id.toString())
            }.toString()
    }
}