package com.aetherteam.aether.capability.accessory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;

import java.util.HashMap;
import java.util.Map;

public class MobAccessoryCapability implements MobAccessory {
    private final Mob mob;

    private final Map<String, Float> accessoryDropChances = new HashMap<>(Map.ofEntries(
            Map.entry("hands", 0.085F),
            Map.entry("necklace", 0.085F),
            Map.entry("aether_gloves", 0.085F),
            Map.entry("aether_pendant", 0.085F)
    ));

    private final Map<String, String> oppositeIdentifier = new HashMap<>(Map.ofEntries(
            Map.entry("hands", "aether_gloves"),
            Map.entry("necklace", "aether_pendant"),
            Map.entry("aether_gloves", "hands"),
            Map.entry("aether_pendant", "necklace")
    ));

    public MobAccessoryCapability(Mob mob) {
        this.mob = mob;
    }

    @Override
    public Mob getMob() {
        return this.mob;
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        CompoundTag entryTag = new CompoundTag();
        for (Map.Entry<String, Float> entry : this.getAccessoryDropChances().entrySet()) {
            entryTag.putFloat(entry.getKey(), entry.getValue());
        }
        tag.put("DropChances", entryTag);
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        if (tag.contains("DropChances")) {
            CompoundTag entryTag = tag.getCompound("DropChances");
            for (String key : entryTag.getAllKeys()) {
                Float value = entryTag.getFloat(key);
                this.getAccessoryDropChances().put(key, value);
            }
        }
    }

    public void setGuaranteedDrop(String identifier) {
        this.setDropChance(identifier, 2.0F);
    }

    public float getEquipmentDropChance(String identifier) {
        return this.getAccessoryDropChances().getOrDefault(identifier, 2.0F);
    }

    public void setDropChance(String identifier, float chance) {
        if (identifier.equals("hands") || identifier.equals("necklace") || identifier.equals("aether_gloves") || identifier.equals("aether_pendant")) {
            this.getAccessoryDropChances().put(identifier, chance);
            this.getAccessoryDropChances().put(oppositeIdentifier.getOrDefault(identifier, identifier), chance);
        }
    }

    public Map<String, Float> getAccessoryDropChances() {
        return this.accessoryDropChances;
    }
}
