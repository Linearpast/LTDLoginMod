package me.londiuh.login;

import me.londiuh.login.commands.LoginCommand;
import me.londiuh.login.commands.RegisterCommand;
import me.londiuh.login.commands.WhitelistCommand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LoginMod.MODID)
public class LoginMod {
    public static final String MODID = "login";
    static GetPlayer getPlayer = new GetPlayer();

    public LoginMod(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        RegisteredPlayersJson.read();
        MinecraftForge.EVENT_BUS.register(LoginCommand.class);
        MinecraftForge.EVENT_BUS.register(RegisterCommand.class);
        MinecraftForge.EVENT_BUS.register(WhitelistCommand.class);
    }


    public static PlayerLogin getPlayer(ServerPlayer player) {
        return getPlayer.get(player);
    }
}
