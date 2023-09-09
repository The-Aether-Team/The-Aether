package com.aetherteam.aether.client.renderer.entity.layers;

import com.aetherteam.aether.AetherTags;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.client.render.CuriosLayer;

/**
 * [CODE COPY] - {@link CuriosLayer}.
 */
public class EntityAccessoryLayer extends CuriosLayer<LivingEntity, EntityModel<LivingEntity>> {
    private final RenderLayerParent<LivingEntity, EntityModel<LivingEntity>> renderLayerParent;

    public EntityAccessoryLayer(RenderLayerParent<LivingEntity, EntityModel<LivingEntity>> renderer) {
        super(renderer);
        this.renderLayerParent = renderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        poseStack.pushPose();
        CuriosApi.getCuriosInventory(livingEntity).ifPresent(handler -> handler.getCurios().forEach((id, stacksHandler) -> {
            IDynamicStackHandler stackHandler = stacksHandler.getStacks();
            IDynamicStackHandler cosmeticStacksHandler = stacksHandler.getCosmeticStacks();

            for (int i = 0; i < stackHandler.getSlots(); i++) {
                ItemStack stack = cosmeticStacksHandler.getStackInSlot(i);
                boolean cosmetic = true;
                NonNullList<Boolean> renderStates = stacksHandler.getRenders();
                boolean renderable = renderStates.size() > i && renderStates.get(i);

                if (stack.isEmpty() && renderable) {
                    stack = stackHandler.getStackInSlot(i);
                    cosmetic = false;
                }

                if (!stack.isEmpty() && stack.is(AetherTags.Items.ACCESSORIES)) { // Check if the Curio is an Aether accessory using the tag.
                    SlotContext slotContext = new SlotContext(id, livingEntity, i, cosmetic, renderable);
                    ItemStack finalStack = stack;
                    CuriosRendererRegistry.getRenderer(stack.getItem()).ifPresent(renderer -> renderer.render(finalStack, slotContext, poseStack, this.renderLayerParent, buffer, light, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch));
                }
            }
        }));
        poseStack.popPose();
    }
}
