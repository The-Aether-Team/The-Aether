package com.gildedgames.aether.client.renderer.entity.layers;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.monster.CockatriceEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CockatriceLayer<T extends CockatriceEntity, M extends EntityModel<T>> extends AbstractEyesLayer<T, M> {
    private static final RenderType RENDER_TYPE = RenderType.getEyes(new ResourceLocation(Aether.MODID, "textures/entity/cockatrice/cockatrice_emissive.png"));

    public CockatriceLayer(IEntityRenderer<T, M> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public RenderType getRenderType() {
        return RENDER_TYPE;
    }

}
