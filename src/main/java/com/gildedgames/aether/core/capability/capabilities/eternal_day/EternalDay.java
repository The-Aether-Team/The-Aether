package com.gildedgames.aether.core.capability.capabilities.eternal_day;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherDimensions;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.capability.interfaces.IEternalDay;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.AetherTimePacket;
import com.gildedgames.aether.core.network.packet.client.CheckTimePacket;
import com.gildedgames.aether.core.network.packet.client.EternalDayPacket;
import com.gildedgames.aether.core.network.packet.client.ServerTimePacket;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EternalDay implements IEternalDay
{
    private final World world;

    private boolean isEternalDay = true;
    private boolean shouldCheckTime = true;
    private long aetherTime = 18000L;
    private long serverWorldTime;

    public EternalDay(World world) {
        this.world = world;
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putBoolean("EternalDay", this.getEternalDay());
        nbt.putBoolean("CheckTime", this.getCheckTime());
        nbt.putLong("Time", this.getAetherTime());
        nbt.putLong("ServerWorldTime", this.getServerWorldTime());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt.contains("EternalDay")) {
            this.setEternalDay(nbt.getBoolean("EternalDay"));
        }
        if (nbt.contains("CheckTime")) {
            this.setCheckTime(nbt.getBoolean("CheckTime"));
        }
        if (nbt.contains("Time")) {
            this.setAetherTime(nbt.getLong("Time"));
        }
        if (nbt.contains("ServerWorldTime")) {
            this.setServerWorldTime(nbt.getLong("ServerWorldTime"));
        }
    }

    @Override
    public void syncToClient() {
        this.setEternalDay(this.getEternalDay());
        this.setCheckTime(this.getCheckTime());
        this.setAetherTime(this.getAetherTime());
        this.setServerWorldTime(this.getServerWorldTime());
    }

    @Override
    public void serverTick(ServerWorld world) {
        if (world.dimension() == AetherDimensions.AETHER_WORLD) {
            if (!AetherConfig.COMMON.disable_eternal_day.get()) {
                Aether.LOGGER.info("s_0");
                if (this.getCheckTime()) {
                    Aether.LOGGER.info("s_1");
                    if (!this.getEternalDay()) {
                        Aether.LOGGER.info("s_2");
                        if ((world.getDayTime() % 24000) != (this.getAetherTime() % 72000)) {
                            Aether.LOGGER.info("s_3");
                            this.setServerWorldTime(world.getDayTime());
                            this.setAetherTime((long) MathHelper.approach(this.getAetherTime(), world.getDayTime(), 10.0F));
                        } else {
                            Aether.LOGGER.info("s_4");
                            this.setCheckTime(false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void clientTick(ClientWorld world) {
        if (world.dimension() == AetherDimensions.AETHER_WORLD) {
            if (!AetherConfig.COMMON.disable_eternal_day.get()) {
                Aether.LOGGER.info("c_0");
                if (this.getCheckTime()) {
                    Aether.LOGGER.info("c_1");
                    if (!this.getEternalDay()) {
                        Aether.LOGGER.info("c_2");
                        if ((this.getServerWorldTime() % 24000) != (this.getAetherTime() % 72000)) {
                            Aether.LOGGER.info("c_3");
                            world.setDayTime(this.getAetherTime());
                        }
                    } else {
                        Aether.LOGGER.info("c_4");
                        world.setDayTime(18000L);
                    }
                }
            }
        }
    }

    @Override
    public void setEternalDay(boolean isEternalDay) {
        this.sendEternalDay(isEternalDay);
        this.isEternalDay = isEternalDay;
    }

    @Override
    public boolean getEternalDay() {
        return this.isEternalDay;
    }

    @Override
    public void setCheckTime(boolean shouldCheckTime) {
        this.sendCheckTime(shouldCheckTime);
        this.shouldCheckTime = shouldCheckTime;
    }

    @Override
    public boolean getCheckTime() {
        return this.shouldCheckTime;
    }

    @Override
    public void setAetherTime(long time) {
        this.sendAetherTime(time);
        this.aetherTime = time;
    }

    @Override
    public long getAetherTime() {
        return this.aetherTime;
    }

    @Override
    public void setServerWorldTime(long time) {
        this.sendServerWorldTime(time);
        this.serverWorldTime = time;
    }

    @Override
    public long getServerWorldTime() {
        return this.serverWorldTime;
    }

    private void sendEternalDay(boolean eternalDay) {
        if (this.getWorld() instanceof ServerWorld) {
            AetherPacketHandler.sendToAll(new EternalDayPacket(eternalDay));
        }
    }

    private void sendCheckTime(boolean checkTime) {
        if (this.getWorld() instanceof ServerWorld) {
            AetherPacketHandler.sendToAll(new CheckTimePacket(checkTime));
        }
    }

    private void sendAetherTime(long time) {
        if (this.getWorld() instanceof ServerWorld) {
            AetherPacketHandler.sendToAll(new AetherTimePacket(time));
        }
    }

    private void sendServerWorldTime(long time) {
        if (this.getWorld() instanceof ServerWorld) {
            AetherPacketHandler.sendToAll(new ServerTimePacket(time));
        }
    }
}
