package com.pvpbeach.coins.placeholder.hooks

import com.pvpbeach.coins.placeholder.PlaceholderHook
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer
import org.bukkit.plugin.java.JavaPlugin

object PapiHook : PlaceholderHook, PlaceholderExpansion()
{
    override fun getIdentifier() = "coins"
    override fun getAuthor() = "mommy"
    override fun getVersion() = "1.0"

    override fun onRequest(p: OfflinePlayer, params: String): String
    {
        return provider.provide(p, params) ?: "Invalid"
    }

    override fun initialize(plugin: JavaPlugin)
    {
        register()
    }
}