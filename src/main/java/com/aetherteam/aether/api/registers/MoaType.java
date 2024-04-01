package com.aetherteam.aether.api.registers;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.AetherMoaTypes;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class MoaType {
    private final Supplier<? extends Item> egg;
    private final int maxJumps;
    private final float speed;
    private final int spawnChance;
    private final ResourceLocation texture;
    private final ResourceLocation saddleTexture;

    public MoaType(MoaType.Properties properties) {
        this(properties.egg, properties.maxJumps, properties.speed, properties.spawnChance, properties.texture, properties.saddleTexture);
    }

    public MoaType(Supplier<? extends Item> egg, int maxJumps, float speed, int spawnChance, ResourceLocation texture, ResourceLocation saddleTexture) {
        this.egg = egg;
        this.maxJumps = maxJumps;
        this.speed = speed;
        this.spawnChance = spawnChance;
        this.texture = texture;
        this.saddleTexture = saddleTexture;
    }

    /**
     * @return An egg {@link Item} to be laid by this {@link MoaType}.
     */
    public Item getEgg() {
        return this.egg.get();
    }

    /**
     * @return An {@link Integer} for the max amount of midair jumps this {@link MoaType} as.
     */
    public int getMaxJumps() {
        return this.maxJumps;
    }

    /**
     * @return A {@link Float} for the {@link MoaType}'s movement and flight speed.
     */
    public float getSpeed() {
        return this.speed;
    }

    /**
     * @return An {@link Integer} for the {@link MoaType}'s spawn weight. This is added and considered in the weight of all other {@link MoaType}s.
     * @see AetherMoaTypes#getWeightedChance(RandomSource)
     */
    public int getSpawnChance() {
        return this.spawnChance;
    }

    /**
     * @return The {@link ResourceLocation} of the {@link MoaType}'s texture.
     */
    public ResourceLocation getMoaTexture() {
        return this.texture;
    }

    /**
     * @return The {@link ResourceLocation} of the {@link MoaType}'s saddle texture.
     */
    public ResourceLocation getSaddleTexture() {
        return this.saddleTexture;
    }

    /**
     * @return The {@link ResourceLocation} of the {@link MoaType}'s full registry ID.
     */
    public ResourceLocation getId() {
        return AetherMoaTypes.MOA_TYPE_REGISTRY.getKey(this);
    }

    /**
     * @return The {@link String} of the {@link MoaType}'s full registry ID, converted from a {@link ResourceLocation} from {@link MoaType#getId()}.
     */
    public String toString() {
        return this.getId().toString();
    }

    public static class Properties {
        private Supplier<? extends Item> egg = AetherItems.BLUE_MOA_EGG;
        private int maxJumps = 3;
        private float speed = 0.1F;
        private int spawnChance = 50;
        private ResourceLocation texture = new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/blue_moa.png");
        private ResourceLocation saddleTexture = new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/moa_saddle.png");

        /**
         * @see MoaType#getEgg()
         */
        public MoaType.Properties egg(Supplier<? extends Item> egg) {
            this.egg = egg;
            return this;
        }

        /**
         * @see MoaType#getMaxJumps()
         */
        public MoaType.Properties maxJumps(int maxJumps) {
            this.maxJumps = maxJumps;
            return this;
        }

        /**
         * @see MoaType#getSpeed()
         */
        public MoaType.Properties speed(float speed) {
            this.speed = speed;
            return this;
        }

        /**
         * @see MoaType#getSpawnChance()
         */
        public MoaType.Properties spawnChance(int spawnChance) {
            this.spawnChance = spawnChance;
            return this;
        }

        /**
         * Sets the {@link MoaType} texture with the "aether" mod ID.
         *
         * @see MoaType#getMoaTexture()
         */
        public MoaType.Properties texture(String texture) {
            this.texture = new ResourceLocation(Aether.MODID, texture);
            return this;
        }

        /**
         * Sets the {@link MoaType} texture with a given mod ID.
         *
         * @param id The given mod ID {@link String}.
         * @see MoaType#getMoaTexture()
         */
        public MoaType.Properties texture(String id, String texture) {
            this.texture = new ResourceLocation(id, texture);
            return this;
        }

        /**
         * Sets the {@link MoaType} saddle texture with the "aether" mod ID.
         *
         * @see MoaType#getMoaTexture()
         */
        public MoaType.Properties saddleTexture(String saddleTexture) {
            this.saddleTexture = new ResourceLocation(Aether.MODID, saddleTexture);
            return this;
        }

        /**
         * Sets the {@link MoaType} saddle texture with a given mod ID.
         *
         * @param id The given mod ID {@link String}.
         * @see MoaType#getMoaTexture()
         */
        public MoaType.Properties saddleTexture(String id, String saddleTexture) {
            this.saddleTexture = new ResourceLocation(id, saddleTexture);
            return this;
        }

        public static MoaType.Properties propertiesFromType(MoaType moaType) {
            MoaType.Properties props = new MoaType.Properties();
            props.egg = moaType.egg;
            props.maxJumps = moaType.maxJumps;
            props.speed = moaType.speed;
            props.spawnChance = moaType.spawnChance;
            props.texture = moaType.texture;
            props.saddleTexture = moaType.saddleTexture;
            return props;
        }
    }
}
