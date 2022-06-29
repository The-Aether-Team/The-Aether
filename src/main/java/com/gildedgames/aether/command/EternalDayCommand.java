package com.gildedgames.aether.command;

import com.gildedgames.aether.capability.AetherCapabilities;
import com.gildedgames.aether.capability.time.AetherTime;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

// TODO: This is a temporary command for testing.
public class EternalDayCommand {
    public static void register(CommandDispatcher<CommandSourceStack> pDispatcher) {
        pDispatcher.register(Commands.literal("eternalday").requires((commandSourceStack) -> {
            return commandSourceStack.hasPermission(2);
        }).then(Commands.literal("set").then(Commands.literal("true").executes((context) -> {
            return setEternalDay(context.getSource(), true);
        })).then(Commands.literal("false").executes((context) -> {
            return setEternalDay(context.getSource(), false);
        }))).then(Commands.literal("query").executes((context) -> {
            return queryEternalDay(context.getSource());
        })));
    }

    public static int setEternalDay(CommandSourceStack stack, boolean value) {
        ServerLevel level = stack.getLevel();
        AetherTime aetherTime = level.getCapability(AetherCapabilities.AETHER_TIME_CAPABILITY).orElse(null);
        aetherTime.setEternalDay(value);
        aetherTime.updateEternalDay();
        stack.sendSuccess(Component.literal("Set eternal day to " + value), true);
        return 1;
    }

    public static int queryEternalDay(CommandSourceStack stack) {
        ServerLevel level = stack.getLevel();
        AetherTime aetherTime = level.getCapability(AetherCapabilities.AETHER_TIME_CAPABILITY).orElse(null);
        stack.sendSuccess(Component.literal("Eternal day is set to " + aetherTime.getEternalDay()), true);
        return 1;
    }
}