package com.pvpbeach.coins

import co.aikar.commands.BukkitCommandManager
import com.pvpbeach.coins.command.CoinCommand
import com.pvpbeach.coins.config.CurrencyConfig
import com.pvpbeach.coins.config.SettingsConfig
import com.pvpbeach.coins.currency.Currency
import com.pvpbeach.coins.placeholder.PlaceholderService
import com.pvpbeach.coins.player.store.PlayerCoinDataStore
import com.pvpbeach.coins.player.sync.PlayerCoinDataSync
import io.github.nosequel.data.DataHandler
import io.github.nosequel.data.connection.mongo.NoAuthMongoConnectionPool
import io.github.nosequel.data.connection.redis.NoAuthRedisConnectionPool
import org.bukkit.plugin.java.JavaPlugin
import xyz.mkotb.configapi.ConfigFactory

class CoinsPlugin : JavaPlugin()
{
    companion object
    {
        lateinit var currencies: CurrencyConfig
        lateinit var settings: SettingsConfig
    }

    private val factory = ConfigFactory.newFactory(this)

    override fun onEnable()
    {
        currencies = factory.fromFile("currencies", CurrencyConfig::class.java)
        settings = factory.fromFile("settings", SettingsConfig::class.java)

        DataHandler
            .withConnectionPool<NoAuthMongoConnectionPool> {
                this.hostname = settings.hostname
                this.databaseName = settings.mongoDatabase

                this.port = settings
                    .mongoPort
                    .toInt()
            }
            .withConnectionPool<NoAuthRedisConnectionPool> {
                this.hostname = settings.hostname
                this.port = settings
                    .redisPort
                    .toInt()
            }

        PlayerCoinDataSync.sync()
        PlayerCoinDataStore.init()

        PlaceholderService.initialize(this)

        // commands
        val manager = BukkitCommandManager(this).apply {
            this.commandContexts.registerContext(Currency::class.java) {
                val value = it.popFirstArg()
                currencies[value]
            }
        }

        manager.enableUnstableAPI("help")
        manager.registerCommand(CoinCommand)
    }

    override fun onDisable()
    {
        factory.save("currencies", currencies)
        factory.save("settings", settings)
    }
}