package com.gildedgames.aether.common.command;

import com.gildedgames.aether.client.gui.screen.menu.AetherWorldDisplayHelper;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;

public class AetherMenuFixCommand {
    public static void register(CommandDispatcher<CommandSourceStack> pDispatcher, boolean isIntegrated) {
        pDispatcher.register(Commands.literal("aether")
                .then(Commands.literal("menu").requires((commandSourceStack) -> isIntegrated)
                .then(Commands.literal("fix").requires((commandSourceStack) -> isIntegrated)
                        .executes((context) -> fixPlayer(context.getSource()))))
        );
    }

    public static int fixPlayer(CommandSourceStack source) {
        if (source.getEntity() instanceof ServerPlayer player) {
            MinecraftServer server = source.getServer();
            GameRules gameRules = server.getGameRules();
            if (player.gameMode.getGameModeForPlayer() == GameType.SPECTATOR
                    && !gameRules.getRule(GameRules.RULE_MOBGRIEFING).get()
                    && !gameRules.getRule(GameRules.RULE_DOMOBSPAWNING).get()
                    && gameRules.getRule(GameRules.RULE_RANDOMTICKING).get() == 0) {
                AetherWorldDisplayHelper.loadedLevel = null;
                AetherWorldDisplayHelper.loadedSummary = null;
                player.setGameMode(server.getDefaultGameType());
                gameRules.getRule(GameRules.RULE_MOBGRIEFING).set(true, server);
                gameRules.getRule(GameRules.RULE_DOMOBSPAWNING).set(true, server);
                gameRules.getRule(GameRules.RULE_RANDOMTICKING).set(3, server);
                AetherWorldDisplayHelper.loadedGameMode = null;
                AetherWorldDisplayHelper.loadedGameRules = null;
                AetherWorldDisplayHelper.loadedPosition = null;
                return 1;
            }
        }
        return 0;
    }

}
