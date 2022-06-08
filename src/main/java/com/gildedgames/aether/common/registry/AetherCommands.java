package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.command.AetherMenuFixCommand;
import com.gildedgames.aether.common.command.AetherTimeCommand;
import com.gildedgames.aether.common.command.EternalDayCommand;
import com.gildedgames.aether.common.command.SunAltarWhitelistCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AetherCommands {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        Commands.CommandSelection selection = event.getEnvironment();
        SunAltarWhitelistCommand.register(dispatcher);
        AetherTimeCommand.register(dispatcher);
        EternalDayCommand.register(dispatcher);
        AetherMenuFixCommand.register(dispatcher, selection == Commands.CommandSelection.INTEGRATED);
    }
}
