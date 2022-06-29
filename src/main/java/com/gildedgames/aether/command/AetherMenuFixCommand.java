package com.gildedgames.aether.command;

import com.gildedgames.aether.client.gui.screen.menu.AetherWorldDisplayHelper;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class AetherMenuFixCommand {
    public static void register(CommandDispatcher<CommandSourceStack> pDispatcher, boolean isIntegrated) {
        pDispatcher.register(Commands.literal("aether")
                .then(Commands.literal("menu").requires((commandSourceStack) -> isIntegrated)
                .then(Commands.literal("fix").requires((commandSourceStack) -> isIntegrated)
                        .executes((context) -> fix())))
        );
    }

    public static int fix() {
        AetherWorldDisplayHelper.loadedLevel = null;
        AetherWorldDisplayHelper.loadedSummary = null;
        return 0;
    }
}
