package me.londiuh.login.listeners;

import me.londiuh.login.LoginMod;
import me.londiuh.login.PlayerLogin;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class OnGameMessage {
    public static boolean canSendMessage(ServerGamePacketListenerImpl networkHandler, ServerboundChatPacket packet) {
        ServerPlayer player = networkHandler.player;
        PlayerLogin playerLogin = LoginMod.getPlayer(player);
        String message = packet.getMessage();
        // TODO: config to allow more commands when you're not logged
        if (!playerLogin.isLoggedIn() && (message.startsWith("/login") || message.startsWith("/register"))) {
            return true;
        }
        return playerLogin.isLoggedIn();
    }
}
