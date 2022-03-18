package com.gildedgames.aether.common.command;

import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.util.SunAltarWhitelist;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.UserWhiteList;
import net.minecraft.server.players.UserWhiteListEntry;

import java.util.Collection;

public class SunAltarWhitelistCommand {
    private static final SimpleCommandExceptionType ERROR_ALREADY_ENABLED = new SimpleCommandExceptionType(new TranslatableComponent("commands.aether.sun_altar_whitelist.alreadyOn"));
    private static final SimpleCommandExceptionType ERROR_ALREADY_DISABLED = new SimpleCommandExceptionType(new TranslatableComponent("commands.aether.sun_altar_whitelist.alreadyOff"));
    private static final SimpleCommandExceptionType ERROR_ALREADY_WHITELISTED = new SimpleCommandExceptionType(new TranslatableComponent("commands.aether.sun_altar_whitelist.add.failed"));
    private static final SimpleCommandExceptionType ERROR_NOT_WHITELISTED = new SimpleCommandExceptionType(new TranslatableComponent("commands.aether.sun_altar_whitelist.remove.failed"));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("aether")
                .then(Commands.literal("sun_altar_whitelist").requires((p_139234_) -> p_139234_.hasPermission(4))
                        .then(Commands.literal("on").executes((p_139236_) -> enableWhitelist(p_139236_.getSource())))
                        .then(Commands.literal("off").executes((p_139232_) -> disableWhitelist(p_139232_.getSource())))
                        .then(Commands.literal("list").executes((p_139228_) -> showList(p_139228_.getSource())))
                        .then(Commands.literal("add")
                                .then(Commands.argument("targets", GameProfileArgument.gameProfile())
                                        .suggests((p_139216_, p_139217_) -> {
                                            PlayerList playerlist = p_139216_.getSource().getServer().getPlayerList();
                                            return SharedSuggestionProvider.suggest(playerlist.getPlayers().stream().filter((p_142794_) -> !playerlist.getWhiteList().isWhiteListed(p_142794_.getGameProfile())).map((p_142791_) -> p_142791_.getGameProfile().getName()), p_139217_);
                                        })
                                        .executes((p_139224_) -> addPlayers(p_139224_.getSource(), GameProfileArgument.getGameProfiles(p_139224_, "targets")))))
                        .then(Commands.literal("remove")
                                .then(Commands.argument("targets", GameProfileArgument.gameProfile())
                                        .suggests((p_139206_, p_139207_) -> SharedSuggestionProvider.suggest(p_139206_.getSource().getServer().getPlayerList().getWhiteListNames(), p_139207_))
                                        .executes((p_139214_) -> removePlayers(p_139214_.getSource(), GameProfileArgument.getGameProfiles(p_139214_, "targets")))))
                        .then(Commands.literal("reload")
                                .executes((p_139204_) -> reload(p_139204_.getSource()))))
        );
    }

    private static int reload(CommandSourceStack source) {
        SunAltarWhitelist.INSTANCE.reload();
        source.sendSuccess(new TranslatableComponent("commands.aether.sun_altar_whitelist.reloaded"), true);
        return 1;
    }

    private static int addPlayers(CommandSourceStack source, Collection<GameProfile> players) throws CommandSyntaxException {
        UserWhiteList sunAltarWhiteList = SunAltarWhitelist.INSTANCE.getSunAltarWhiteList();
        int i = 0;

        for(GameProfile gameProfile : players) {
            if (!sunAltarWhiteList.isWhiteListed(gameProfile)) {
                UserWhiteListEntry entry = new UserWhiteListEntry(gameProfile);
                sunAltarWhiteList.add(entry);
                source.sendSuccess(new TranslatableComponent("commands.aether.sun_altar_whitelist.add.success", ComponentUtils.getDisplayName(gameProfile)), true);
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

        for(GameProfile gameProfile : players) {
            if (sunAltarWhiteList.isWhiteListed(gameProfile)) {
                UserWhiteListEntry userwhitelistentry = new UserWhiteListEntry(gameProfile);
                sunAltarWhiteList.remove(userwhitelistentry);
                source.sendSuccess(new TranslatableComponent("commands.aether.sun_altar_whitelist.remove.success", ComponentUtils.getDisplayName(gameProfile)), true);
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
        if (AetherConfig.COMMON.sun_altar_whitelist.get()) {
            throw ERROR_ALREADY_ENABLED.create();
        } else {
            AetherConfig.COMMON.sun_altar_whitelist.set(true);
            AetherConfig.COMMON.sun_altar_whitelist.save();
            source.sendSuccess(new TranslatableComponent("commands.aether.sun_altar_whitelist.enabled"), true);
            return 1;
        }
    }

    private static int disableWhitelist(CommandSourceStack source) throws CommandSyntaxException {
        if (!AetherConfig.COMMON.sun_altar_whitelist.get()) {
            throw ERROR_ALREADY_DISABLED.create();
        } else {
            AetherConfig.COMMON.sun_altar_whitelist.set(false);
            AetherConfig.COMMON.sun_altar_whitelist.save();
            source.sendSuccess(new TranslatableComponent("commands.aether.sun_altar_whitelist.disabled"), true);
            return 1;
        }
    }

    private static int showList(CommandSourceStack source) {
        String[] names = SunAltarWhitelist.INSTANCE.getSunAltarWhiteListNames();
        if (names.length == 0) {
            source.sendSuccess(new TranslatableComponent("commands.aether.sun_altar_whitelist.none"), false);
        } else {
            source.sendSuccess(new TranslatableComponent("commands.aether.sun_altar_whitelist.list", names.length, String.join(", ", names)), false);
        }
        return names.length;
    }
}
