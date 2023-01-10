package io.github.idknicks.warp

import com.github.nicklib.bstats.Metrics
import com.github.nicklib.data.Config
import io.github.idknicks.warp.command.SetSpawnCmd
import io.github.idknicks.warp.command.SpawnCmd
import io.github.idknicks.warp.command.WarpCmd
import io.github.idknicks.warp.command.WarpTabComplete
import org.bukkit.plugin.java.JavaPlugin

class Plugin() : JavaPlugin() {

    companion object {
        var plugin: JavaPlugin? = null
        var config: Config? = null
        var prefix: String = Config("config", plugin).getString("prefix")!!
    }

    override fun onEnable() {
        init()
    }

    /** 모듈을 관리합니다. */
    private fun init() {

        /** COMMAND */
        getCommand("warp")?.setExecutor(WarpCmd())
        getCommand("warp")?.tabCompleter = WarpTabComplete()
        getCommand("spawn")?.setExecutor(SpawnCmd())
        getCommand("setspawn")?.setExecutor(SetSpawnCmd())

        /** CONFIG */
        plugin = this
        Plugin.config?.loadDefaultConfig()

        /** BSTATS */
        Metrics(this, 12345)
    }
}