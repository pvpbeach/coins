package com.pvpbeach.coins.placeholder.providers

import com.pvpbeach.coins.CoinsPlugin
import com.pvpbeach.coins.placeholder.PlaceholderProvider
import com.pvpbeach.coins.player.PlayerCoinData
import org.bukkit.OfflinePlayer
import java.util.UUID

object NativePlaceholderProvider : PlaceholderProvider
{
    override fun provide(player: OfflinePlayer, request: String): String?
    {
        val parts = request.split("-")

        return when (parts[0])
        {
            "value" ->
            {
                if (parts.size != 2)
                {
                    return null
                }

                val id = player.uniqueId
                val currency = CoinsPlugin.currencies[parts[1]] ?: return "N/A"

                currency.format(PlayerCoinData[id].currencyValues[currency])
            }
            else ->
            {
                ""
            }
        }
    }
}