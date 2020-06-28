package com.aether.util;

import com.aether.Aether;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Aether.MODID)
public class AetherSoundEvents {

	public static final SoundEvent MUSIC_DISC_AETHER_TUNE = null;
	public static final SoundEvent MUSIC_DISC_ASCENDING_DAWN = null;
	public static final SoundEvent MUSIC_DISC_WELCOMING_SKIES = null;
	public static final SoundEvent MUSIC_DISC_LEGACY = null;
	
	public static final SoundEvent MUSIC_MENU = null;
	public static final SoundEvent MUSIC_AETHER1 = null;
	public static final SoundEvent MUSIC_AETHER2 = null;
	public static final SoundEvent MUSIC_AETHER3 = null;
	public static final SoundEvent MUSIC_AETHER4 = null;
	
	public static final SoundEvent UI_TOAST_AETHER_GENERAL = null;
	public static final SoundEvent UI_TOAST_AETHER_SILVER = null;
	public static final SoundEvent UI_TOAST_AETHER_BRONZE = null;
	
	public static final SoundEvent BLOCK_AETHER_PORTAL_AMBIENT = null;
	public static final SoundEvent BLOCK_DUNGEON_TRAP_TRIGGER = null;
	
	public static final SoundEvent ENTITY_MOA_AMBIENT = null;
	public static final SoundEvent ENTITY_MOA_FLAP = null;
	public static final SoundEvent ENTITY_SHEEPUFF_AMBIENT = null;
	public static final SoundEvent ENTITY_SHEEPUFF_HURT = null;
	public static final SoundEvent ENTITY_SHEEPUFF_DEATH = null;
	public static final SoundEvent ENTITY_SHEEPUFF_SHEAR = null;
	public static final SoundEvent ENTITY_FLYING_COW_AMBIENT = null;
	public static final SoundEvent ENTITY_FLYING_COW_HURT = null;
	public static final SoundEvent ENTITY_FLYING_COW_DEATH = null;
	public static final SoundEvent ENTITY_PHYG_AMBIENT = null;
	public static final SoundEvent ENTITY_PHYG_HURT = null;
	public static final SoundEvent ENTITY_PHYG_DEATH = null;
	public static final SoundEvent ENTITY_AERBUNNY_HURT = null;
	public static final SoundEvent ENTITY_AERBUNNY_DEATH = null;
	public static final SoundEvent ENTITY_AERBUNNY_LIFT = null;
	public static final SoundEvent ENTITY_ZEPHYR_AMBIENT = null;
	public static final SoundEvent ENTITY_ZEPHYR_SHOOT = null;
	public static final SoundEvent ENTITY_AERWHALE_AMBIENT = null;
	public static final SoundEvent ENTITY_AERWHALE_DEATH = null;
	public static final SoundEvent ENTITY_PROJECTILE_SHOOT = null;
	public static final SoundEvent ENTITY_DART_SHOOTER_SHOOT = null;
	public static final SoundEvent ENTITY_SLIDER_AWAKEN = null;
	public static final SoundEvent ENTITY_SLIDER_COLLIDE = null;
	public static final SoundEvent ENTITY_SLIDER_MOVE = null;
	public static final SoundEvent ENTITY_SLIDER_DEATH = null;
	public static final SoundEvent ENTITY_SUN_SPIRIT_SHOOT = null;
	
	
	@EventBusSubscriber(modid = Aether.MODID, bus = EventBusSubscriber.Bus.MOD)
	public static final class Registration {
		
		@SubscribeEvent
		public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
			event.getRegistry().registerAll(new SoundEvent[] {
				
				sound("music_disc.aether_tune"),
				sound("music_disc.ascending_dawn"),
				sound("music_disc.welcoming_skies"),
				sound("music_disc.legacy"),
				
				sound("music.menu"),
				sound("music.aether1"),
				sound("music.aether2"),
				sound("music.aether3"),
				sound("music.aether4"),
				
				sound("ui.toast.aether_general"),
				sound("ui.toast.aether_silver"),
				sound("ui.toast.aether_bronze"),
				
				sound("block.aether_portal.ambient"),
				sound("block.dungeon_trap.trigger"),
				
				sound("entity.moa.ambient"),
				sound("entity.moa.flap"),
				sound("entity.sheepuff.ambient"),
				sound("entity.sheepuff.hurt"),
				sound("entity.sheepuff.death"),
				sound("entity.sheepuff.shear"),
				sound("entity.flying_cow.ambient"),
				sound("entity.flying_cow.hurt"),
				sound("entity.flying_cow.death"),
				sound("entity.phyg.ambient"),
				sound("entity.phyg.hurt"),
				sound("entity.phyg.death"),
				sound("entity.aerbunny.hurt"),
				sound("entity.aerbunny.death"),
				sound("entity.aerbunny.lift"),
				sound("entity.zephyr.ambient"),
				sound("entity.zephyr.shoot"),
				sound("entity.aerwhale.ambient"),
				sound("entity.aerwhale.death"),
				sound("entity.projectile.shoot"),
				sound("entity.dart_shooter.shoot"),
				sound("entity.slider.awaken"),
				sound("entity.slider.death"),
				sound("entity.sun_spirit.shoot"),
				
			});
		}
		
		private static SoundEvent sound(String name) {
<<<<<<< HEAD
			SoundEvent sound = new SoundEvent(new ResourceLocation(Aether.MODID, name));
			sound.setRegistryName(sound.getName());
			return sound;
=======
			return new SoundEvent(new ResourceLocation(Aether.MODID, name));
>>>>>>> parent of ee7cccb... lakes & fixed sound registry, pls dont kill me raptor
		}
		
	}
	
}
