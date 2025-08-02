package me.londiuh.login;

import net.minecraft.server.level.ServerPlayer;

public class PlayerLogin {
    private final ServerPlayer player;
    private boolean loggedIn;

    public PlayerLogin(ServerPlayer player) {
        this.player = player;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public ServerPlayer getPlayer() {
        return player;
    }
}
