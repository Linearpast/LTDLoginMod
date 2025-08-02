package me.londiuh.login;

import net.minecraft.server.level.ServerPlayer;
import java.util.HashMap;
import java.util.UUID;

public class GetPlayer extends HashMap<UUID, PlayerLogin> {
    public PlayerLogin get(ServerPlayer player) {
        UUID uuid = player.getUUID();
        if (containsKey(uuid)) {
            return super.get(uuid);
        }
        PlayerLogin newPlayer = new PlayerLogin(player);
        put(uuid, newPlayer);
        return newPlayer;
    }
}
