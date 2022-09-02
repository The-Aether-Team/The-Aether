package com.gildedgames.aether.command;

import com.gildedgames.aether.api.WorldDisplayHelper;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class WorldPreviewFixCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, boolean isIntegrated) {
        dispatcher.register(Commands.literal("aether")
                .then(Commands.literal("world_preview").requires((commandSourceStack) -> isIntegrated)
                        .then(Commands.literal("fix").executes((context) -> fix(context.getSource())))
                )
        );
    }

    public static int fix(CommandSourceStack source) {
        WorldDisplayHelper.loadedLevel = null;
        WorldDisplayHelper.loadedSummary = null;
        source.sendSuccess(Component.translatable("commands.aether.menu.fix"), true);
        return 0;
    }
}
