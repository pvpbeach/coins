package com.pvpbeach.coins.placeholder

import com.pvpbeach.coins.placeholder.providers.NativePlaceholderProvider
import org.bukkit.plugin.java.JavaPlugin

interface PlaceholderHook
{
    val provider: PlaceholderProvider
        get() = NativePlaceholderProvider

    fun initialize(plugin: JavaPlugin)
}