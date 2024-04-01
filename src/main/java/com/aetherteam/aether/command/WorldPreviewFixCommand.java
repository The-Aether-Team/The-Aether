package com.aetherteam.aether.command;

import com.aetherteam.aether.client.WorldDisplayHelper;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class WorldPreviewFixCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, boolean isIntegratedServer) {
        dispatcher.register(Commands.literal("aether")
                .then(Commands.literal("world_preview").requires((commandSourceStack) -> isIntegratedServer) // Only works on singleplayer worlds.
                        .then(Commands.literal("fix").executes((context) -> fix(context.getSource())))
                )
        );
    }

    /**
     * Resets the values used for displaying the world preview, in case they become bugged.
     *
     * @param source The {@link CommandSourceStack}.
     * @return An {@link Integer}.
     */
    private static int fix(CommandSourceStack source) {
        WorldDisplayHelper.resetStates();
        source.sendSuccess(() -> Component.translatable("commands.aether.menu.fix"), true);
        return 1;
    }
}
