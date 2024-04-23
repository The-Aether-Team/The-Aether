package com.aetherteam.aether.entity.miscellaneous;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.item.AetherItems;
import io.github.fabricators_of_create.porting_lib.entity.PortingLibEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SkyrootChestBoat extends ChestBoat implements SkyrootBoatBehavior {
    public SkyrootChestBoat(EntityType<? extends SkyrootChestBoat> entityType, Level level) {
        super(entityType, level);
    }

    public SkyrootChestBoat(Level level, double x, double y, double z) {
        this(AetherEntityTypes.SKYROOT_CHEST_BOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public Item getDropItem() {
        return AetherItems.SKYROOT_CHEST_BOAT.get();
    }

    /**
     * @see SkyrootBoatBehavior#fall(Boat, double, boolean)
     */
    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
        this.fall(this, y, onGround);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return PortingLibEntity.getEntitySpawningPacket(this);
    }
}
