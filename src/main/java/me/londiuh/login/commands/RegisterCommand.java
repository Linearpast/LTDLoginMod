package me.londiuh.login.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.londiuh.login.LoginMod;
import me.londiuh.login.PlayerLogin;
import me.londiuh.login.RegisteredPlayersJson;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class RegisterCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal("register")
                .then(argument("newPassword", StringArgumentType.word())
                    .then(argument("confirmPassword", StringArgumentType.word())
                        .executes(ctx -> {
                            String password = StringArgumentType.getString(ctx, "newPassword");
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            String username = player.getName().getString();
                            if (RegisteredPlayersJson.isPlayerRegistered(username)) {
                                ctx.getSource().sendSuccess(new TextComponent(
                                        new TranslatableComponent("message.login.was_registered").getString()
                                ).withStyle(ChatFormatting.RED), false);
                                return 1;
                            }
                            if (!password.equals(StringArgumentType.getString(ctx, "confirmPassword"))) {
                                ctx.getSource().sendSuccess(new TextComponent(
                                        new TranslatableComponent("message.login.error_password_register").getString()
                                ).withStyle(ChatFormatting.RED), false);
                                return 1;
                            }
                            String uuid = player.getUUID().toString();
                            RegisteredPlayersJson.save(uuid, username, password);
                            PlayerLogin playerLogin = LoginMod.getPlayer(player);
                            playerLogin.setLoggedIn(true);
                            player.setInvulnerable(false);
                            ctx.getSource().sendSuccess(new TextComponent(
                                    new TranslatableComponent("message.login.register").getString()
                            ).withStyle(ChatFormatting.GREEN), false);
                            return 1;
        }))));
    }

    @SubscribeEvent
    public static void commandRegister(RegisterCommandsEvent event) {
        register(event.getDispatcher());
    }
}
