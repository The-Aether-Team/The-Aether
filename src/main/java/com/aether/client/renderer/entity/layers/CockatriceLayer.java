package com.aether.client.renderer.entity.layers;

import com.aether.Aether;
import com.aether.entity.monster.CockatriceEntity;
import com.aether.client.renderer.entity.model.CockatriceModel;

import com.aether.entity.monster.SentryEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.SlimeModel;
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
