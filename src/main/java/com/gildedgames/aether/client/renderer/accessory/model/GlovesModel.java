package com.gildedgames.aether.client.renderer.accessory.model;

import com.gildedgames.aether.common.item.accessories.gloves.GlovesItem;
import com.gildedgames.aether.common.item.accessories.gloves.LeatherGlovesItem;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class GlovesModel extends HumanoidModel<LivingEntity>
{
    public GlovesModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createLayer(boolean isSlim) {
        CubeDeformation cube = new CubeDeformation(0.6F);
        MeshDefinition meshDefinition = HumanoidModel.createMesh(cube, 0.0F);
        PartDefinition partDefinition = meshDefinition.getRoot();
        if (!isSlim) {
            partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cube), PartPose.offset(-5.0F, 2.0F, 0.0F));
            partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cube), PartPose.offset(5.0F, 2.0F, 0.0F));
        } else {
            partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, cube), PartPose.offset(-5.0F, 2.0F, 0.0F));
            partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, cube), PartPose.offset(5.0F, 2.0F, 0.0F));
        }
        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    @Nonnull
    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

    @Nonnull
    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.rightArm, this.leftArm);
    }

    public static class WornGlovesModel extends PlayerModel<LivingEntity>
    {
        public WornGlovesModel(ModelPart root, boolean isSlim) {
            super(root, isSlim);
        }

        public static LayerDefinition createLayer(boolean isSlim) {
            CubeDeformation cube = new CubeDeformation(0.25F);
            MeshDefinition meshDefinition = PlayerModel.createMesh(cube, 0.0F);
            PartDefinition partDefinition = meshDefinition.getRoot();
            if (!isSlim) {
                partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
                partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
                partDefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cube), PartPose.offset(-5.0F, 2.0F, 0.0F));
                partDefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cube), PartPose.offset(5.0F, 2.0F, 0.0F));
            } else {
                partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
                partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
                partDefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, cube), PartPose.offset(-5.0F, 2.0F, 0.0F));
                partDefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, cube), PartPose.offset(5.0F, 2.0F, 0.0F));
            }
            return LayerDefinition.create(meshDefinition, 16, 16);
        }

        @Nonnull
        @Override
        protected Iterable<ModelPart> headParts() {
            return ImmutableList.of();
        }

        @Nonnull
        @Override
        protected Iterable<ModelPart> bodyParts() {
            return ImmutableList.of(this.rightArm, this.leftArm, this.leftSleeve, this.rightSleeve);
        }

        public void renderGlove(ItemStack stack, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, AbstractClientPlayer player, ModelPart rendererArm, ModelPart rendererSleeve) {
            this.setAllVisible(false);
            this.attackTime = 0.0F;
            this.crouching = false;
            this.swimAmount = 0.0F;
            this.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

            GlovesItem glovesItem = (GlovesItem) stack.getItem();
            VertexConsumer vertexBuilder = ItemRenderer.getArmorFoilBuffer(bufferSource, RenderType.armorCutoutNoCull(this.slim ? glovesItem.getGlovesSlimTexture() : glovesItem.getGlovesTexture()), false, stack.isEnchanted());

            float red = 1.0F;
            float green = 1.0F;
            float blue = 1.0F;

            if (glovesItem instanceof LeatherGlovesItem leatherGlovesItem) {
                int i = leatherGlovesItem.getColor(stack);
                red = (float) (i >> 16 & 255) / 255.0F;
                green = (float) (i >> 8 & 255) / 255.0F;
                blue = (float) (i & 255) / 255.0F;
            }

            //TODO: toggle.
            boolean isSleeve = false;
            if (!isSleeve) {
                rendererArm.visible = true;
                rendererArm.xRot = 0.0F;
                rendererArm.render(poseStack, vertexBuilder, combinedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
            } else {
                rendererSleeve.visible = true;
                rendererSleeve.xRot = 0.0F;
                rendererSleeve.render(poseStack, vertexBuilder, combinedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
            }
        }
    }
}
