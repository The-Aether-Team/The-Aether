package com.aetherteam.aether.capability.lightning;

import com.aetherteam.aether.capability.AetherCapabilities;
import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;

import java.util.Optional;

public interface LightningTracker extends Component {
    LightningBolt getLightningBolt();

    static Optional<LightningTracker> get(LightningBolt lightningBolt) {
        return AetherCapabilities.LIGHTNING_TRACKER_CAPABILITY.maybeGet(lightningBolt);
    }

    void setOwner(Entity owner);
    Entity getOwner();
}
