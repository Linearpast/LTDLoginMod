package me.londiuh.login.commands;

import me.londiuh.login.LoginMod;
import me.londiuh.login.PlayerLogin;
import me.londiuh.login.RegisteredPlayersJson;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundCustomSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LoginCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal("login")
                .then(argument("password", StringArgumentType.word())
                    .executes(ctx -> {
                        String password = StringArgumentType.getString(ctx, "password");
                        String username = ctx.getSource().getPlayerOrException().getName().getString();
                        ServerPlayer player = ctx.getSource().getPlayerOrException();

                        if (!RegisteredPlayersJson.isPlayerRegistered(username)) {
                            ctx.getSource().sendSuccess(new TextComponent(
                                    new TranslatableComponent("message.login.not_register").getString()
                            ).withStyle(ChatFormatting.RED), false);
                        } else if (RegisteredPlayersJson.isCorrectPassword(username, password)) {
                            PlayerLogin playerLogin = LoginMod.getPlayer(player);
                            playerLogin.setLoggedIn(true);
                            ctx.getSource().sendSuccess(new TextComponent(
                                    new TranslatableComponent("message.login.login").getString()
                            ).withStyle(ChatFormatting.GREEN), false);
                            if (!player.isCreative()) {
                                player.setInvulnerable(false);
                            }
                            player.connection.send(new ClientboundCustomSoundPacket(
                                    new ResourceLocation("minecraft:block.note_block.pling"),
                                    SoundSource.MASTER,
                                    player.position(),
                                    100f, 0f)
                            );
                        } else {
                            player.connection.send(new ClientboundCustomSoundPacket(
                                    new ResourceLocation("minecraft:entity.zombie.attack_iron_door"),
                                    SoundSource.MASTER,
                                    player.position(),
                                    100f, 0.5f)
                            );
                            ctx.getSource().sendSuccess(
                                    new TextComponent(
                                            new TranslatableComponent("message.login.error_password").getString()
                                    ).withStyle(ChatFormatting.RED), false
                            );
                        }
                        return 1;
        })));
    }

    @SubscribeEvent
    public static void commandRegister(RegisterCommandsEvent event) {
        register(event.getDispatcher());
    }
}
