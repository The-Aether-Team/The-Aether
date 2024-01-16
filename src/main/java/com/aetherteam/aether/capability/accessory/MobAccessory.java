package com.aetherteam.aether.capability.accessory;

import com.aetherteam.aether.capability.AetherCapabilities;
import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.world.entity.Mob;

import java.util.Map;
import java.util.Optional;

public interface MobAccessory extends Component {
    Mob getMob();

    static Optional<MobAccessory> get(Mob mob) {
        return AetherCapabilities.MOB_ACCESSORY_CAPABILITY.maybeGet(mob);
    }

    void setGuaranteedDrop(String identifier);

    float getEquipmentDropChance(String identifier);

    void setDropChance(String identifier, float chance);

    Map<String, Float> getAccessoryDropChances();
}
