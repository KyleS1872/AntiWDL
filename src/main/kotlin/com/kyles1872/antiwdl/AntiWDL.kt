package com.kyles1872.antiwdl

import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.messaging.PluginMessageListener

class AntiWDL : JavaPlugin(), PluginMessageListener {

    private val incomingChannels: List<String> = listOf("wdl:init", "wdl:request", "WDL|INIT", "WDL|REQUEST")

    override fun onEnable() {
        incomingChannels.forEach {
            try {
                server.messenger.registerIncomingPluginChannel(this, it, this)
            } catch (_: Exception) {
                // Thrown when legacy channels are registered on servers 1.13+
            }
        }
    }

    override fun onDisable() = server.messenger.unregisterIncomingPluginChannel(this)

    override fun onPluginMessageReceived(channel: String, player: Player, data: ByteArray?) {
        if (channel in incomingChannels && !player.isOp)
            player.kickPlayer("Connection lost. Please try again or contact an administrator.")
    }
}
