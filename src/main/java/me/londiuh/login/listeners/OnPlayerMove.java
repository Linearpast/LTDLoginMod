package me.londiuh.login.listeners;

import me.londiuh.login.LoginMod;
import me.londiuh.login.PlayerLogin;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class OnPlayerMove {
    public static boolean canMove(ServerGamePacketListenerImpl networkHandler) {
        ServerPlayer player = networkHandler.player;
        PlayerLogin playerLogin = LoginMod.getPlayer(player);
        boolean isLoggedIn = playerLogin.isLoggedIn();
        if (!isLoggedIn) {
            player.teleportTo(player.getX(), player.getY(), player.getZ()); // teleport to sync client position
        }
        return isLoggedIn;
    }
}
