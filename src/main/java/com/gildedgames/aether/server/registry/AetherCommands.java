package com.gildedgames.aether.server.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.server.command.AetherTimeCommand;
import com.gildedgames.aether.server.command.EternalDayCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AetherCommands {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        AetherTimeCommand.register(dispatcher);
        EternalDayCommand.register(dispatcher);
    }
}
