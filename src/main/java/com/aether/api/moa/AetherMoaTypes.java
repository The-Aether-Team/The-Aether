package com.aether.api.moa;

import com.aether.Aether;
import com.aether.item.AetherItemGroups;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Aether.MODID)
public class AetherMoaTypes {

	public static final AetherMoaType BLUE = null;
	public static final AetherMoaType WHITE = null;
	public static final AetherMoaType BLACK = null;
	public static final AetherMoaType ORANGE = null;
	
	@EventBusSubscriber(modid = Aether.MODID, bus = Bus.MOD)
	public static class Registration {
		
		@SubscribeEvent
		public static void registerAetherMoaTypes(RegistryEvent.Register<AetherMoaType> event) {
			event.getRegistry().registerAll(new AetherMoaType[] {
				
				moaType("blue", 0x7777FF, new MoaProperties.Builder().maxJumps(3).speed(0.1F)),
				moaType("white", 0xFFFFFF, new MoaProperties.Builder().maxJumps(4).speed(0.1F)),
				moaType("black", 0x222222, new MoaProperties.Builder().maxJumps(8).speed(0.1F).saddleTexture(new ResourceLocation(Aether.MODID, "textures/entity/moa/black_saddle.png"))),
				moaType("orange", -0xC3D78, new MoaProperties.Builder().maxJumps(2).speed(0.6F)),
				
			});
		}
		
		public static AetherMoaType moaType(String name, int hexColor, MoaProperties.Builder properties) {
			return moaType(name, hexColor, properties.build());
		}
		
		public static AetherMoaType moaType(String name, int hexColor, MoaProperties properties) {
			AetherMoaType type = new AetherMoaType(hexColor, properties, AetherItemGroups.AETHER_MISC);
			type.setRegistryName(new ResourceLocation(Aether.MODID, name));
			return type;
		}
		
	}
	
}
