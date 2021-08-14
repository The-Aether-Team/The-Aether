package com.gildedgames.aether.common.item.accessories;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
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

public class AccessoryItem extends Item implements ICurioItem, IVanishable
{
    public static final IDispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
        protected ItemStack execute(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            return AccessoryItem.dispenseAccessory(p_82487_1_, p_82487_2_) ? p_82487_2_ : super.execute(p_82487_1_, p_82487_2_);
        }
    };

    public static boolean dispenseAccessory(IBlockSource blockSource, ItemStack stack) {
        BlockPos blockpos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
        List<LivingEntity> list = blockSource.getLevel().getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(blockpos), EntityPredicates.NO_SPECTATORS.and(new EntityPredicates.ArmoredMob(stack)));
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
                        SlotContext slotContext = new SlotContext(id, livingentity, i);
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

    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        EntityRenderer<?> entityRenderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(livingEntity);
        if (entityRenderer instanceof IEntityRenderer<?, ?>) {
            EntityModel<?> model = ((IEntityRenderer<?, ?>) entityRenderer).getModel();
            if (model instanceof BipedModel<?>) {
                BipedModel<?> bipedModel = (BipedModel<?>) model;
                this.renderModel(bipedModel, identifier, index, matrixStack, renderTypeBuffer, light, livingEntity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, stack);
            }
        }
    }

    public void renderModel(BipedModel<?> model, String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) { }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment == Enchantments.BINDING_CURSE;
    }
}