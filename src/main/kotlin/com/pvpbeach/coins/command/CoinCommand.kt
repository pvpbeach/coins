package com.pvpbeach.coins.command

import co.aikar.commands.CommandHelp
import co.aikar.commands.annotation.*
import com.pvpbeach.coins.currency.Currency
import com.pvpbeach.coins.wrapper.CurrencyWrapper
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("coins")
object CoinCommand
{
    @Default
    @HelpCommand
    fun help(help: CommandHelp)
    {
        help.showHelp()
    }

    @Description("Gives a certain player an amount of coins.")
    @CommandAlias("give")
    fun give(sender: CommandSender, target: Player, amount: Int, @Optional @Default("coins") currency: Currency)
    {
        CurrencyWrapper[target.uniqueId][currency] += amount
    }

    @Description("Set a certain player to an amount of coins.")
    @CommandAlias("set")
    fun set(sender: CommandSender, target: Player, amount: Int, @Optional @Default("coins") currency: Currency)
    {
        CurrencyWrapper[target.uniqueId][currency] = amount
    }

    @Description("Removes an amount of coins from a player's balance.")
    @CommandAlias("remove")
    fun remove(sender: CommandSender, target: Player, amount: Int, @Optional @Default("coins") currency: Currency)
    {
        CurrencyWrapper[target.uniqueId][currency] -= amount
    }
}