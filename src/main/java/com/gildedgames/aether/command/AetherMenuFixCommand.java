package com.gildedgames.aether.command;

import com.gildedgames.aether.api.WorldDisplayHelper;
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
        WorldDisplayHelper.loadedLevel = null;
        WorldDisplayHelper.loadedSummary = null;
        return 0;
    }
}
