package com.gildedgames.aether.api.registers;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.AetherMoaTypes;
import com.gildedgames.aether.item.AetherItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class MoaType {
    private final Supplier<Item> egg;
    private final int maxJumps;
    private final float speed;
    private final ResourceLocation texture;
    private final ResourceLocation saddleTexture;

    public MoaType(MoaType.Properties properties) {
        this(properties.egg, properties.maxJumps, properties.speed, properties.texture, properties.saddleTexture);
    }

    public MoaType(Supplier<Item> egg, int maxJumps, float speed, ResourceLocation texture, ResourceLocation saddleTexture) {
        this.egg = egg;
        this.maxJumps = maxJumps;
        this.speed = speed;
        this.texture = texture;
        this.saddleTexture = saddleTexture;
    }

    public Item getEgg() {
        return this.egg.get();
    }

    public int getMaxJumps() {
        return this.maxJumps;
    }

    public float getSpeed() {
        return this.speed;
    }

    public ResourceLocation getMoaTexture() {
        return this.texture;
    }

    public ResourceLocation getSaddleTexture() {
        return this.saddleTexture;
    }

    public ResourceLocation getId() {
        return AetherMoaTypes.MOA_TYPE_REGISTRY.get().getKey(this);
    }

    public String toString() {
        return this.getId().toString();
    }

    public static class Properties {
        private Supplier<Item> egg = AetherItems.BLUE_MOA_EGG;
        private int maxJumps = 3;
        private float speed = 0.1F;
        private ResourceLocation texture = new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/blue_moa.png");
        private ResourceLocation saddleTexture = new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/moa_saddle.png");

        public MoaType.Properties egg(Supplier<Item> egg) {
            this.egg = egg;
            return this;
        }

        public MoaType.Properties maxJumps(int maxJumps) {
            this.maxJumps = maxJumps;
            return this;
        }

        public MoaType.Properties speed(float speed) {
            this.speed = speed;
            return this;
        }

        public MoaType.Properties texture(String texture) {
            this.texture = new ResourceLocation(Aether.MODID, texture);
            return this;
        }

        public MoaType.Properties texture(String id, String texture) {
            this.texture = new ResourceLocation(id, texture);
            return this;
        }

        public MoaType.Properties saddleTexture(String saddleTexture) {
            this.saddleTexture = new ResourceLocation(Aether.MODID, saddleTexture);
            return this;
        }

        public MoaType.Properties saddleTexture(String id, String saddleTexture) {
            this.saddleTexture = new ResourceLocation(id, saddleTexture);
            return this;
        }

        public static MoaType.Properties propertiesFromType(MoaType moaType) {
            MoaType.Properties props = new MoaType.Properties();
            props.egg = moaType.egg;
            props.maxJumps = moaType.maxJumps;
            props.speed = moaType.speed;
            props.texture = moaType.texture;
            props.saddleTexture = moaType.saddleTexture;
            return props;
        }
    }
}
