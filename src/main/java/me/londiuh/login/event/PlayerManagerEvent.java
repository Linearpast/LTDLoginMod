package me.londiuh.login.event;

import me.londiuh.login.LoginMod;
import me.londiuh.login.PlayerLogin;
import me.londiuh.login.listeners.OnPlayerConnect;
import me.londiuh.login.listeners.OnPlayerLeave;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.UserWhiteList;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ExtractMethodRecommender")
@Mod.EventBusSubscriber(modid = LoginMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerManagerEvent {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerConnect(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();
        if(player instanceof ServerPlayer serverPlayer) {
            OnPlayerConnect.listen(serverPlayer);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if(player instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.tickCount == 1200) {
                PlayerLogin playerLogin = LoginMod.getPlayer(serverPlayer);
                if(!playerLogin.isLoggedIn()) {
                    serverPlayer.connection.disconnect(new TextComponent(
                            new TranslatableComponent("message.login.kick").getString()
                    ));
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getPlayer();
        if(player instanceof ServerPlayer serverPlayer) {
            OnPlayerLeave.listen(serverPlayer);
        }
    }
}
