package com.aetherteam.aether.entity.miscellaneous;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.SkyrootBoatBehavior;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;

public class SkyrootBoat extends Boat implements SkyrootBoatBehavior {
    public SkyrootBoat(EntityType<? extends SkyrootBoat> type, Level level) {
        super(type, level);
    }

    public SkyrootBoat(Level level, double x, double y, double z) {
        this(AetherEntityTypes.SKYROOT_BOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

   
    @Override
    public Item getDropItem() {
        return AetherItems.SKYROOT_BOAT.get();
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
        this.fall(this, y, onGround);
    }

   
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
