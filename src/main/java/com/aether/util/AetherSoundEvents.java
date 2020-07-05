package com.aether.util;

import static com.aether.util.AetherSoundEvents.Registration.sound;

import java.util.LinkedList;

import com.aether.Aether;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Aether.MODID)
public class AetherSoundEvents {
	
	private static LinkedList<SoundEvent> sounds = new LinkedList<>();

	public static final SoundEvent MUSIC_DISC_AETHER_TUNE = sound("music_disc.aether_tune");
	public static final SoundEvent MUSIC_DISC_ASCENDING_DAWN = sound("music_disc.ascending_dawn");
	public static final SoundEvent MUSIC_DISC_WELCOMING_SKIES = sound("music_disc.welcoming_skies");
	public static final SoundEvent MUSIC_DISC_LEGACY = sound("music_disc.legacy");
	
	public static final SoundEvent MUSIC_MENU = sound("music.menu");
	public static final SoundEvent MUSIC_AETHER1 = sound("music.aether1");
	public static final SoundEvent MUSIC_AETHER2 = sound("music.aether2");
	public static final SoundEvent MUSIC_AETHER3 = sound("music.aether3");
	public static final SoundEvent MUSIC_AETHER4 = sound("music.aether4");
	
	public static final SoundEvent UI_TOAST_AETHER_GENERAL = sound("ui.toast.aether_general");
	public static final SoundEvent UI_TOAST_AETHER_SILVER = sound("ui.toast.aether_silver");
	public static final SoundEvent UI_TOAST_AETHER_BRONZE = sound("ui.toast.aether_bronze");
	
	public static final SoundEvent BLOCK_AETHER_PORTAL_AMBIENT = sound("block.aether_portal.ambient");
	public static final SoundEvent BLOCK_DUNGEON_TRAP_TRIGGER = sound("block.dungeon_trap.trigger");
	
	public static final SoundEvent ENTITY_MOA_AMBIENT = sound("entity.moa.ambient");
	public static final SoundEvent ENTITY_MOA_FLAP = sound("entity.moa.flap");
	public static final SoundEvent ENTITY_SHEEPUFF_AMBIENT = sound("entity.sheepuff.ambient");
	public static final SoundEvent ENTITY_SHEEPUFF_HURT = sound("entity.sheepuff.hurt");
	public static final SoundEvent ENTITY_SHEEPUFF_DEATH = sound("entity.sheepuff.death");
	public static final SoundEvent ENTITY_SHEEPUFF_SHEAR = sound("entity.sheepuff.shear");
	public static final SoundEvent ENTITY_FLYING_COW_AMBIENT = sound("entity.flying_cow.ambient");
	public static final SoundEvent ENTITY_FLYING_COW_HURT = sound("entity.flying_cow.hurt");
	public static final SoundEvent ENTITY_FLYING_COW_DEATH = sound("entity.flying_cow.death");
	public static final SoundEvent ENTITY_PHYG_AMBIENT = sound("entity.phyg.ambient");
	public static final SoundEvent ENTITY_PHYG_HURT = sound("entity.phyg.hurt");
	public static final SoundEvent ENTITY_PHYG_DEATH = sound("entity.phyg.death");
	public static final SoundEvent ENTITY_AERBUNNY_HURT = sound("entity.aerbunny.hurt");
	public static final SoundEvent ENTITY_AERBUNNY_DEATH = sound("entity.aerbunny.death");
	public static final SoundEvent ENTITY_AERBUNNY_LIFT = sound("entity.aerbunny.lift");
	public static final SoundEvent ENTITY_ZEPHYR_AMBIENT = sound("entity.zephyr.ambient");
	public static final SoundEvent ENTITY_ZEPHYR_SHOOT = sound("entity.zephyr.shoot");
	public static final SoundEvent ENTITY_AERWHALE_AMBIENT = sound("entity.aerwhale.ambient");
	public static final SoundEvent ENTITY_AERWHALE_DEATH = sound("entity.aerwhale.death");
	public static final SoundEvent ENTITY_PROJECTILE_SHOOT = sound("entity.projectile.shoot");
	public static final SoundEvent ENTITY_DART_SHOOTER_SHOOT = sound("entity.dart_shooter.shoot");
	public static final SoundEvent ENTITY_SLIDER_AWAKEN = sound("entity.slider.awaken");
	public static final SoundEvent ENTITY_SLIDER_COLLIDE = sound("entity.slider.collide");
	public static final SoundEvent ENTITY_SLIDER_MOVE = sound("entity.slider.move");
	public static final SoundEvent ENTITY_SLIDER_DEATH = sound("entity.slider.death");
	public static final SoundEvent ENTITY_SUN_SPIRIT_SHOOT = sound("entity.sun_spirit.shoot");
	
	
	@EventBusSubscriber(modid = Aether.MODID, bus = Bus.MOD)
	public static class Registration {
		
		@SubscribeEvent
		public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
			for (SoundEvent sound : sounds) {
				event.getRegistry().register(sound);
			}
			
			sounds.clear();
			sounds = null;
		}
		
		public static SoundEvent sound(String name) {
			SoundEvent sound = new SoundEvent(new ResourceLocation(Aether.MODID, name));
			sound.setRegistryName(sound.getName());
			sounds.add(sound);
			return sound;
		}
		
	}
	
}
