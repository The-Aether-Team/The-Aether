package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.Tag;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AetherEntityTagData extends EntityTypeTagsProvider
{
    public AetherEntityTagData(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, Aether.MODID, existingFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Aether Entity Tags";
    }

    @Override
    protected void addTags() {
        //aether
        tag(AetherTags.Entities.PIGS).add(
                EntityType.PIG,
                AetherEntityTypes.PHYG.get(),
                EntityType.PIGLIN,
                EntityType.PIGLIN_BRUTE,
                EntityType.ZOMBIFIED_PIGLIN,
                EntityType.HOGLIN,
                EntityType.ZOGLIN);
        tag(AetherTags.Entities.NO_SKYROOT_DOUBLE_DROPS).add(
                EntityType.PLAYER,
                EntityType.WITHER,
                EntityType.ENDER_DRAGON);
        tag(AetherTags.Entities.DEFLECTABLE_PROJECTILES).addTag(EntityTypeTags.ARROWS).add(
                EntityType.EGG,
                EntityType.SMALL_FIREBALL,
                EntityType.FIREBALL,
                EntityType.SNOWBALL,
                EntityType.LLAMA_SPIT,
                EntityType.TRIDENT,
                EntityType.SHULKER_BULLET,
                AetherEntityTypes.GOLDEN_DART.get(),
                AetherEntityTypes.POISON_DART.get(),
                AetherEntityTypes.ENCHANTED_DART.get(),
                AetherEntityTypes.POISON_NEEDLE.get(),
                AetherEntityTypes.ZEPHYR_SNOWBALL.get(),
                AetherEntityTypes.LIGHTNING_KNIFE.get(),
                AetherEntityTypes.HAMMER_PROJECTILE.get());
        tag(AetherTags.Entities.SWET_TARGET).add(
                EntityType.CHICKEN,
                EntityType.COW,
                EntityType.PIG,
                EntityType.SHEEP,
                AetherEntityTypes.FLYING_COW.get(),
                AetherEntityTypes.PHYG.get(),
                AetherEntityTypes.SHEEPUFF.get()
        );

        //vanilla
        tag(EntityTypeTags.IMPACT_PROJECTILES).add(
                AetherEntityTypes.GOLDEN_DART.get(),
                AetherEntityTypes.POISON_DART.get(),
                AetherEntityTypes.ENCHANTED_DART.get(),
                AetherEntityTypes.LIGHTNING_KNIFE.get(),
                AetherEntityTypes.HAMMER_PROJECTILE.get());
        tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).add(AetherEntityTypes.AERBUNNY.get());
        tag(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES).add(AetherEntityTypes.FIRE_MINION.get());
    }

    @Nonnull
    protected TagsProvider.TagAppender<EntityType<?>> tag(@Nonnull Tag.Named<EntityType<?>> tag) {
        return super.tag(tag);
    }
}
