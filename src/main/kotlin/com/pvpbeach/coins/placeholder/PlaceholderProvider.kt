package com.pvpbeach.coins.placeholder

import org.bukkit.OfflinePlayer

interface PlaceholderProvider
{
    fun provide(player: OfflinePlayer, request: String): String?
}