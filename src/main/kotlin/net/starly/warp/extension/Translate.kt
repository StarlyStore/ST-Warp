package net.starly.warp.extension

import org.bukkit.ChatColor

class Translate {

    companion object {

        fun color(arrays: List<String>): List<String> {
            var newArray = arrayListOf<String>()

            for(array: String in arrays) {
                var newString = ChatColor.translateAlternateColorCodes('&', array)
                newArray.add(newString)
            }
            return newArray
        }
    }
}