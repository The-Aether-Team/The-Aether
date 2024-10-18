package com.aetherteam.aether.data.resources.registries;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
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
    public static final ResourceKey<DamageType> CLOUD_CRYSTAL = createKey("cloud_crystal");
    public static final ResourceKey<DamageType> CRUSH = createKey("crush");
    public static final ResourceKey<DamageType> FIRE_CRYSTAL = createKey("fire_crystal");
    public static final ResourceKey<DamageType> FLOATING_BLOCK = createKey("floating_block");
    public static final ResourceKey<DamageType> ICE_CRYSTAL = createKey("ice_crystal");
    public static final ResourceKey<DamageType> INCINERATION = createKey("incineration");
    public static final ResourceKey<DamageType> INEBRIATION = createKey("inebriation");
    public static final ResourceKey<DamageType> THUNDER_CRYSTAL = createKey("thunder_crystal");

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(CLOUD_CRYSTAL, new DamageType("aether.cloud_crystal", 0.1F));
        context.register(CRUSH, new DamageType("aether.crush", 0.1F));
        context.register(FIRE_CRYSTAL, new DamageType("aether.fire_crystal", 0.1F, DamageEffects.BURNING));
        context.register(FLOATING_BLOCK, new DamageType("aether.floating_block", 0.1F));
        context.register(ICE_CRYSTAL, new DamageType("aether.ice_crystal", 0.1F, DamageEffects.FREEZING));
        context.register(INCINERATION, new DamageType("aether.incineration", 0.1F, DamageEffects.BURNING));
        context.register(INEBRIATION, new DamageType("aether.inebriation", 0.1F));
        context.register(THUNDER_CRYSTAL, new DamageType("aether.thunder_crystal", 0.1F));
    }

    private static ResourceKey<DamageType> createKey(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(Aether.MODID, name));
    }

    public static DamageSource damageSource(Level level, ResourceKey<DamageType> key) {
        return new DamageSource(level.holderOrThrow(key));
    }

    public static DamageSource entityDamageSource(Level level, ResourceKey<DamageType> key, @Nullable Entity entity) {
        return new DamageSource(level.holderOrThrow(key), entity);
    }

    public static DamageSource indirectEntityDamageSource(Level level, ResourceKey<DamageType> key, @Nullable Entity source, @Nullable Entity trueSource) {
        return new DamageSource(level.holderOrThrow(key), source, trueSource);
    }
}
