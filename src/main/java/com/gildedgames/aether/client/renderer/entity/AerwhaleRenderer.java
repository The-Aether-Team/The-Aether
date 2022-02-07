package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.model.AerwhaleModel;
import com.gildedgames.aether.client.renderer.entity.model.ClassicAerwhaleModel;
import com.gildedgames.aether.common.entity.passive.Aerwhale;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import com.mojang.math.Vector3f;

public class AerwhaleRenderer extends MultiModelRenderer<Aerwhale, EntityModel<Aerwhale>, AerwhaleModel, ClassicAerwhaleModel>
{
    private static final ResourceLocation DEFAULT_AERWHALE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/aerwhale/aerwhale.png");
    private static final ResourceLocation OLD_AERWHALE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/aerwhale/old_aerwhale.png");
    private final AerwhaleModel defaultModel;
    private final ClassicAerwhaleModel oldModel;
    
    public AerwhaleRenderer(EntityRendererProvider.Context renderer) {
        super(renderer, new AerwhaleModel(renderer.bakeLayer(AetherModelLayers.AERWHALE)), 0.5F);
        this.defaultModel = new AerwhaleModel(renderer.bakeLayer(AetherModelLayers.AERWHALE));
        this.oldModel = new ClassicAerwhaleModel(renderer.bakeLayer(AetherModelLayers.AERWHALE_CLASSIC));
    }

    @Override
    protected void scale(Aerwhale aerwhale, PoseStack matrixStackIn, float partialTickTime) {
        matrixStackIn.translate(0, 1.2, 0);
//        if (_staticData == null) {
//        	_staticData = new float[] {aerwhale.rotationYaw, aerwhale.rotationPitch};
//        }
//        float[] prevRotations = (float[]) _staticData;
//        float prevRotationYaw = prevRotations[0];
//        float prevRotationPitch = prevRotations[1];
        
        Vec3 look = aerwhale.getDeltaMovement().normalize();//getLook(partialTickTime);
        
        float yaw = (float)(Mth.atan2(look.z, look.x) * 180.0 / Math.PI);
        float pitch = -(float)(Math.atan(look.y) * 73.0);
//        yaw = MathHelper.lerp(partialTickTime, aerwhale.prevRotationYaw, yaw);
//        float yaw = MathHelper.lerp(partialTickTime, aerwhale.prevRotationYaw, aerwhale.rotationYaw);
//        float pitch = aerwhale.rotationPitch;
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(yaw + 0.0F));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(pitch));
//        matrixStackIn.rotate(new Quaternion(Vector3f.ZP, 90.0F, true));
        matrixStackIn.scale(2.0F, 2.0F, 2.0F);
        
//        if (yaw != prevRotationYaw || pitch != prevRotationPitch) {
////        	System.out.printf("Aerwhale world = %s\n", aerwhale.world);
//        	float motionYaw = MathHelper.wrapDegrees((float)Math.atan2(aerwhale.getMotion().z, aerwhale.getMotion().x) * (180.0F / (float)Math.PI) - 90.0F); 
//        	System.out.printf("Get aerwhale (pitch, yaw) =  %+7.2f, %+7.2f  [(x, z):                                       Δ (%+6.2f, %+6.2f)] %+7.2f\n", pitch, MathHelper.wrapDegrees(yaw), aerwhale.getMotion().x, aerwhale.getMotion().z, motionYaw);
//        	prevRotations[0] = yaw;
//        	prevRotations[1] = pitch;
//        }
    }

    @Override
    public AerwhaleModel getDefaultModel() {
        return this.defaultModel;
    }

    @Override
    public ClassicAerwhaleModel getOldModel() {
        return this.oldModel;
    }

    @Override
    public ResourceLocation getDefaultTexture() {
        return DEFAULT_AERWHALE_TEXTURE;
    }

    @Override
    public ResourceLocation getOldTexture() {
        return OLD_AERWHALE_TEXTURE;
    }
}

