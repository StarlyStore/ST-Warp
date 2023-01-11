package net.starly.warp

import net.starly.warp.command.SetSpawnCmd
import net.starly.warp.command.SpawnCmd
import net.starly.warp.command.WarpCmd
import net.starly.warp.command.tabcomplete.WarpTabComplete
import net.starly.core.bstats.Metrics
import net.starly.core.data.Config
import net.starly.warp.command.tabcomplete.SpawnTabComplete
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

class WarpMain : JavaPlugin() {

    companion object {
        var instance: WarpMain? = null
        var log: Logger = Bukkit.getLogger()
        var config: Config? = null
        var prefix: String? = null
    }

    override fun onEnable() {
        init()
    }

    /** 모듈을 관리합니다. */
    private fun init() {

        /** DEPENDENCY */
        if (Bukkit.getPluginManager().getPlugin("ST-Core") == null) {
            log.warning("[" + instance!!.name + "] ST-Core 플러그인이 적용되지 않았습니다! 플러그인을 비활성화합니다.");
            log.warning("[" + instance!!.name + "] 다운로드 링크 : §fhttps://discord.gg/TF8jqSJjCG");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        /** COMMAND */
        getCommand("warp")?.setExecutor(WarpCmd())
        getCommand("warp")?.tabCompleter = WarpTabComplete()
        getCommand("spawn")?.setExecutor(SpawnCmd())
        getCommand("spawn")?.tabCompleter = SpawnTabComplete()
        getCommand("setspawn")?.setExecutor(SetSpawnCmd())
        getCommand("setspawn")?.tabCompleter = SpawnTabComplete()

        /** CONFIG */
        instance = this
        Companion.config = Config("config", this)
        Companion.config!!.loadDefaultConfig()
        prefix = Config("config", this).config.getString("prefix")

        /** METRICS */
        Metrics(this, 17360)
    }
}