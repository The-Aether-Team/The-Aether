package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.entity.model.SunSpiritModel;
import com.gildedgames.aether.common.entity.monster.FireMinionEntity;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FireMinionRenderer extends BipedRenderer<MobEntity, SunSpiritModel>
{
    private static final ResourceLocation SPIRIT = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sun_spirit/sun_spirit.png");
    private static final ResourceLocation FROZEN_SPIRIT = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sun_spirit/frozen_sun_spirit.png");
    
    public FireMinionRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new SunSpiritModel(0.0F, 0.0F), 0.4F);
        this.shadowRadius = 0.8F;
    }
    
    @Override
    public ResourceLocation getTextureLocation(MobEntity entity) {        
        if (entity.hasCustomName()) {
            String name = TextFormatting.stripFormatting(entity.getName().getString());
            if ("JorgeQ".equals(name) || "Jorge_SunSpirit".equals(name)) {
                return FROZEN_SPIRIT;
            }
        }
        return SPIRIT;
    }
}
