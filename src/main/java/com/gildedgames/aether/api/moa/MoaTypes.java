package com.gildedgames.aether.api.moa;

import com.gildedgames.aether.Aether;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

//TODO: Switch to DeferredRegister
@ObjectHolder(Aether.MODID)
public class MoaTypes
{
	public static final MoaType BLUE = null;
	public static final MoaType WHITE = null;
	public static final MoaType BLACK = null;
	public static final MoaType ORANGE = null;
	
	@EventBusSubscriber(modid = Aether.MODID, bus = Bus.MOD)
	public static class Registration {
		
		@SubscribeEvent
		public static void registerAetherMoaTypes(RegistryEvent.Register<MoaType> event) {
			event.getRegistry().registerAll(new MoaType[] {
				
				moaType("blue", new MoaType.Properties().eggColor(0x7777FF).maxJumps(3).moaSpeed(0.1F)),
				moaType("white", new MoaType.Properties().eggColor(0xFFFFFF).maxJumps(4).moaSpeed(0.1F)),
				moaType("black", new MoaType.Properties().eggColor(0x222222).maxJumps(8).moaSpeed(0.1F).saddleTexture("textures/entity/moa/black_saddle.png")),
				moaType("orange", new MoaType.Properties().eggColor(-0xC3D78).maxJumps(2).moaSpeed(0.3F)),
				
			});
		}
		
		public static MoaType moaType(String name, MoaType.Properties properties) {
			MoaType type = new MoaType(properties);
			type.setRegistryName(name);
			return type;
		}
		
	}
	
}
