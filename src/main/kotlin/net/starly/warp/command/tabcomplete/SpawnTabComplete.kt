package net.starly.warp.command.tabcomplete

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import java.util.*

class SpawnTabComplete : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        cmd: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String> {
        return Collections.emptyList()
    }
}