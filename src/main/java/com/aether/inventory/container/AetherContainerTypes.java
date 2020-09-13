package com.aether.inventory.container;

import com.aether.Aether;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Aether.MODID)
public class AetherContainerTypes {

	public static final ContainerType<EnchanterContainer> ENCHANTER = null;
	public static final ContainerType<FreezerContainer> FREEZER = null;
	public static final ContainerType<IncubatorContainer> INCUBATOR = null;
	
	@EventBusSubscriber(modid = Aether.MODID, bus = EventBusSubscriber.Bus.MOD)
	public static class Registration {
		
		@SubscribeEvent
		public static void registerContainerTypes(RegistryEvent.Register<ContainerType<?>> event) {
			event.getRegistry().registerAll(new ContainerType[] {
				
				containerType("enchanter", EnchanterContainer::new),
				containerType("freezer", FreezerContainer::new),
				containerType("incubator", IncubatorContainer::new),
				
			});			
		}
		
		private static <T extends Container> ContainerType<T> containerType(String name, ContainerType.IFactory<T> factory) {
			ContainerType<T> containerType = new ContainerType<>(factory);
			containerType.setRegistryName(name);
			return containerType;
		}
		
	}
	
}
