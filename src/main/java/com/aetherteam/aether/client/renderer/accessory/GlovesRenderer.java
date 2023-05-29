package com.aetherteam.aether.client.renderer.accessory;

import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.accessory.model.GlovesModel;
import com.aetherteam.aether.item.accessories.gloves.GlovesItem;
import com.aetherteam.aether.mixin.mixins.client.accessor.PlayerModelAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class GlovesRenderer implements ICurioRenderer {
    private final GlovesModel glovesModel;
    private final GlovesModel glovesModelSlim;
    private final GlovesModel glovesFirstPerson;

    public GlovesRenderer() {
        this.glovesModel = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.GLOVES));
        this.glovesModelSlim = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.GLOVES_SLIM));
        this.glovesFirstPerson = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.GLOVES_FIRST_PERSON));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource buffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        GlovesItem glovesItem = (GlovesItem) stack.getItem();
        GlovesModel model = this.glovesModel;
        ResourceLocation texture = glovesItem.getGlovesTexture();

        if (renderLayerParent.getModel() instanceof PlayerModel<?> playerModel) {
            PlayerModelAccessor playerModelAccessor = (PlayerModelAccessor) playerModel;
            model = playerModelAccessor.aether$getSlim() ? this.glovesModelSlim : this.glovesModel;
        }

        ICurioRenderer.followBodyRotations(slotContext.entity(), model);

        float red = glovesItem.getColors(stack).getLeft();
        float green = glovesItem.getColors(stack).getMiddle();
        float blue = glovesItem.getColors(stack).getRight();

        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(texture), false, stack.isEnchanted());;
        model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
    }

    public void renderFirstPerson(ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, AbstractClientPlayer player, HumanoidArm arm) {
        GlovesModel model = this.glovesFirstPerson;

        model.setAllVisible(false);
        model.attackTime = 0.0F;
        model.crouching = false;
        model.swimAmount = 0.0F;
        model.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

        GlovesItem glovesItem = (GlovesItem) stack.getItem();
        VertexConsumer consumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(glovesItem.getGlovesTexture()), false, stack.isEnchanted());

        float red = glovesItem.getColors(stack).getLeft();
        float green = glovesItem.getColors(stack).getMiddle();
        float blue = glovesItem.getColors(stack).getRight();

        if (arm == HumanoidArm.RIGHT) {
            this.renderGlove(model.rightArm, poseStack, combinedLight, consumer, red, green, blue);
        } else if (arm == HumanoidArm.LEFT) {
            this.renderGlove(model.leftArm, poseStack, combinedLight, consumer, red, green, blue);
        }
    }

    private void renderGlove(ModelPart gloveArm, PoseStack poseStack, int combinedLight, VertexConsumer consumer, float red, float green, float blue) {
        gloveArm.visible = true;
        gloveArm.xRot = 0.0F;
        gloveArm.render(poseStack, consumer, combinedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
    }
}
