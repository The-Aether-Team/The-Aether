package com.gildedgames.aether.data.generators.tags;

import com.gildedgames.aether.data.resources.AetherDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;

import java.util.concurrent.CompletableFuture;

/**
 * Damage type tags are used to change the behavior of damage and how it is calculated.
 */
public class AetherDamageTypeTagData extends DamageTypeTagsProvider {
    public AetherDamageTypeTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(AetherDamageTypes.INEBRIATION);
        this.tag(DamageTypeTags.DAMAGES_HELMET).add(AetherDamageTypes.FLOATING_BLOCK);
        this.tag(DamageTypeTags.IS_FIRE).add(
                AetherDamageTypes.INCINERATION,
                AetherDamageTypes.FIRE_CRYSTAL
        );
        this.tag(DamageTypeTags.IS_PROJECTILE).add(
                AetherDamageTypes.CLOUD_CRYSTAL,
                AetherDamageTypes.FIRE_CRYSTAL,
                AetherDamageTypes.ICE_CRYSTAL,
                AetherDamageTypes.THUNDER_CRYSTAL
        );
    }
}
