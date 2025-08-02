package me.londiuh.login.listeners;

import me.londiuh.login.LoginMod;
import me.londiuh.login.PlayerLogin;
import net.minecraft.server.level.ServerPlayer;

public class OnPlayerLeave {
    public static void listen(ServerPlayer player) {
        PlayerLogin playerLogin = LoginMod.getPlayer(player);
        playerLogin.setLoggedIn(false);
    }
}
