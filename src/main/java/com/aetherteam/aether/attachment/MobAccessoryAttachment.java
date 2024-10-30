package com.aetherteam.aether.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.wispforest.accessories.api.slot.SlotTypeReference;

import java.util.HashMap;
import java.util.Map;

public class MobAccessoryAttachment {
    private final Map<String, Float> accessoryDropChances;

    public static final Codec<MobAccessoryAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, Codec.FLOAT).fieldOf("drop_chances").forGetter(MobAccessoryAttachment::getAccessoryDropChances)
    ).apply(instance, MobAccessoryAttachment::new));

    public MobAccessoryAttachment() {
        this.accessoryDropChances = new HashMap<>(Map.ofEntries(
                Map.entry("hand", 0.085F),
                Map.entry("necklace", 0.085F),
                Map.entry("aether:gloves_slot", 0.085F),
                Map.entry("aether:pendant_slot", 0.085F)
        ));
    }

    private MobAccessoryAttachment(Map<String, Float> dropChances) {
        this.accessoryDropChances = dropChances;
    }

    public void setGuaranteedDrop(SlotTypeReference identifier) {
        if (this.accessoryDropChances.containsKey(identifier.slotName())) {
            this.getAccessoryDropChances().put(identifier.slotName(), 2.0F);
        }
    }

    public float getEquipmentDropChance(SlotTypeReference identifier) {
        if (this.accessoryDropChances.containsKey(identifier.slotName())) {
            return this.getAccessoryDropChances().get(identifier.slotName());
        }
        return 0.0F;
    }

    public void setDropChance(SlotTypeReference identifier, float chance) {
        if (this.accessoryDropChances.containsKey(identifier.slotName())) {
            this.getAccessoryDropChances().put(identifier.slotName(), chance);
        }
    }

    public Map<String, Float> getAccessoryDropChances() {
        return this.accessoryDropChances;
    }
}
