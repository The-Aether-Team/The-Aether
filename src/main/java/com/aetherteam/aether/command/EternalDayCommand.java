package com.aetherteam.aether.command;

import com.aetherteam.aether.capability.time.AetherTime;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

public class EternalDayCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("aether")
                .then(Commands.literal("eternal_day").requires((commandSourceStack) -> commandSourceStack.hasPermission(2))
                        .then(Commands.literal("set")
                                .then(Commands.argument("option", BoolArgumentType.bool())
                                        .suggests((context, builder) -> SharedSuggestionProvider.suggest(BoolArgumentType.bool().getExamples(), builder))
                                        .executes((context) -> setEternalDay(context.getSource(), BoolArgumentType.getBool(context, "option"))))
                        ).then(Commands.literal("query").executes((context) -> queryEternalDay(context.getSource())))
                )
        );
    }

    private static int setEternalDay(CommandSourceStack source, boolean value) {
        ServerLevel level = source.getLevel();
        AetherTime.get(level).ifPresent(aetherTime -> {
            aetherTime.setEternalDay(value);
            aetherTime.updateEternalDay(); // Syncs to client.
            source.sendSuccess(() -> Component.translatable("commands.aether.capability.time.eternal_day.set", value), true);
        });
        return 1;
    }

    private static int queryEternalDay(CommandSourceStack source) {
        ServerLevel level = source.getLevel();
        AetherTime.get(level).ifPresent(aetherTime -> source.sendSuccess(() -> Component.translatable("commands.aether.capability.time.eternal_day.query", aetherTime.getEternalDay()), true));
        return 1;
    }
}