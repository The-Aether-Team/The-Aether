package com.aetherteam.aether.client.particle;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.AetherClient;
import net.minecraft.client.particle.SnowflakeParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Aether.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AETHER_PORTAL = PARTICLES.register("aether_portal", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> CRYSTAL_LEAVES = PARTICLES.register("crystal_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BOSS_DOORWAY_BLOCK = PARTICLES.register("door", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> EVIL_WHIRLWIND = PARTICLES.register("evil_whirlwind", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FROZEN = PARTICLES.register("frozen", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GOLDEN_OAK_LEAVES = PARTICLES.register("golden_oak_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HOLIDAY_LEAVES = PARTICLES.register("holiday_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PASSIVE_WHIRLWIND = PARTICLES.register("passive_whirlwind", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ZEPHYR_SNOWFLAKE = PARTICLES.register("zephyr_snowflake", () -> new SimpleParticleType(false));

    /**
     * @see AetherClient#eventSetup()
     */
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(AETHER_PORTAL.get(), AetherPortalParticle.Factory::new);
        event.registerSpriteSet(CRYSTAL_LEAVES.get(), CrystalLeavesParticle.Factory::new);
        event.registerSpriteSet(BOSS_DOORWAY_BLOCK.get(), DungeonBlockOverlayParticle.Factory::new);
        event.registerSpriteSet(EVIL_WHIRLWIND.get(), EvilWhirlwindParticle.Factory::new);
        event.registerSpriteSet(FROZEN.get(), FrozenParticle.Factory::new);
        event.registerSpriteSet(GOLDEN_OAK_LEAVES.get(), GoldenOakLeavesParticle.Factory::new);
        event.registerSpriteSet(HOLIDAY_LEAVES.get(), HolidayLeavesParticle.Factory::new);
        event.registerSpriteSet(PASSIVE_WHIRLWIND.get(), PassiveWhirlwindParticle.Factory::new);
        event.registerSpriteSet(ZEPHYR_SNOWFLAKE.get(), SnowflakeParticle.Provider::new);
    }
}
