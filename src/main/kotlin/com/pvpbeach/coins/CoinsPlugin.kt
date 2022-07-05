package com.pvpbeach.coins

import co.aikar.commands.BukkitCommandManager
import co.aikar.commands.InvalidCommandArgument
import com.pvpbeach.coins.command.CoinCommand
import com.pvpbeach.coins.config.CurrencyConfig
import com.pvpbeach.coins.config.SettingsConfig
import com.pvpbeach.coins.currency.Currency
import com.pvpbeach.coins.placeholder.PlaceholderService
import com.pvpbeach.coins.player.serializer.PlayerCoinDataWrapperSerializer
import com.pvpbeach.coins.player.store.PlayerCoinDataStore
import com.pvpbeach.coins.player.sync.PlayerCoinDataSync
import io.github.nosequel.config.Configuration
import io.github.nosequel.config.ConfigurationFile
import io.github.nosequel.config.json.JsonConfigurationFile
import io.github.nosequel.data.DataHandler
import io.github.nosequel.data.connection.mongo.NoAuthMongoConnectionPool
import io.github.nosequel.data.connection.redis.NoAuthRedisConnectionPool
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class CoinsPlugin : JavaPlugin()
{
    companion object
    {
        private inline fun <reified T : Configuration> config(fileName: String, plugin: JavaPlugin): ReadOnlyProperty<Any, T>
        {
            val file = File(plugin.dataFolder, fileName)
            val clazz = T::class.java

            if (file.parentFile != null && !file.parentFile.exists())
            {
                file.mkdirs()
            }

            if (!file.exists())
            {
                file.createNewFile()
            }

            return object : ReadOnlyProperty<Any, T>
            {
                var value: T? = null

                /**
                 * Returns the value of the property for the given object.
                 * @param thisRef the object for which the value is requested.
                 * @param property the metadata for the property.
                 * @return the property value.
                 */
                override fun getValue(thisRef: Any, property: KProperty<*>): T
                {
                    if (value == null)
                    {
                        value = clazz
                            .getConstructor(ConfigurationFile::class.java)
                            .newInstance(
                                JsonConfigurationFile(file)
                            )

                        if (value != null && value is Configuration)
                        {
                            value!!.load()
                            value!!.save()
                        }
                    }

                    return value!!
                }
            }
        }

        lateinit var instance: CoinsPlugin
    }

    val currencies by config<CurrencyConfig>("currencies.json", this)
    val settings by config<SettingsConfig>("settings.json", this)

    override fun onEnable()
    {
        instance = this

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
            .withSerializer<PlayerCoinDataWrapperSerializer>()

        PlayerCoinDataSync.sync()
        PlayerCoinDataStore.init()

        PlaceholderService.initialize(this)

        // commands
        val manager = BukkitCommandManager(this).apply {
            this.commandContexts.registerContext(Currency::class.java) {
                val value = it.popFirstArg()
                currencies[value] ?: throw InvalidCommandArgument("No currency found by id $value!")
            }
        }

        manager.enableUnstableAPI("help")
        manager.registerCommand(CoinCommand)
    }
}