package com.aetherteam.aether.data.generators.tags;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.data.resources.registries.AetherDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

/**
 * Damage type tags are used to change the behavior of damage and how it is calculated.
 */
public class AetherDamageTypeTagData extends TagsProvider<DamageType> {
    public AetherDamageTypeTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, Registries.DAMAGE_TYPE, registries, Aether.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(
                AetherDamageTypes.ARMOR_PIERCING_ATTACK,
                AetherDamageTypes.INEBRIATION
        );
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
        this.tag(AetherTags.DamageTypes.IS_COLD).add(
                AetherDamageTypes.ICE_CRYSTAL
        );
    }
}
