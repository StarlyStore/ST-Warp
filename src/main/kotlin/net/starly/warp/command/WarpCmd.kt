package net.starly.warp.command

import net.starly.warp.WarpMain.Companion.config
import net.starly.warp.WarpMain.Companion.prefix
import net.starly.warp.data.WarpData
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import net.starly.warp.WarpMain.Companion.instance
import net.starly.warp.extension.Translate
import net.starly.core.data.Config

class WarpCmd : CommandExecutor {


    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {

        var player: Player = sender as Player
        var warpData: WarpData = WarpData(player)

        if (sender is Player) {

            if (args.isEmpty()) {
                for(list: String in Translate.color(config!!.getStringList("messages.warp.main"))) {
                    player.sendMessage(list)
                }
                return true;
            }

            when (args[0]) {

                "이동", "teleport", "tp" -> {

                    if (!player.hasPermission("warp.teleport")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("errMessages.noPermission"))))
                        return true
                    }

                    if (args.size == 1) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("errMessages.noTypingWarp"))))
                        return true;
                    }

                    if (!warpData.isWarpExist(args[1])) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("errMessages.notExistWarp"))))
                        return true
                    }

                    var name: String = args[1]
                    warpData.getWarpLocation(name)?.let { player.teleport(it) }
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "$prefix".plus(config!!.getString("messages.warp.teleport").replace("{name}", name))))
                }

                "강제이동", "forceteleport", "forcedteleport" -> {

                    if (!player.hasPermission("warp.forcedteleport")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("errMessages.noPermission"))))
                        return true
                    }

                    if (args.size == 1) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("errMessages.noTypingPlayer"))))
                        return true;
                    }

                    if(args.size == 2) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("errMessages.noTypingWarp"))))
                        return true;
                    }

                    if (!warpData.isWarpExist(args[2])) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("errMessages.notExistWarp"))))
                        return true
                    }

                    if(Bukkit.getPlayer(args[1]) == null) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("errMessages.targetIsOffline"))))
                        return true
                    }

                    var target: Player = player.server.getPlayer(args[1])!!
                    var name: String = args[2]

                    warpData.getWarpLocation(name)?.let { target.teleport(it) }
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("messages.warp.forcedTeleport"))
                    .replace("{name}", name)
                    .replace("{target}", target.name)))
                }


                "생성", "create" -> {

                    if (!player.hasPermission("warp.create")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("errMessages.noPermission"))))
                        return true;
                    }

                    if (args.size == 1) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("errMessages.noTypingWarp"))))
                        return true;
                    }

                    if (warpData.isWarpExist(args[1])) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("errMessages.alreadyWarp"))))
                        return true;
                    }

                    var name: String = args[1]
                    warpData.createWarp(name)
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("messages.warp.create")
                        .replace("{name}", name))))
                    return true;
                }

                "제거", "삭제", "delete" -> {

                    var name: String = args[1]

                    if (!player.hasPermission("warp.delete")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("errMessages.noPermission"))))
                        return true;
                    }

                    if (args.size == 1) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("errMessages.noTypingWarp"))))
                        return true;
                    }

                    if (!warpData.isWarpExist(name)) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("errMessages.notExistWarp"))))
                        return true;
                    }

                    warpData.deleteWarp(name)
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("messages.warp.delete")
                        .replace("{name}", name))))
                    return true;

                }

                "목록", "list" -> {

                    if (!player.hasPermission("warp.list")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("errMessages.noPermission"))))
                        return true;
                    }

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus("§f워프 목록")))

                    for(list: String in warpData.listWarp()) {
                        player.sendMessage("§6> §7${list}")
                    }
                    return true;
                }


                "리로드", "reload" -> {

                    if(!player.hasPermission("warp.reload")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("errMessages.noPermission"))))
                        return true;
                    }

                    config = Config("config", instance)
                    config!!.reloadConfig()
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("messages.warp.reloadConfig"))))
                    return true;
                }

                else -> {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("errMessages.notExistCommand"))))
                }
            }
        }
        return false;
    }
}