package com.aether.registry;

import com.aether.Aether;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class AetherSoundEvents
{
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Aether.MODID);

	public static final RegistryObject<SoundEvent> MUSIC_DISC_AETHER_TUNE = register("music_disc.aether_tune");
	public static final RegistryObject<SoundEvent> MUSIC_DISC_ASCENDING_DAWN = register("music_disc.ascending_dawn");
	public static final RegistryObject<SoundEvent> MUSIC_DISC_WELCOMING_SKIES = register("music_disc.welcoming_skies");
	public static final RegistryObject<SoundEvent> MUSIC_DISC_LEGACY = register("music_disc.legacy");

	public static final RegistryObject<SoundEvent> MUSIC_MENU = register("music.menu");
	public static final RegistryObject<SoundEvent> MUSIC_AETHER1 = register("music.aether1");
	public static final RegistryObject<SoundEvent> MUSIC_AETHER2 = register("music.aether2");
	public static final RegistryObject<SoundEvent> MUSIC_AETHER3 = register("music.aether3");
	public static final RegistryObject<SoundEvent> MUSIC_AETHER4 = register("music.aether4");

	public static final RegistryObject<SoundEvent> UI_TOAST_AETHER_GENERAL = register("ui.toast.aether_general");
	public static final RegistryObject<SoundEvent> UI_TOAST_AETHER_SILVER = register("ui.toast.aether_silver");
	public static final RegistryObject<SoundEvent> UI_TOAST_AETHER_BRONZE = register("ui.toast.aether_bronze");

	public static final RegistryObject<SoundEvent> BLOCK_AETHER_PORTAL_AMBIENT = register("block.aether_portal.ambient");
	public static final RegistryObject<SoundEvent> BLOCK_DUNGEON_TRAP_TRIGGER = register("block.dungeon_trap.trigger");

	public static final RegistryObject<SoundEvent> ENTITY_MOA_AMBIENT = register("entity.moa.ambient");
	public static final RegistryObject<SoundEvent> ENTITY_MOA_FLAP = register("entity.moa.flap");

	public static final RegistryObject<SoundEvent> ENTITY_SHEEPUFF_AMBIENT = register("entity.sheepuff.ambient");
	public static final RegistryObject<SoundEvent> ENTITY_SHEEPUFF_HURT = register("entity.sheepuff.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_SHEEPUFF_DEATH = register("entity.sheepuff.death");
	public static final RegistryObject<SoundEvent> ENTITY_SHEEPUFF_SHEAR = register("entity.sheepuff.shear");

	public static final RegistryObject<SoundEvent> ENTITY_FLYING_COW_AMBIENT = register("entity.flying_cow.ambient");
	public static final RegistryObject<SoundEvent> ENTITY_FLYING_COW_HURT = register("entity.flying_cow.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_FLYING_COW_DEATH = register("entity.flying_cow.death");

	public static final RegistryObject<SoundEvent> ENTITY_PHYG_AMBIENT = register("entity.phyg.ambient");
	public static final RegistryObject<SoundEvent> ENTITY_PHYG_HURT = register("entity.phyg.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_PHYG_DEATH = register("entity.phyg.death");

	public static final RegistryObject<SoundEvent> ENTITY_AERBUNNY_HURT = register("entity.aerbunny.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_AERBUNNY_DEATH = register("entity.aerbunny.death");
	public static final RegistryObject<SoundEvent> ENTITY_AERBUNNY_LIFT = register("entity.aerbunny.lift");

	public static final RegistryObject<SoundEvent> ENTITY_ZEPHYR_AMBIENT = register("entity.zephyr.ambient");
	public static final RegistryObject<SoundEvent> ENTITY_ZEPHYR_SHOOT = register("entity.zephyr.shoot");

	public static final RegistryObject<SoundEvent> ENTITY_AERWHALE_AMBIENT = register("entity.aerwhale.ambient");
	public static final RegistryObject<SoundEvent> ENTITY_AERWHALE_DEATH = register("entity.aerwhale.death");

	public static final RegistryObject<SoundEvent> ENTITY_PROJECTILE_SHOOT = register("entity.projectile.shoot");
	public static final RegistryObject<SoundEvent> ENTITY_DART_SHOOTER_SHOOT = register("entity.dart_shooter.shoot");

	public static final RegistryObject<SoundEvent> ENTITY_SLIDER_AWAKEN = register("entity.slider.awaken");
	public static final RegistryObject<SoundEvent> ENTITY_SLIDER_COLLIDE = register("entity.slider.collide");
	public static final RegistryObject<SoundEvent> ENTITY_SLIDER_MOVE = register("entity.slider.move");
	public static final RegistryObject<SoundEvent> ENTITY_SLIDER_DEATH = register("entity.slider.death");

	public static final RegistryObject<SoundEvent> ENTITY_SUN_SPIRIT_SHOOT = register("entity.sun_spirit.shoot");

	private static RegistryObject<SoundEvent> register(String name) {
		return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(Aether.MODID, name)));
	}
}
