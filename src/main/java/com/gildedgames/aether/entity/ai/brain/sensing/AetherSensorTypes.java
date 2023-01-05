package com.gildedgames.aether.entity.ai.brain.sensing;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.entity.monster.dungeon.boss.slider.Slider;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AetherSensorTypes {
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, Aether.MODID);

    public static final RegistryObject<SensorType<InDungeonPlayerSensor<Slider>>> SLIDER_PLAYER_SENSOR = SENSOR_TYPES.register("in_dungeon_player_sensor",
            () -> new SensorType<>(InDungeonPlayerSensor::new));
}
