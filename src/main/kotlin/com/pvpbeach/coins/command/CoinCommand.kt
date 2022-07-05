package com.pvpbeach.coins.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.CommandHelp
import co.aikar.commands.annotation.*
import com.pvpbeach.coins.CoinsPlugin
import com.pvpbeach.coins.currency.Currency
import com.pvpbeach.coins.wrapper.CurrencyWrapper
import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("coins|coin")
object CoinCommand : BaseCommand()
{
    @Default
    @HelpCommand
    fun help(help: CommandHelp)
    {
        help.showHelp()
    }

    @Description("Gives a certain player an amount of coins.")
    @Subcommand("give")
    fun give(sender: CommandSender, target: OfflinePlayer, amount: Int, @Default("coins") currency: Currency)
    {
        CurrencyWrapper[target.uniqueId][currency] += amount
    }

    @Description("Set a certain player to an amount of coins.")
    @Subcommand("set")
    fun set(sender: CommandSender, target: OfflinePlayer, amount: Int, @Default("coins") currency: Currency)
    {
        CurrencyWrapper[target.uniqueId][currency] = amount
    }

    @Description("Removes an amount of coins from a player's balance.")
    @Subcommand("remove")
    fun remove(sender: CommandSender, target: OfflinePlayer, amount: Int, @Default("coins") currency: Currency)
    {
        CurrencyWrapper[target.uniqueId][currency] -= amount
    }

    @Description("Shows information regarding a player's economy status")
    @Subcommand("info")
    fun info(sender: CommandSender, target: OfflinePlayer)
    {
        CoinsPlugin
            .instance
            .currencies
            .currencies.forEach {
                sender.sendMessage("${ChatColor.GRAY} - ${it.format(CurrencyWrapper[target.uniqueId][it])}")
            }
    }
}