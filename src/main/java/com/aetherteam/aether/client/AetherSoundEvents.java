package com.aetherteam.aether.client;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Aether.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_AETHER_PORTAL_AMBIENT = register("block.aether_portal.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_AETHER_PORTAL_TRAVEL = register("block.aether_portal.travel");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_AETHER_PORTAL_TRIGGER = register("block.aether_portal.trigger");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_CHEST_MIMIC_OPEN = register("block.chest_mimic.open");

    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_ALTAR_CRACKLE = register("block.altar.crackle");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_FREEZER_CRACKLE = register("block.freezer.crackle");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_INCUBATOR_CRACKLE = register("block.incubator.crackle");

    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_DUNGEON_TRAP_TRIGGER = register("block.dungeon_trap.trigger");

    public static final DeferredHolder<SoundEvent, SoundEvent> WATER_EVAPORATE = register("block.water.evaporate");

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_AMBROSIUM_SHARD = register("item.ambrosium_shard.use");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_SWET_BALL_USE = register("item.swet_ball.use");

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_DART_SHOOTER_SHOOT = register("item.dart_shooter.shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_LIGHTNING_KNIFE_SHOOT = register("item.lightning_knife.shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_HAMMER_OF_KINGBDOGZ_SHOOT = register("item.hammer_of_kingbdogz.shoot");

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARMOR_EQUIP_ZANITE = register("item.armor.equip_zanite");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARMOR_EQUIP_GRAVITITE = register("item.armor.equip_gravitite");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARMOR_EQUIP_VALKYRIE = register("item.armor.equip_valkyrie");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARMOR_EQUIP_NEPTUNE = register("item.armor.equip_neptune");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARMOR_EQUIP_PHOENIX = register("item.armor.equip_phoenix");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARMOR_EQUIP_OBSIDIAN = register("item.armor.equip_obsidian");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARMOR_EQUIP_SENTRY = register("item.armor.equip_sentry");

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ACCESSORY_EQUIP_GENERIC = register("item.accessory.equip_generic");

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ACCESSORY_EQUIP_IRON_RING = register("item.accessory.equip_iron_ring");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ACCESSORY_EQUIP_GOLD_RING = register("item.accessory.equip_gold_ring");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ACCESSORY_EQUIP_ZANITE_RING = register("item.accessory.equip_zanite_ring");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ACCESSORY_EQUIP_ICE_RING = register("item.accessory.equip_ice_ring");

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ACCESSORY_EQUIP_IRON_PENDANT = register("item.accessory.equip_iron_pendant");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ACCESSORY_EQUIP_GOLD_PENDANT = register("item.accessory.equip_gold_pendant");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ACCESSORY_EQUIP_ZANITE_PENDANT = register("item.accessory.equip_zanite_pendant");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ACCESSORY_EQUIP_ICE_PENDANT = register("item.accessory.equip_ice_pendant");

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ACCESSORY_EQUIP_CAPE = register("item.accessory.equip_cape");

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_MUSIC_DISC_AETHER_TUNE = register("item.music_disc.aether_tune");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_MUSIC_DISC_ASCENDING_DAWN = register("item.music_disc.ascending_dawn");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_MUSIC_DISC_CHINCHILLA = register("item.music_disc.chinchilla");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_MUSIC_DISC_HIGH = register("item.music_disc.high");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PHYG_AMBIENT = register("entity.phyg.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PHYG_DEATH = register("entity.phyg.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PHYG_HURT = register("entity.phyg.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PHYG_SADDLE = register("entity.phyg.saddle");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PHYG_STEP = register("entity.phyg.step");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_FLYING_COW_AMBIENT = register("entity.flying_cow.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_FLYING_COW_DEATH = register("entity.flying_cow.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_FLYING_COW_HURT = register("entity.flying_cow.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_FLYING_COW_SADDLE = register("entity.flying_cow.saddle");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_FLYING_COW_MILK = register("entity.flying_cow.milk");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_FLYING_COW_STEP = register("entity.flying_cow.step");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SHEEPUFF_AMBIENT = register("entity.sheepuff.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SHEEPUFF_DEATH = register("entity.sheepuff.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SHEEPUFF_HURT = register("entity.sheepuff.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SHEEPUFF_SHEAR = register("entity.sheepuff.shear");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SHEEPUFF_STEP = register("entity.sheepuff.step");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_AMBIENT = register("entity.moa.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_DEATH = register("entity.moa.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_HURT = register("entity.moa.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_SADDLE = register("entity.moa.saddle");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_STEP = register("entity.moa.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_FLAP = register("entity.moa.flap");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_EGG = register("entity.moa.egg");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERWHALE_AMBIENT = register("entity.aerwhale.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERWHALE_DEATH = register("entity.aerwhale.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERWHALE_HURT = register("entity.aerwhale.hurt");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERBUNNY_DEATH = register("entity.aerbunny.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERBUNNY_HURT = register("entity.aerbunny.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERBUNNY_LIFT = register("entity.aerbunny.lift");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SWET_ATTACK = register("entity.swet.attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SWET_DEATH = register("entity.swet.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SWET_HURT = register("entity.swet.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SWET_JUMP = register("entity.swet.jump");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SWET_SQUISH = register("entity.swet.squish");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_WHIRLWIND_DROP = register("entity.whirlwind.drop");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AECHOR_PLANT_SHOOT = register("entity.aechor_plant.shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AECHOR_PLANT_HURT = register("entity.aechor_plant.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AECHOR_PLANT_DEATH = register("entity.aechor_plant.death");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_COCKATRICE_SHOOT = register("entity.cockatrice.shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_COCKATRICE_AMBIENT = register("entity.cockatrice.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_COCKATRICE_DEATH = register("entity.cockatrice.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_COCKATRICE_HURT = register("entity.cockatrice.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_COCKATRICE_FLAP = register("entity.cockatrice.flap");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ZEPHYR_SHOOT = register("entity.zephyr.shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ZEPHYR_AMBIENT = register("entity.zephyr.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ZEPHYR_DEATH = register("entity.zephyr.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ZEPHYR_HURT = register("entity.zephyr.hurt");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SENTRY_DEATH = register("entity.sentry.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SENTRY_HURT = register("entity.sentry.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SENTRY_JUMP = register("entity.sentry.jump");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SENTRY_SQUISH = register("entity.sentry.squish");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SENTRY_AMBIENT = register("entity.sentry.ambient");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MIMIC_ATTACK = register("entity.mimic.attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MIMIC_DEATH = register("entity.mimic.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MIMIC_HURT = register("entity.mimic.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MIMIC_KILL = register("entity.mimic.kill");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SLIDER_AWAKEN = register("entity.slider.awaken");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SLIDER_AMBIENT = register("entity.slider.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SLIDER_COLLIDE = register("entity.slider.collide");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SLIDER_MOVE = register("entity.slider.move");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SLIDER_HURT = register("entity.slider.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SLIDER_DEATH = register("entity.slider.death");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_VALKYRIE_DEATH = register("entity.valkyrie.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_VALKYRIE_HURT = register("entity.valkyrie.hurt");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_VALKYRIE_QUEEN_DEATH = register("entity.valkyrie_queen.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_VALKYRIE_QUEEN_HURT = register("entity.valkyrie_queen.hurt");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SUN_SPIRIT_SHOOT = register("entity.sun_spirit.shoot");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_CLOUD_MINION_SHOOT = register("entity.cloud_minion.shoot");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_CLOUD_CRYSTAL_EXPLODE = register("entity.cloud_crystal.explode");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_FIRE_CRYSTAL_EXPLODE = register("entity.fire_crystal.explode");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ICE_CRYSTAL_EXPLODE = register("entity.ice_crystal.explode");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_THUNDER_CRYSTAL_EXPLODE = register("entity.thunder_crystal.explode");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_DART_HIT = register("entity.dart.hit");

    public static final DeferredHolder<SoundEvent, SoundEvent> UI_TOAST_AETHER_GENERAL = register("ui.toast.aether_general");
    public static final DeferredHolder<SoundEvent, SoundEvent> UI_TOAST_AETHER_BRONZE = register("ui.toast.aether_bronze");
    public static final DeferredHolder<SoundEvent, SoundEvent> UI_TOAST_AETHER_SILVER = register("ui.toast.aether_silver");

    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_MENU = registerHolder("music.menu");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_AETHER = registerHolder("music.aether");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String location) {
        return SOUNDS.register(location, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Aether.MODID, location)));
    }

    private static DeferredHolder<SoundEvent, SoundEvent> registerHolder(String location) {
        return SOUNDS.register(location, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Aether.MODID, location)));
    }
}
