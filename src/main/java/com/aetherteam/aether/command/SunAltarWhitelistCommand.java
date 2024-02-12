package com.aetherteam.aether.command;

import com.aetherteam.aether.AetherConfig;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.UserWhiteList;
import net.minecraft.server.players.UserWhiteListEntry;

import java.util.Collection;

/**
 * [CODE COPY] - {@link net.minecraft.server.commands.WhitelistCommand}.<br><br>
 * Adapted to use {@link SunAltarWhitelist}.
 */
public class SunAltarWhitelistCommand {
    private static final SimpleCommandExceptionType ERROR_ALREADY_ENABLED = new SimpleCommandExceptionType(Component.translatable("commands.aether.sun_altar_whitelist.alreadyOn"));
    private static final SimpleCommandExceptionType ERROR_ALREADY_DISABLED = new SimpleCommandExceptionType(Component.translatable("commands.aether.sun_altar_whitelist.alreadyOff"));
    private static final SimpleCommandExceptionType ERROR_ALREADY_WHITELISTED = new SimpleCommandExceptionType(Component.translatable("commands.aether.sun_altar_whitelist.add.failed"));
    private static final SimpleCommandExceptionType ERROR_NOT_WHITELISTED = new SimpleCommandExceptionType(Component.translatable("commands.aether.sun_altar_whitelist.remove.failed"));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("aether")
                .then(Commands.literal("sun_altar_whitelist").requires((commandSourceStack) -> commandSourceStack.hasPermission(4))
                        .then(Commands.literal("on").executes((context) -> enableWhitelist(context.getSource())))
                        .then(Commands.literal("off").executes((context) -> disableWhitelist(context.getSource())))
                        .then(Commands.literal("list").executes((context) -> showList(context.getSource())))
                        .then(Commands.literal("add")
                                .then(Commands.argument("targets", GameProfileArgument.gameProfile())
                                        .suggests((context, builder) -> {
                                            PlayerList playerlist = context.getSource().getServer().getPlayerList();
                                            return SharedSuggestionProvider.suggest(playerlist.getPlayers().stream().filter((serverPlayer) -> !playerlist.getWhiteList().isWhiteListed(serverPlayer.getGameProfile())).map((player) -> player.getGameProfile().getName()), builder);
                                        })
                                        .executes((context) -> addPlayers(context.getSource(), GameProfileArgument.getGameProfiles(context, "targets")))
                                )
                        ).then(Commands.literal("remove")
                                .then(Commands.argument("targets", GameProfileArgument.gameProfile())
                                        .suggests((context, builder) -> SharedSuggestionProvider.suggest(context.getSource().getServer().getPlayerList().getWhiteListNames(), builder))
                                        .executes((context) -> removePlayers(context.getSource(), GameProfileArgument.getGameProfiles(context, "targets")))
                                )
                        ).then(Commands.literal("reload")
                                .executes((context) -> reload(context.getSource()))
                        )
                )
        );
    }

    private static int reload(CommandSourceStack source) {
        SunAltarWhitelist.INSTANCE.reload();
        source.sendSuccess(() -> Component.translatable("commands.aether.sun_altar_whitelist.reloaded"), true);
        return 1;
    }

    private static int addPlayers(CommandSourceStack source, Collection<GameProfile> players) throws CommandSyntaxException {
        UserWhiteList sunAltarWhiteList = SunAltarWhitelist.INSTANCE.getSunAltarWhiteList();
        int i = 0;

        for (GameProfile gameProfile : players) {
            if (!sunAltarWhiteList.isWhiteListed(gameProfile)) {
                UserWhiteListEntry entry = new UserWhiteListEntry(gameProfile);
                sunAltarWhiteList.add(entry);
                source.sendSuccess(() -> Component.translatable("commands.aether.sun_altar_whitelist.add.success", gameProfile.getName()), true);
                ++i;
            }
        }

        if (i == 0) {
            throw ERROR_ALREADY_WHITELISTED.create();
        } else {
            return i;
        }
    }

    private static int removePlayers(CommandSourceStack source, Collection<GameProfile> players) throws CommandSyntaxException {
        UserWhiteList sunAltarWhiteList = SunAltarWhitelist.INSTANCE.getSunAltarWhiteList();
        int i = 0;

        for (GameProfile gameProfile : players) {
            if (sunAltarWhiteList.isWhiteListed(gameProfile)) {
                UserWhiteListEntry userwhitelistentry = new UserWhiteListEntry(gameProfile);
                sunAltarWhiteList.remove(userwhitelistentry);
                source.sendSuccess(() -> Component.translatable("commands.aether.sun_altar_whitelist.remove.success", gameProfile.getName()), true);
                ++i;
            }
        }

        if (i == 0) {
            throw ERROR_NOT_WHITELISTED.create();
        } else {
            source.getServer().kickUnlistedPlayers(source);
            return i;
        }
    }

    private static int enableWhitelist(CommandSourceStack source) throws CommandSyntaxException {
        if (AetherConfig.SERVER.sun_altar_whitelist.get()) {
            throw ERROR_ALREADY_ENABLED.create();
        } else {
            AetherConfig.SERVER.sun_altar_whitelist.set(true);
            AetherConfig.SERVER.sun_altar_whitelist.save();
            source.sendSuccess(() -> Component.translatable("commands.aether.sun_altar_whitelist.enabled"), true);
            return 1;
        }
    }

    private static int disableWhitelist(CommandSourceStack source) throws CommandSyntaxException {
        if (!AetherConfig.SERVER.sun_altar_whitelist.get()) {
            throw ERROR_ALREADY_DISABLED.create();
        } else {
            AetherConfig.SERVER.sun_altar_whitelist.set(false);
            AetherConfig.SERVER.sun_altar_whitelist.save();
            source.sendSuccess(() -> Component.translatable("commands.aether.sun_altar_whitelist.disabled"), true);
            return 1;
        }
    }

    private static int showList(CommandSourceStack source) {
        String[] names = SunAltarWhitelist.INSTANCE.getSunAltarWhiteListNames();
        if (names.length == 0) {
            source.sendSuccess(() -> Component.translatable("commands.aether.sun_altar_whitelist.none"), false);
        } else {
            source.sendSuccess(() -> Component.translatable("commands.aether.sun_altar_whitelist.list", names.length, String.join(", ", names)), false);
        }
        return names.length;
    }
}
