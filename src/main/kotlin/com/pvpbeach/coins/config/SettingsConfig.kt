package com.pvpbeach.coins.config

import xyz.mkotb.configapi.comment.Comment

class SettingsConfig
{
    @Comment("This will be used for both MongoDB and Redis.")
    val hostname = "127.0.0.1"

    val mongoPort = "27017"
    val mongoDatabase = "coins"

    val redisPort = "27017"
}