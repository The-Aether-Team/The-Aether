package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.EntityTypeTagsProvider;
import net.minecraft.data.TagsProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class AetherEntityTagData extends EntityTypeTagsProvider
{
    public AetherEntityTagData(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, Aether.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Aether Entity Tags";
    }

    @Override
    protected void addTags() {
        //aether
        tag(AetherTags.Entities.PIGS)
                .add(EntityType.PIG)
                .add(AetherEntityTypes.PHYG.get())
                .add(EntityType.PIGLIN)
                .add(EntityType.PIGLIN_BRUTE)
                .add(EntityType.ZOMBIFIED_PIGLIN)
                .add(EntityType.HOGLIN)
                .add(EntityType.ZOGLIN);
        tag(AetherTags.Entities.NO_SKYROOT_DOUBLE_DROPS)
                .add(EntityType.PLAYER)
                .add(EntityType.WITHER)
                .add(EntityType.ENDER_DRAGON);
        tag(AetherTags.Entities.DEFLECTABLE_PROJECTILES)
                .addTag(EntityTypeTags.ARROWS)
                .add(EntityType.EGG)
                .add(EntityType.SMALL_FIREBALL)
                .add(EntityType.FIREBALL)
                .add(EntityType.SNOWBALL)
                .add(AetherEntityTypes.GOLDEN_DART.get())
                .add(AetherEntityTypes.POISON_DART.get())
                .add(AetherEntityTypes.ENCHANTED_DART.get())
                .add(AetherEntityTypes.POISON_NEEDLE.get())
                .add(AetherEntityTypes.ZEPHYR_SNOWBALL.get())
                .add(AetherEntityTypes.LIGHTNING_KNIFE.get())
                .add(AetherEntityTypes.HAMMER_PROJECTILE.get());

        //vanilla
        tag(EntityTypeTags.IMPACT_PROJECTILES)
                .add(AetherEntityTypes.GOLDEN_DART.get())
                .add(AetherEntityTypes.POISON_DART.get())
                .add(AetherEntityTypes.ENCHANTED_DART.get())
                .add(AetherEntityTypes.LIGHTNING_KNIFE.get())
                .add(AetherEntityTypes.HAMMER_PROJECTILE.get());
    }

    protected TagsProvider.Builder<EntityType<?>> tag(ITag.INamedTag<EntityType<?>> tag) {
        return super.tag(tag);
    }
}
