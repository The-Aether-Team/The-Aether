package com.aetherteam.aether.world.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTestType;

/**
 * This test returns true if the current position is on the border of the room.
 * The codec can accept 3 ints for the size of the room.
 */
public class BorderBoxPosTest extends PosRuleTest {
    public static final MapCodec<BorderBoxPosTest> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("baseX").orElse(0).forGetter(o -> o.baseX),
            Codec.INT.fieldOf("baseY").orElse(0).forGetter(o -> o.baseY),
            Codec.INT.fieldOf("baseZ").orElse(0).forGetter(o -> o.baseZ),
            Codec.INT.xmap(x -> x - 1, x -> x + 1).fieldOf("width").orElse(0).forGetter(o -> o.width),
            Codec.INT.xmap(x -> x - 1, x -> x + 1).fieldOf("height").orElse(0).forGetter(o -> o.height),
            Codec.INT.xmap(x -> x - 1, x -> x + 1).fieldOf("width").orElse(0).forGetter(o -> o.length)
    ).apply(instance, BorderBoxPosTest::new));

    private final int baseX;
    private final int baseY;
    private final int baseZ;

    private final int width;
    private final int height;
    private final int length;

    public BorderBoxPosTest(int width, int height, int length) {
        this(0, 0, 0, width, height, length);
    }

    public BorderBoxPosTest(int baseX, int baseY, int baseZ, int width, int height, int length) {
        this.baseX = baseX;
        this.baseY = baseY;
        this.baseZ = baseZ;
        this.width = width;
        this.height = height;
        this.length = length;
    }

    @Override
    public boolean test(BlockPos localPos, BlockPos relativePos, BlockPos structurePos, RandomSource random) {
        return this.isBorderBlock(localPos.getX(), localPos.getY(), localPos.getZ());
    }

    private boolean isBorderBlock(int relativeX, int relativeY, int relativeZ) {
        return relativeX == this.baseX || relativeX == this.width
                || relativeY == this.baseY || relativeY == this.height
                || relativeZ == this.baseZ || relativeZ == this.length;
    }

    @Override
    protected PosRuleTestType<?> getType() {
        return AetherPosRuleTests.BORDER_BOX.get();
    }
}
