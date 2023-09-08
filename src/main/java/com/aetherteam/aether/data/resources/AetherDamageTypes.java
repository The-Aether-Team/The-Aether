package com.aetherteam.aether.data.resources;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

/**
 * Custom damage types are used for death messages and different damage calculations through tags.
 */
public class AetherDamageTypes {
    public static final DamageSource ARMOR_PIERCING_ATTACK = getKey("armor_piercing_attack");
    public static final DamageSource CLOUD_CRYSTAL = getKey("cloud_crystal");
    public static final DamageSource CRUSH = getKey("crush");
    public static final DamageSource FIRE_CRYSTAL = getKey("fire_crystal");
    public static final DamageSource FLOATING_BLOCK = getKey("floating_block");
    public static final DamageSource ICE_CRYSTAL = getKey("ice_crystal");
    public static final DamageSource INCINERATION = getKey("incineration");
    public static final DamageSource INEBRIATION = getKey("inebriation");
    public static final DamageSource THUNDER_CRYSTAL = getKey("thunder_crystal");

//    public static void bootstrap(BootstapContext<DamageType> context) {
//        context.register(ARMOR_PIERCING_ATTACK, new DamageType("mob", 0.1F));
//        context.register(CLOUD_CRYSTAL, new DamageType("aether.cloud_crystal", 0.1F));
//        context.register(CRUSH, new DamageType("aether.crush", 0.1F));
//        context.register(FIRE_CRYSTAL, new DamageType("aether.fire_crystal", 0.1F, DamageEffects.BURNING));
//        context.register(FLOATING_BLOCK, new DamageType("aether.floating_block", 0.1F));
//        context.register(ICE_CRYSTAL, new DamageType("aether.ice_crystal", 0.1F, DamageEffects.FREEZING));
//        context.register(INCINERATION, new DamageType("aether.incineration", 0.1F, DamageEffects.BURNING));
//        context.register(INEBRIATION, new DamageType("aether.inebriation", 0.1F));
//        context.register(THUNDER_CRYSTAL, new DamageType("aether.thunder_crystal", 0.1F));
//    }

//    private static ResourceKey<DamageType> getKey(String name) {
//        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Aether.MODID, name));
//    }

//    public static DamageSource damageSource(Level level, ResourceKey<DamageType> key) {
//        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
//    }
//
//    public static DamageSource entityDamageSource(Level level, ResourceKey<DamageType> key, @Nullable Entity entity) {
//        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key), entity);
//    }
//
//    public static DamageSource indirectEntityDamageSource(Level level, ResourceKey<DamageType> key, @Nullable Entity source, @Nullable Entity trueSource) {
//        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key), source, trueSource);
//    }
}
