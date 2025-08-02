package me.londiuh.login.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.UuidArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.players.UserWhiteListEntry;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class WhitelistCommand {
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
		dispatcher.register(literal("login").then(literal("whitelist")
				.requires(commandSourceStack -> commandSourceStack.hasPermission(2))
				.then(argument("id", StringArgumentType.word())
						.then(argument("uuid", UuidArgument.uuid())
								.executes(commandContext -> {
									GameProfile gameProfile = new GameProfile(
											UuidArgument.getUuid(commandContext, "uuid"),
											StringArgumentType.getString(commandContext, "id")
									);
									commandContext.getSource().getServer().getPlayerList().getWhiteList().add(
											new UserWhiteListEntry(gameProfile)
									);
									commandContext.getSource().sendSuccess(
											new TextComponent(
													new TranslatableComponent(
															"message.login.whitelist",
															gameProfile.getName(),
															gameProfile.getId().toString()
													).getString()
											),
											true
									);
									return Command.SINGLE_SUCCESS;
								})
						)
				)

		));
	}

	@SubscribeEvent
	public static void commandRegister(RegisterCommandsEvent event) {
		register(event.getDispatcher());
	}
}
