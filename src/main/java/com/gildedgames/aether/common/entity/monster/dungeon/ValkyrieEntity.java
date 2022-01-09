package com.gildedgames.aether.common.entity.monster.dungeon;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class ValkyrieEntity extends Monster
{
    private int chatTime;

    public ValkyrieEntity(EntityType<? extends Monster> type, Level worldIn) {
        super(type, worldIn);
    }

//    public ValkyrieEntity(World worldIn) {
//        this(AetherEntityTypes.VALKYRIE.get(), worldIn);
//    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.5));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 0.65, true));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F, 8.0F));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 8.0)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 10.0)
                .add(Attributes.MAX_HEALTH, 50.0);
    }

    @SuppressWarnings("resource")
    private void chatItUp(Player player, Component message) {
        if (chatTime <= 0) {
            DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> {
                Minecraft.getInstance().gui.getChat().addMessage(message);
            });
            chatTime = 60;
        }
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
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
            chatItUp(player, new TranslatableComponent(translationId));
            return super.mobInteract(player, hand);
        }
        return InteractionResult.FAIL;
    }

    @Override
    protected void actuallyHurt(DamageSource ds, float f) {
        if (ds.getEntity() instanceof Player && level.getDifficulty() != Difficulty.PEACEFUL) {
            Player player = (Player) ds.getEntity();
            if (this.getTarget() == null) {
                chatTime = 0;
                String translationId = "gui.valkyrie.dialog." + (char) (random.nextInt(3) + '1');
                chatItUp(player, new TranslatableComponent(translationId));
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
