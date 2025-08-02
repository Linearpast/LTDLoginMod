package me.londiuh.login.listeners;

import me.londiuh.login.LoginMod;
import me.londiuh.login.PlayerLogin;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;

public class OnPlayerConnect {
    public static void listen(ServerPlayer player) {
        PlayerLogin playerLogin = LoginMod.getPlayer(player);
        playerLogin.setLoggedIn(false);
        player.setInvulnerable(true);
        player.sendMessage(
                new TextComponent(
                        new TranslatableComponent("message.login.welcome").getString()
                ),
                player.getUUID());
        player.connection.send(new ClientboundSetTitleTextPacket(new TextComponent(
                new TranslatableComponent("message.login.title_tip").getString()
        ).withStyle(ChatFormatting.GREEN)));
    }
}
