package com.gildedgames.aether.core.registry;

import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.api.registers.MoaType;
import net.minecraft.util.RandomSource;

import java.util.*;

public class AetherMoaTypes
{
    public static Map<String, MoaType> MOA_TYPES = new HashMap<>();

    public static final MoaType BLUE = register("blue", new MoaType.Properties().egg(AetherItems.BLUE_MOA_EGG).maxJumps(3).speed(0.15F).texture("textures/entity/mobs/moa/blue_moa.png"));
    public static final MoaType WHITE = register("white", new MoaType.Properties().egg(AetherItems.WHITE_MOA_EGG).maxJumps(4).speed(0.15F).texture("textures/entity/mobs/moa/white_moa.png"));
    public static final MoaType BLACK = register("black", new MoaType.Properties().egg(AetherItems.BLACK_MOA_EGG).maxJumps(8).speed(0.15F).texture("textures/entity/mobs/moa/black_moa.png").saddleTexture("textures/entity/mobs/moa/black_moa_saddle.png"));
    public static final MoaType ORANGE = register("orange", new MoaType.Properties().egg(AetherItems.ORANGE_MOA_EGG).maxJumps(2).speed(0.3F).texture("textures/entity/mobs/moa/orange_moa.png"));

    public static MoaType register(String registryName, MoaType.Properties properties) {
        MoaType moaType = new MoaType(registryName, properties);
        MOA_TYPES.put(registryName, moaType);
        return moaType;
    }

    public static MoaType getRandomMoaType() {
        RandomSource random = RandomSource.create();
        List<MoaType> moaTypes = new ArrayList<>(MOA_TYPES.values());
        return moaTypes.get(random.nextInt(moaTypes.size()));
    }
}
