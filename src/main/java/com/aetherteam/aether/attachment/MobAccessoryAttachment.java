package com.aetherteam.aether.attachment;

import com.aetherteam.aether.inventory.container.AccessoryContainer;
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
                Map.entry("GLOVES", 0.085F),
                Map.entry("PENDANT", 0.085F)
        ));
    }

    private MobAccessoryAttachment(Map<String, Float> dropChances) {
        this.accessoryDropChances = new HashMap<>(dropChances);
    }

    public void setGuaranteedDrop(AccessoryContainer.SlotType identifier) {
        if (this.accessoryDropChances.containsKey(identifier.name())) {
            this.getAccessoryDropChances().put(identifier.name(), 2.0F);
        }
    }

    public float getEquipmentDropChance(AccessoryContainer.SlotType identifier) {
        if (this.accessoryDropChances.containsKey(identifier.name())) {
            return this.getAccessoryDropChances().get(identifier.name());
        }
        return 0.0F;
    }

    public void setDropChance(AccessoryContainer.SlotType identifier, float chance) {
        if (this.accessoryDropChances.containsKey(identifier.name())) {
            this.getAccessoryDropChances().put(identifier.name(), chance);
        }
    }

    public Map<String, Float> getAccessoryDropChances() {
        return this.accessoryDropChances;
    }
}
