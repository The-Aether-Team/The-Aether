package com.aetherteam.aether.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.TimeArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

/**
 * [VANILLA COPY] - {@link net.minecraft.server.commands.TimeCommand}.
 */
public class AetherTimeCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("aether")
                .then(Commands.literal("time").requires((commandSourceStack) -> commandSourceStack.hasPermission(2))
                        .then(Commands.literal("set")
                                .then(Commands.literal("day").executes((context) -> setTime(context.getSource(), 3000)))
                                .then(Commands.literal("noon").executes((context) -> setTime(context.getSource(), 18000)))
                                .then(Commands.literal("night").executes((context) -> setTime(context.getSource(), 39000)))
                                .then(Commands.literal("midnight").executes((context) -> setTime(context.getSource(), 54000)))
                                .then(Commands.argument("time", TimeArgument.time()).executes((context) -> setTime(context.getSource(), IntegerArgumentType.getInteger(context, "time"))))
                        ).then(Commands.literal("add")
                                .then(Commands.argument("time", TimeArgument.time()).executes((context) -> addTime(context.getSource(), IntegerArgumentType.getInteger(context, "time"))))
                        ).then(Commands.literal("query")
                                .then(Commands.literal("daytime").executes((context) -> queryTime(context.getSource(), getDayTime(context.getSource().getLevel()))))
                                .then(Commands.literal("day").executes((context) -> queryTime(context.getSource(), (int) (context.getSource().getLevel().getDayTime() / 72000L % (long) Integer.MAX_VALUE))))
                        )
                )
        );
    }

    /**
     * Returns the day time (time wrapped within a day)
     */
    private static int getDayTime(ServerLevel level) {
        return (int) level.getDayTime();
    }

    private static int queryTime(CommandSourceStack source, int time) {
        source.sendSuccess(Component.translatable("commands.time.query", time), false);
        return time;
    }

    private static int setTime(CommandSourceStack source, int time) {
        ServerLevel level = source.getLevel();
        level.setDayTime(time);
        source.sendSuccess(Component.translatable("commands.time.set", time), true);
        return getDayTime(source.getLevel());
    }

    private static int addTime(CommandSourceStack source, int amount) {
        ServerLevel level = source.getLevel();
        level.setDayTime(level.getDayTime() + amount);
        int i = getDayTime(source.getLevel());
        source.sendSuccess(Component.translatable("commands.time.set", i), true);
        return i;
    }
}
