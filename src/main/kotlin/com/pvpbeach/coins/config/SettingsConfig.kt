package com.pvpbeach.coins.config

import io.github.nosequel.config.Configuration
import io.github.nosequel.config.ConfigurationFile
import io.github.nosequel.config.annotation.Configurable

class SettingsConfig(file: ConfigurationFile) : Configuration(file)
{
    @field:Configurable(path = "hostname")
    val hostname: String = "127.0.0.1"

    @field:Configurable(path = "mongo_database")
    val mongoDatabase: String = "coins"

    @field:Configurable(path = "mongo_port")
    val mongoPort: String = "27017"

    @field:Configurable(path = "redis_port")
    val redisPort: String = "6379"
}