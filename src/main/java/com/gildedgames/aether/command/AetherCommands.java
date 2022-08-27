package com.gildedgames.aether.command;

import com.gildedgames.aether.Aether;
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
        Commands.CommandSelection selection = event.getCommandSelection();
        AetherTimeCommand.register(dispatcher);
        EternalDayCommand.register(dispatcher);
        PlayerCapabilityCommand.register(dispatcher);
        SunAltarWhitelistCommand.register(dispatcher);
        WorldPreviewFixCommand.register(dispatcher, selection == Commands.CommandSelection.INTEGRATED);
    }
}
