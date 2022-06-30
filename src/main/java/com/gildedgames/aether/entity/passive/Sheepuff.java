package com.gildedgames.aether.entity.passive;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.gildedgames.aether.client.AetherSoundEvents;
import com.gildedgames.aether.entity.ai.EatAetherGrassGoal;
import com.gildedgames.aether.entity.ai.FallingRandomStrollGoal;
import com.gildedgames.aether.entity.ai.controller.FallingMovementController;
import com.gildedgames.aether.entity.ai.navigator.FallPathNavigation;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.AetherTags;
import com.google.common.collect.Maps;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.IForgeShearable;

import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.level.ItemLike;

public class Sheepuff extends AetherAnimal implements IForgeShearable {
    private static final EntityDataAccessor<Byte> DATA_WOOL_COLOR_ID = SynchedEntityData.defineId(Sheepuff.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> DATA_PUFFED_ID = SynchedEntityData.defineId(Sheepuff.class, EntityDataSerializers.BOOLEAN);

    private static final Map<DyeColor, ItemLike> ITEM_BY_DYE = Util.make(Maps.newEnumMap(DyeColor.class), (p_203402_0_) -> {
        p_203402_0_.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
        p_203402_0_.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
        p_203402_0_.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
        p_203402_0_.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
        p_203402_0_.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
        p_203402_0_.put(DyeColor.LIME, Blocks.LIME_WOOL);
        p_203402_0_.put(DyeColor.PINK, Blocks.PINK_WOOL);
        p_203402_0_.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
        p_203402_0_.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
        p_203402_0_.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
        p_203402_0_.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
        p_203402_0_.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
        p_203402_0_.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
        p_203402_0_.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
        p_203402_0_.put(DyeColor.RED, Blocks.RED_WOOL);
        p_203402_0_.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
    });
    private static final Map<DyeColor, float[]> COLOR_ARRAY_BY_COLOR = Maps.newEnumMap(Arrays.stream(DyeColor.values()).collect(Collectors.toMap((DyeColor p_200204_0_) -> p_200204_0_, Sheepuff::createSheepColor)));

    private int eatAnimationTick, amountEaten;
    private EatAetherGrassGoal eatBlockGoal;

    protected final FallPathNavigation fallNavigation;
    protected final GroundPathNavigation groundNavigation;

    private static float[] createSheepColor(DyeColor p_192020_0_) {
        if (p_192020_0_ == DyeColor.WHITE) {
            return new float[]{0.9019608F, 0.9019608F, 0.9019608F};
        } else {
            float[] afloat = p_192020_0_.getTextureDiffuseColors();
            return new float[]{afloat[0] * 0.75F, afloat[1] * 0.75F, afloat[2] * 0.75F};
        }
    }

    public static float[] getColorArray(DyeColor p_175513_0_) {
        return COLOR_ARRAY_BY_COLOR.get(p_175513_0_);
    }

    public Sheepuff(EntityType<? extends Sheepuff> type, Level level) {
        super(type, level);
        this.moveControl = new FallingMovementController(this);
        this.fallNavigation = new FallPathNavigation(this, level);
        this.groundNavigation = new GroundPathNavigation(this, level);
    }

    @Override
    protected void registerGoals() {
        this.eatBlockGoal = new EatAetherGrassGoal(this);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, Ingredient.of(AetherTags.Items.SHEEPUFF_TEMPTATION_ITEMS), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, this.eatBlockGoal);
        this.goalSelector.addGoal(6, new FallingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Nonnull
    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0)
                .add(Attributes.MOVEMENT_SPEED, 0.23);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_WOOL_COLOR_ID, (byte) 0);
        this.entityData.define(DATA_PUFFED_ID, false);
    }

    @Override
    public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor level, @Nonnull DifficultyInstance difficulty, @Nonnull MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
        this.setColor(getRandomSheepuffColor(level.getRandom()));
        return super.finalizeSpawn(level, difficulty, reason, spawnData, tag);
    }

    @Override
    protected void customServerAiStep() {
        this.eatAnimationTick = this.eatBlockGoal.getEatAnimationTick();
        super.customServerAiStep();
    }

    @Override
    public void aiStep() {
        if (this.level.isClientSide) {
            this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
        }
        super.aiStep();
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 10) {
            this.eatAnimationTick = 40;
        } else {
            super.handleEntityEvent(id);
        }
    }

    public float getHeadEatPositionScale(float pos) {
        if (this.eatAnimationTick <= 0) {
            return 0.0F;
        } else if (this.eatAnimationTick >= 4 && this.eatAnimationTick <= 36) {
            return 1.0F;
        } else {
            return this.eatAnimationTick < 4 ? (this.eatAnimationTick - pos) / 4.0F : -(this.eatAnimationTick - 40 - pos) / 4.0F;
        }
    }

    public float getHeadEatAngleScale(float angle) {
        if (this.eatAnimationTick > 4 && this.eatAnimationTick <= 36) {
            float f = ((float) (this.eatAnimationTick - 4) - angle) / 32.0F;
            return ((float) Math.PI / 5.0F) + 0.21991149F * Mth.sin(f * 28.7F);
        } else {
            return this.eatAnimationTick > 0 ? ((float) Math.PI / 5.0F) : this.getXRot() * ((float) Math.PI / 180.0F);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getPuffed()) {
            this.resetFallDistance();
            AttributeInstance gravity = this.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
            if (gravity != null) {
                double fallSpeed = Math.max(gravity.getValue() * -0.625, -0.05);
                if (this.getDeltaMovement().y < fallSpeed) {
                    this.setDeltaMovement(this.getDeltaMovement().x, fallSpeed, this.getDeltaMovement().z);
                }
            }
            this.navigation = this.fallNavigation;
        } else {
            this.navigation = this.groundNavigation;
        }
    }

    @Override
    protected void jumpFromGround() {
        super.jumpFromGround();
        if (this.getPuffed()) {
            this.push(0.0, 1.8, 0.0);
        }
    }

    @Override
    public void ate() {
        ++this.amountEaten;
        if (!this.isSheared()) {
            if (this.amountEaten >= 2) {
                this.setPuffed(true);
                this.amountEaten = 0;
            }
        } else {
            if (this.amountEaten == 1) {
                this.setSheared(false);
                this.setColor(DyeColor.WHITE);
                this.amountEaten = 0;
            }
        }
        if (this.isBaby()) {
            this.ageUp(60);
        }
    }

    @Nonnull
    @Override
    public InteractionResult mobInteract(Player player, @Nonnull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() instanceof DyeItem dyeItem && !this.isSheared()) {
            DyeColor color = dyeItem.getDyeColor();
            if (this.getColor() != color) {
                if (this.getPuffed() && itemstack.getCount() >= 2) {
                    player.swing(hand);
                    if (!player.level.isClientSide) {
                        this.setColor(color);
                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(2);
                        }
                    }
                } else if (!this.getPuffed()) {
                    player.swing(hand);
                    if (!player.level.isClientSide) {
                        this.setColor(color);
                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                    }
                }

            }
        }
        return super.mobInteract(player, hand);
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nullable Player player, @Nonnull ItemStack item, Level level, BlockPos pos, int fortune) {
        level.playSound(null, this, AetherSoundEvents.ENTITY_SHEEPUFF_SHEAR.get(), player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
        if (!level.isClientSide) {
            this.amountEaten = 0;
            this.setSheared(true);
            this.setPuffed(false);
            int i = 1 + this.random.nextInt(3);
            List<ItemStack> items = new java.util.ArrayList<>();
            for (int j = 0; j < i; ++j) {
                items.add(new ItemStack(ITEM_BY_DYE.get(this.getColor())));
            }
            return items;
        }
        return Collections.emptyList();
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, Level world, BlockPos pos) {
        return this.isAlive() && !this.isSheared() && !this.isBaby();
    }

    public boolean isSheared() {
        return (this.entityData.get(DATA_WOOL_COLOR_ID) & 16) != 0;
    }

    public void setSheared(boolean sheared) {
        byte b0 = this.entityData.get(DATA_WOOL_COLOR_ID);
        if (sheared) {
            this.entityData.set(DATA_WOOL_COLOR_ID, (byte) (b0 | 16));
        } else {
            this.entityData.set(DATA_WOOL_COLOR_ID, (byte) (b0 & -17));
        }
    }

    public DyeColor getColor() {
        return DyeColor.byId(this.entityData.get(DATA_WOOL_COLOR_ID) & 15);
    }

    public void setColor(DyeColor dyeColor) {
        byte b0 = this.entityData.get(DATA_WOOL_COLOR_ID);
        this.entityData.set(DATA_WOOL_COLOR_ID, (byte) (b0 & 240 | dyeColor.getId() & 15));
    }

    public static DyeColor getRandomSheepuffColor(RandomSource random) {
        int i = random.nextInt(100);
        if (i < 5) {
            return DyeColor.LIGHT_BLUE;
        } else if (i < 10) {
            return DyeColor.CYAN;
        } else if (i < 15) {
            return DyeColor.LIME;
        } else if (i < 18) {
            return DyeColor.PINK;
        } else {
            return random.nextInt(500) == 0 ? DyeColor.PURPLE : DyeColor.WHITE;
        }
    }

    public boolean getPuffed() {
        return this.entityData.get(DATA_PUFFED_ID);
    }

    public void setPuffed(boolean flag) {
        this.entityData.set(DATA_PUFFED_ID, flag);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(AetherTags.Items.SHEEPUFF_TEMPTATION_ITEMS);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherSoundEvents.ENTITY_SHEEPUFF_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
        return AetherSoundEvents.ENTITY_SHEEPUFF_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_SHEEPUFF_DEATH.get();
    }

    @Override
    protected void playStepSound(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        this.level.playSound(null, this.getX(), this.getY(), this.getZ(), AetherSoundEvents.ENTITY_SHEEPUFF_STEP.get(), SoundSource.NEUTRAL, 0.15F, 1.0F);
    }

    @Override
    protected int calculateFallDamage(float distance, float damageMultiplier) {
        return this.getPuffed() ? 0 : super.calculateFallDamage(distance, damageMultiplier);
    }

    @Override
    public int getMaxFallDistance() {
        return !this.isOnGround() && this.getPuffed() ? 20 : super.getMaxFallDistance();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@Nonnull ServerLevel level, @Nonnull AgeableMob entity) {
        Sheepuff sheepuffParent = (Sheepuff) entity;
        Sheepuff sheepuffBaby = AetherEntityTypes.SHEEPUFF.get().create(level);
        if (sheepuffBaby != null) {
            sheepuffBaby.setColor(this.getOffspringColor(this, sheepuffParent));
        }
        return sheepuffBaby;
    }

    private DyeColor getOffspringColor(Animal parent1, Animal parent2) {
        DyeColor dyeColor1 = ((Sheepuff) parent1).getColor();
        DyeColor dyeColor2 = ((Sheepuff) parent2).getColor();
        CraftingContainer craftingInventory = makeContainer(dyeColor1, dyeColor2);
        return this.level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftingInventory, this.level).map(
                (p_213614_1_) -> p_213614_1_.assemble(craftingInventory)).map(ItemStack::getItem).filter(DyeItem.class::isInstance).map(DyeItem.class::cast).map(DyeItem::getDyeColor).orElseGet(() -> this.level.random.nextBoolean() ? dyeColor1 : dyeColor2);
    }

    private static CraftingContainer makeContainer(DyeColor dyeColor1, DyeColor dyeColor2) {
        CraftingContainer craftingInventory = new CraftingContainer(new SheepuffContainer(null, -1), 2, 1);
        craftingInventory.setItem(0, new ItemStack(DyeItem.byColor(dyeColor1)));
        craftingInventory.setItem(1, new ItemStack(DyeItem.byColor(dyeColor2)));
        return craftingInventory;
    }

    @Override
    protected float getStandingEyeHeight(@Nonnull Pose pose, EntityDimensions size) {
        return 0.95F * size.height;
    }

    @Nonnull
    @Override
    public ResourceLocation getDefaultLootTable() {
        if (this.isSheared()) {
            return this.getType().getDefaultLootTable();
        } else {
            return switch (this.getColor()) {
                case WHITE -> AetherLoot.ENTITIES_SHEEPUFF_WHITE;
                case ORANGE -> AetherLoot.ENTITIES_SHEEPUFF_ORANGE;
                case MAGENTA -> AetherLoot.ENTITIES_SHEEPUFF_MAGENTA;
                case LIGHT_BLUE -> AetherLoot.ENTITIES_SHEEPUFF_LIGHT_BLUE;
                case YELLOW -> AetherLoot.ENTITIES_SHEEPUFF_YELLOW;
                case LIME -> AetherLoot.ENTITIES_SHEEPUFF_LIME;
                case PINK -> AetherLoot.ENTITIES_SHEEPUFF_PINK;
                case GRAY -> AetherLoot.ENTITIES_SHEEPUFF_GRAY;
                case LIGHT_GRAY -> AetherLoot.ENTITIES_SHEEPUFF_LIGHT_GRAY;
                case CYAN -> AetherLoot.ENTITIES_SHEEPUFF_CYAN;
                case PURPLE -> AetherLoot.ENTITIES_SHEEPUFF_PURPLE;
                case BLUE -> AetherLoot.ENTITIES_SHEEPUFF_BLUE;
                case BROWN -> AetherLoot.ENTITIES_SHEEPUFF_BROWN;
                case GREEN -> AetherLoot.ENTITIES_SHEEPUFF_GREEN;
                case RED -> AetherLoot.ENTITIES_SHEEPUFF_RED;
                case BLACK -> AetherLoot.ENTITIES_SHEEPUFF_BLACK;
            };
        }
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Sheared", this.isSheared());
        compound.putBoolean("Puffed", this.getPuffed());
        compound.putByte("Color", (byte) this.getColor().getId());
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSheared(compound.getBoolean("Sheared"));
        this.setPuffed(compound.getBoolean("Puffed"));
        this.setColor(DyeColor.byId(compound.getByte("Color")));
    }

    public static class SheepuffContainer extends AbstractContainerMenu {

        public SheepuffContainer(@Nullable MenuType<?> pMenuType, int pContainerId) {
            super(pMenuType, pContainerId);
        }

        @Override
        @Nonnull
        public ItemStack quickMoveStack(@Nullable Player pPlayer, int pIndex) {
            return ItemStack.EMPTY;
        }

        @Override
        public boolean stillValid(@Nonnull Player player) {
            return false;
        }
    }
}
