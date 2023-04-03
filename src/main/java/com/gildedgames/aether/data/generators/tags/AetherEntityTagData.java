package com.gildedgames.aether.data.generators.tags;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.AetherTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class AetherEntityTagData extends EntityTypeTagsProvider {
    public AetherEntityTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper helper) {
        super(output, registries, Aether.MODID, helper);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        // Aether
        this.tag(AetherTags.Entities.WHIRLWIND_UNAFFECTED).add(AetherEntityTypes.AECHOR_PLANT.get()).addTag(Tags.EntityTypes.BOSSES);
        this.tag(AetherTags.Entities.PIGS).add(
                EntityType.PIG,
                AetherEntityTypes.PHYG.get(),
                EntityType.PIGLIN,
                EntityType.PIGLIN_BRUTE,
                EntityType.ZOMBIFIED_PIGLIN,
                EntityType.HOGLIN,
                EntityType.ZOGLIN);
        this.tag(AetherTags.Entities.NO_SKYROOT_DOUBLE_DROPS).add(
                EntityType.PLAYER,
                EntityType.WITHER,
                EntityType.ENDER_DRAGON);
        this.tag(AetherTags.Entities.NO_AMBROSIUM_DROPS).add(EntityType.PLAYER);
        this.tag(AetherTags.Entities.UNLAUNCHABLE);
        this.tag(AetherTags.Entities.NO_CANDY_CANE_DROPS).add(EntityType.PLAYER);
        this.tag(AetherTags.Entities.DEFLECTABLE_PROJECTILES).addTag(EntityTypeTags.ARROWS).add(
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

        // Forge
        this.tag(Tags.EntityTypes.BOSSES).add(
                AetherEntityTypes.SLIDER.get(),
                AetherEntityTypes.VALKYRIE_QUEEN.get(),
                AetherEntityTypes.SUN_SPIRIT.get()
        );

        // Vanilla
        this.tag(EntityTypeTags.IMPACT_PROJECTILES).add(
                AetherEntityTypes.GOLDEN_DART.get(),
                AetherEntityTypes.POISON_DART.get(),
                AetherEntityTypes.ENCHANTED_DART.get(),
                AetherEntityTypes.LIGHTNING_KNIFE.get(),
                AetherEntityTypes.HAMMER_PROJECTILE.get());
        this.tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).add(AetherEntityTypes.AERBUNNY.get());
        this.tag(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES).add(AetherEntityTypes.FIRE_MINION.get());
        this.tag(EntityTypeTags.FROG_FOOD).add(
                AetherEntityTypes.BLUE_SWET.get(),
                AetherEntityTypes.GOLDEN_SWET.get(),
                AetherEntityTypes.SENTRY.get());
    }
}
