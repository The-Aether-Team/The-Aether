package com.aetherteam.aether.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.HashMap;
import java.util.Map;

public class MobAccessoryAttachment {
    private final Map<String, Float> accessoryDropChances;

    public static final Codec<MobAccessoryAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, Codec.FLOAT).fieldOf("drop_chances").forGetter(MobAccessoryAttachment::getAccessoryDropChances)
    ).apply(instance, MobAccessoryAttachment::new));

    public MobAccessoryAttachment() {
        this.accessoryDropChances = new HashMap<>(Map.ofEntries(
                Map.entry("hands", 0.085F),
                Map.entry("necklace", 0.085F),
                Map.entry("aether_gloves", 0.085F),
                Map.entry("aether_pendant", 0.085F)
        ));
    }

    private MobAccessoryAttachment(Map<String, Float> dropChances) {
        this.accessoryDropChances = dropChances;
    }

    public void setGuaranteedDrop(String identifier) {
        if (identifier.equals("hands") || identifier.equals("necklace") || identifier.equals("aether_gloves") || identifier.equals("aether_pendant")) {
            this.getAccessoryDropChances().put(identifier, 2.0F);
        }
    }

    public float getEquipmentDropChance(String identifier) {
        if (identifier.equals("hands") || identifier.equals("necklace") || identifier.equals("aether_gloves") || identifier.equals("aether_pendant")) {
            return this.getAccessoryDropChances().get(identifier);
        }
        return 0.0F;
    }

    public void setDropChance(String identifier, float chance) {
        if (identifier.equals("hands") || identifier.equals("necklace") || identifier.equals("aether_gloves") || identifier.equals("aether_pendant")) {
            this.getAccessoryDropChances().put(identifier, chance);
        }
    }

    public Map<String, Float> getAccessoryDropChances() {
        return this.accessoryDropChances;
    }
}
