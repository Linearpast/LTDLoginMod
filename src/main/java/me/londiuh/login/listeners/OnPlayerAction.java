package me.londiuh.login.listeners;

import me.londiuh.login.LoginMod;
import me.londiuh.login.PlayerLogin;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class OnPlayerAction {
    public static boolean canInteract(ServerGamePacketListenerImpl networkHandler) {
        ServerPlayer player = networkHandler.player;
        PlayerLogin playerLogin = LoginMod.getPlayer(player);
	    return playerLogin.isLoggedIn();
    }
}
