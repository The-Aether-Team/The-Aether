package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.core.data.provider.DimensionTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class AetherDimensionTags extends DimensionTagsProvider {
    public AetherDimensionTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, Aether.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {

    }

    @Override
    public String getName() {
        return "Aether Dimension Tags";
    }
}
