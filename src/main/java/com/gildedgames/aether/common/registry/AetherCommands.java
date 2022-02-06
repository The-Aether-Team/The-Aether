package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.capability.interfaces.IEternalDay;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AetherCommands {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        EternalDayCommand.register(dispatcher);
    }

    // TODO: This is a temporary command for testing.
    public static class EternalDayCommand {
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
            IEternalDay eternalDay = level.getCapability(AetherCapabilities.ETERNAL_DAY_CAPABILITY).orElse(null);
            eternalDay.setEternalDay(value);
            stack.sendSuccess(new TextComponent("Set eternal day to " + value), true);
            return 1;
        }

        public static int queryEternalDay(CommandSourceStack stack) {
            ServerLevel level = stack.getLevel();
            IEternalDay eternalDay = level.getCapability(AetherCapabilities.ETERNAL_DAY_CAPABILITY).orElse(null);
            stack.sendSuccess(new TextComponent("Eternal day is set to " + eternalDay.getEternalDay()), true);
            return 1;
        }
    }
}
