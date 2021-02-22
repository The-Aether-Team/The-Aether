package com.aether.data;

import com.aether.Aether;
import com.aether.registry.AetherEntityTypes;
import com.aether.registry.AetherTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.EntityTypeTagsProvider;
import net.minecraft.data.TagsProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class AetherEntityTags extends EntityTypeTagsProvider
{
    public AetherEntityTags(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, Aether.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Aether Entity Tags";
    }

    @Override
    protected void registerTags() {
        //aether
        tag(AetherTags.Entities.PIGS)
                .add(EntityType.PIG)
                .add(AetherEntityTypes.PHYG_TYPE)
                .add(EntityType.PIGLIN)
                .add(EntityType.field_242287_aj) //PIGLIN BRUTE
                .add(EntityType.ZOMBIFIED_PIGLIN)
                .add(EntityType.HOGLIN)
                .add(EntityType.ZOGLIN);
        tag(AetherTags.Entities.NO_SKYROOT_DOUBLE_DROPS)
                .add(EntityType.PLAYER)
                .add(EntityType.WITHER)
                .add(EntityType.ENDER_DRAGON);

        //vanilla
        tag(EntityTypeTags.ARROWS)
                .add(AetherEntityTypes.PHOENIX_ARROW_TYPE)
                .add(AetherEntityTypes.SPECTRAL_PHOENIX_ARROW_TYPE);
        tag(EntityTypeTags.IMPACT_PROJECTILES)
                .add(AetherEntityTypes.GOLDEN_DART_TYPE)
                .add(AetherEntityTypes.POISON_DART_TYPE)
                .add(AetherEntityTypes.ENCHANTED_DART_TYPE)
                .add(AetherEntityTypes.LIGHTNING_KNIFE_TYPE) //TODO: Lightning knife doesnt work with this.
                .add(AetherEntityTypes.HAMMER_PROJECTILE_TYPE);
    }

    private TagsProvider.Builder<EntityType<?>> tag(ITag.INamedTag<EntityType<?>> tag) {
        return this.getOrCreateBuilder(tag);
    }
}
