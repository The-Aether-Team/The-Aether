package com.gildedgames.aether.common.entity.monster;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class ValkyrieEntity extends MonsterEntity
{
    private int chatTime;

    public ValkyrieEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

//    public ValkyrieEntity(World worldIn) {
//        this(AetherEntityTypes.VALKYRIE.get(), worldIn);
//    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 0.5));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 0.65, true));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F, 8.0F));
    }

    public static AttributeModifierMap.MutableAttribute createMobAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 8.0)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 10.0)
                .add(Attributes.MAX_HEALTH, 50.0);
    }

    @SuppressWarnings("resource")
    private void chatItUp(PlayerEntity player, ITextComponent message) {
        if (chatTime <= 0) {
            DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> {
                Minecraft.getInstance().gui.getChat().addMessage(message);
            });
            chatTime = 60;
        }
    }

    @Override
    protected ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack item = player.getItemInHand(hand);
        if (this.getTarget() == null) {
            this.lookAt(player, 180.0F, 180.0F);
            String translationId;
            if (item.getItem() == AetherItems.VICTORY_MEDAL.get()) {
                if (item.getCount() >= 10) {
                    translationId = "gui.valkyrie.dialog.medal.1";
                }
                else if (item.getCount() >= 5) {
                    translationId = "gui.valkyrie.dialog.medal.2";
                }
                else {
                    translationId = "gui.valkyrie.dialog.medal.3";
                }
            }
            else {
                // switch (random.nextInt(3)) {
                //     case 0:
                //         translationId = "gui.valkyrie.dialog.1";
                //         break;
                //     case 1:
                //         translationId = "gui.valkyrie.dialog.2";
                //         break;
                //     case 2:
                //         translationId = "gui.valkyrie.dialog.3";
                //         break;
                // }
                translationId = "gui.valkyrie.dialog." + (char) (random.nextInt(3) + '1');
            }
            chatItUp(player, new TranslationTextComponent(translationId));
            return super.mobInteract(player, hand);
        }
        return ActionResultType.FAIL;
    }

    @Override
    protected void actuallyHurt(DamageSource ds, float f) {
        if (ds.getEntity() instanceof PlayerEntity && level.getDifficulty() != Difficulty.PEACEFUL) {
            PlayerEntity player = (PlayerEntity) ds.getEntity();
            if (this.getTarget() == null) {
                chatTime = 0;
                String translationId = "gui.valkyrie.dialog." + (char) (random.nextInt(3) + '1');
                chatItUp(player, new TranslationTextComponent(translationId));
                this.setTarget(player);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        
        // TODO: valkyrie logic needs to be reimplemented from scratch
        // because all of the old code is useless and irreparably flawed
    }

    
}
