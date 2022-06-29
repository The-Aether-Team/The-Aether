package com.gildedgames.aether.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.TimeArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

// Vanilla copy of TimeCommand
public class AetherTimeCommand {
    public static void register(CommandDispatcher<CommandSourceStack> pDispatcher) {
        pDispatcher.register(Commands.literal("aether")
                .then(Commands.literal("time").requires((commandSourceStack) -> {
                    return commandSourceStack.hasPermission(2);
                }).then(Commands.literal("set").then(Commands.literal("day").executes((context) -> {
                    return setTime(context.getSource(), 3000);
                })).then(Commands.literal("noon").executes((context) -> {
                    return setTime(context.getSource(), 18000);
                })).then(Commands.literal("night").executes((context) -> {
                    return setTime(context.getSource(), 39000);
                })).then(Commands.literal("midnight").executes((context) -> {
                    return setTime(context.getSource(), 54000);
                })).then(Commands.argument("time", TimeArgument.time()).executes((context) -> {
                    return setTime(context.getSource(), IntegerArgumentType.getInteger(context, "time"));
                }))).then(Commands.literal("add").then(Commands.argument("time", TimeArgument.time()).executes((context) -> {
                    return addTime(context.getSource(), IntegerArgumentType.getInteger(context, "time"));
                }))).then(Commands.literal("query").then(Commands.literal("daytime").executes((context) -> {
                    return queryTime(context.getSource(), getDayTime(context.getSource().getLevel()));
                })).then(Commands.literal("day").executes((context) -> {
                    return queryTime(context.getSource(), (int)(context.getSource().getLevel().getDayTime() / 72000L % 2147483647L));
                }))))
        );
    }

    /**
     * Returns the day time (time wrapped within a day)
     */
    private static int getDayTime(ServerLevel pLevel) {
        return (int) pLevel.getDayTime();
    }

    private static int queryTime(CommandSourceStack pSource, int pTime) {
        pSource.sendSuccess(Component.translatable("commands.time.query", pTime), false);
        return pTime;
    }

    public static int setTime(CommandSourceStack pSource, int pTime) {
        ServerLevel level = pSource.getLevel();
        level.setDayTime(pTime);
        pSource.sendSuccess(Component.translatable("commands.time.set", pTime), true);
        return getDayTime(pSource.getLevel());
    }

    public static int addTime(CommandSourceStack pSource, int pAmount) {
        ServerLevel level = pSource.getLevel();
        level.setDayTime(level.getDayTime() + pAmount);
        int i = getDayTime(pSource.getLevel());
        pSource.sendSuccess(Component.translatable("commands.time.set", i), true);
        return i;
    }
}
