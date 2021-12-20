package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.entity.model.SunSpiritModel;
import com.gildedgames.aether.common.entity.monster.FireMinionEntity;

import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Mob;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.ChatFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FireMinionRenderer extends HumanoidMobRenderer<Mob, SunSpiritModel>
{
    private static final ResourceLocation SPIRIT = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sun_spirit/sun_spirit.png");
    private static final ResourceLocation FROZEN_SPIRIT = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sun_spirit/frozen_sun_spirit.png");
    
    public FireMinionRenderer(EntityRenderDispatcher rendererManager) {
        super(rendererManager, new SunSpiritModel(0.0F, 0.0F), 0.4F);
        this.shadowRadius = 0.8F;
    }
    
    @Override
    public ResourceLocation getTextureLocation(Mob entity) {        
        if (entity.hasCustomName()) {
            String name = ChatFormatting.stripFormatting(entity.getName().getString());
            if ("JorgeQ".equals(name) || "Jorge_SunSpirit".equals(name)) {
                return FROZEN_SPIRIT;
            }
        }
        return SPIRIT;
    }
}
