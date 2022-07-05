package com.pvpbeach.coins.placeholder

import com.pvpbeach.coins.placeholder.hooks.PapiHook
import org.bukkit.plugin.java.JavaPlugin

object PlaceholderService
{
    val detectionMap = hashMapOf<String, PlaceholderHook>(
        "PlaceholderAPI" to PapiHook
    )

    fun initialize(plugin: JavaPlugin)
    {
        detectionMap.forEach {
            it.value.initialize(plugin)
        }
    }
}