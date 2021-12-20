package com.gildedgames.aether.common.item.accessories;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

import net.minecraft.world.item.Item.Properties;

public class AccessoryItem extends Item implements ICurioItem, Vanishable
{
    public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
        protected ItemStack execute(BlockSource p_82487_1_, ItemStack p_82487_2_) {
            return AccessoryItem.dispenseAccessory(p_82487_1_, p_82487_2_) ? p_82487_2_ : super.execute(p_82487_1_, p_82487_2_);
        }
    };

    public static boolean dispenseAccessory(BlockSource blockSource, ItemStack stack) {
        BlockPos blockpos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
        List<LivingEntity> list = blockSource.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(blockpos), EntitySelector.NO_SPECTATORS.and(new EntitySelector.MobCanWearArmorEntitySelector(stack)));
        if (list.isEmpty()) {
            return false;
        } else {
            LivingEntity livingentity = list.get(0);
            ItemStack itemStack = stack.split(1);
            ICuriosHelper curiosHelper = CuriosApi.getCuriosHelper();
            curiosHelper.getCurio(itemStack).ifPresent(curio -> curiosHelper.getCuriosHandler(livingentity).ifPresent(handler -> {
                Map<String, ICurioStacksHandler> curios = handler.getCurios();
                for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
                    IDynamicStackHandler stackHandler = entry.getValue().getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); i++) {
                        String id = entry.getKey();
                        SlotContext slotContext = new SlotContext(id, livingentity, i, true, true);
                        if (curiosHelper.isStackValid(slotContext, itemStack) && curio.canEquip(id, livingentity) && curio.canEquipFromUse(slotContext)) {
                            ItemStack present = stackHandler.getStackInSlot(i);
                            if (present.isEmpty()) {
                                stackHandler.setStackInSlot(i, itemStack.copy());
                                int count = itemStack.getCount();
                                itemStack.shrink(count);
                            }
                        }
                    }
                }
            }));
            return true;
        }
    }

    public AccessoryItem(Properties properties) {
        super(properties);
        DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_GENERIC.get(), 1.0f, 1.0f);
    }

    @Override //TODO: Fix this for 1.18
    public void render(String identifier, int index, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        EntityRenderer<?> entityRenderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(livingEntity);
        if (entityRenderer instanceof RenderLayerParent<?, ?>) {
            EntityModel<?> model = ((RenderLayerParent<?, ?>) entityRenderer).getModel();
            if (model instanceof HumanoidModel<?>) {
                HumanoidModel<?> bipedModel = (HumanoidModel<?>) model;
                this.renderModel(bipedModel, identifier, index, matrixStack, renderTypeBuffer, light, livingEntity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, stack);
            }
        }
    }

    public void renderModel(HumanoidModel<?> model, String identifier, int index, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) { }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment == Enchantments.BINDING_CURSE;
    }
}