package com.aetherteam.aether.client.particle;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.nitrogen.fabric.registries.DeferredHolder;
import com.aetherteam.nitrogen.fabric.registries.DeferredRegister;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.SnowflakeParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class AetherParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Aether.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AETHER_PORTAL = PARTICLES.register("aether_portal", () -> FabricParticleTypes.simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> CRYSTAL_LEAVES = PARTICLES.register("crystal_leaves", () -> FabricParticleTypes.simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BOSS_DOORWAY_BLOCK = PARTICLES.register("door", () -> FabricParticleTypes.simple(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> EVIL_WHIRLWIND = PARTICLES.register("evil_whirlwind", () -> FabricParticleTypes.simple(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FROZEN = PARTICLES.register("frozen", () -> FabricParticleTypes.simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GOLDEN_OAK_LEAVES = PARTICLES.register("golden_oak_leaves", () -> FabricParticleTypes.simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HOLIDAY_LEAVES = PARTICLES.register("holiday_leaves", () -> FabricParticleTypes.simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PASSIVE_WHIRLWIND = PARTICLES.register("passive_whirlwind", () -> FabricParticleTypes.simple(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ZEPHYR_SNOWFLAKE = PARTICLES.register("zephyr_snowflake", () -> FabricParticleTypes.simple(false));

    /**
     * @see AetherClient#eventSetup()
     */
    public static void registerParticleFactories() {
        var registry = ParticleFactoryRegistry.getInstance();
        registry.register(AETHER_PORTAL.get(), AetherPortalParticle.Factory::new);
        registry.register(CRYSTAL_LEAVES.get(), CrystalLeavesParticle.Factory::new);
        registry.register(BOSS_DOORWAY_BLOCK.get(), DungeonBlockOverlayParticle.Factory::new);
        registry.register(EVIL_WHIRLWIND.get(), EvilWhirlwindParticle.Factory::new);
        registry.register(FROZEN.get(), FrozenParticle.Factory::new);
        registry.register(GOLDEN_OAK_LEAVES.get(), GoldenOakLeavesParticle.Factory::new);
        registry.register(HOLIDAY_LEAVES.get(), HolidayLeavesParticle.Factory::new);
        registry.register(PASSIVE_WHIRLWIND.get(), PassiveWhirlwindParticle.Factory::new);
        registry.register(ZEPHYR_SNOWFLAKE.get(), SnowflakeParticle.Provider::new);
    }
}
