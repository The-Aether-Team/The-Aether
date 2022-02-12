package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.model.SunSpiritModel;

import com.gildedgames.aether.common.entity.monster.dungeon.FireMinion;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.world.entity.Mob;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class FireMinionRenderer extends MobRenderer<FireMinion, SunSpiritModel<FireMinion>>
{
    private static final ResourceLocation SUN_SPIRIT_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sun_spirit/sun_spirit.png");
    private static final ResourceLocation FROZEN_SPIRIT_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sun_spirit/frozen_sun_spirit.png");
    
    public FireMinionRenderer(EntityRendererProvider.Context context) {
        super(context, new SunSpiritModel<>(context.bakeLayer(AetherModelLayers.SUN_SPIRIT)), 0.4F);
        this.shadowRadius = 0.8F;
    }
    
    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(FireMinion entity) {
        if (entity.hasCustomName()) {
            String name = entity.getName().getContents();
            if (name.equals("JorgeQ") || name.equals("Jorge_SunSpirit")) {
                return FROZEN_SPIRIT_TEXTURE;
            }
        }
        return SUN_SPIRIT_TEXTURE;
    }
}
