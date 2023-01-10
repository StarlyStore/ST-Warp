package net.starly.warp.command

import net.starly.warp.WarpMain
import net.starly.warp.WarpMain.Companion.config
import net.starly.warp.WarpMain.Companion.prefix
import net.starly.warp.data.WarpData
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SpawnCmd: CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {

        var player: Player = sender as Player
        var warpData: WarpData = WarpData(player)

        if(!(sender is Player)) {
        }

            if(args.isEmpty()) {

                if(!player.hasPermission("warp.spawn")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("errMessages.noPermission"))))
                    return true
                }

                if(!warpData.isSpawnExist()) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("errMessages.notSetSpawn"))))
                    return true
                }

                warpData.getSpawnLocation()?.let { player.teleport(it) }
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("messages.spawn.teleport"))))

        }
        return false;
    }
}

class SetSpawnCmd : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {

        var player: Player = sender as Player
        var warpData: WarpData = WarpData(player)

        if(sender is Player) {

            if(args.isEmpty()) {

                if(!player.hasPermission("warp.setspawn")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${WarpMain.prefix}".plus(WarpMain.config!!.getString("errMessages.noPermission"))))
                    return true
                }

                warpData.setSpawnLocation()
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "${prefix}".plus(config!!.getString("messages.spawn.set"))))
            }
        }
        return false;
    }
}