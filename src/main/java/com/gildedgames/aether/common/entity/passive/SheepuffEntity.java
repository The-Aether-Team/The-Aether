package com.gildedgames.aether.common.entity.passive;

import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.AetherAnimalEntity;
import com.gildedgames.aether.common.entity.ai.EatAetherGrassGoal;
import com.gildedgames.aether.common.entity.ai.navigator.FallPathNavigator;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.google.common.collect.Maps;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IShearable;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SheepuffEntity extends AetherAnimalEntity implements IShearable {
    public static final DataParameter<Byte> FLEECE_COLOR = EntityDataManager.defineId(SheepuffEntity.class, DataSerializers.BYTE);
    public static final DataParameter<Boolean> SHEARED = EntityDataManager.<Boolean>defineId(SheepuffEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> PUFFY = EntityDataManager.defineId(SheepuffEntity.class, DataSerializers.BOOLEAN);

    protected final FallPathNavigator fallNavigation;
    protected final GroundPathNavigator groundNavigation;

    private int sheepTimer, amountEaten;
    private EatAetherGrassGoal eatGrassGoal;

    private static final Map<DyeColor, IItemProvider> WOOL_BY_COLOR = Util.make(Maps.newEnumMap(DyeColor.class), (map) -> {
        map.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
        map.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
        map.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
        map.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
        map.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
        map.put(DyeColor.LIME, Blocks.LIME_WOOL);
        map.put(DyeColor.PINK, Blocks.PINK_WOOL);
        map.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
        map.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
        map.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
        map.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
        map.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
        map.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
        map.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
        map.put(DyeColor.RED, Blocks.RED_WOOL);
        map.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
    });

    public SheepuffEntity(EntityType<? extends SheepuffEntity> type, World worldIn) {
        super(type, worldIn);
        this.fallNavigation = new FallPathNavigator(this, worldIn);
        this.groundNavigation = new GroundPathNavigator(this, worldIn);
    }

    public SheepuffEntity(World worldIn) {
        this(AetherEntityTypes.SHEEPUFF.get(), worldIn);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FLEECE_COLOR, (byte) 0);
        this.entityData.define(SHEARED, false);
        this.entityData.define(PUFFY, false);
    }

    @Override
    protected void registerGoals() {
        this.eatGrassGoal = new EatAetherGrassGoal(this);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, Ingredient.of(Items.WHEAT), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, this.eatGrassGoal);
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Override
    protected void customServerAiStep() {
        this.sheepTimer = this.eatGrassGoal.getEatingGrassTimer();
        super.customServerAiStep();
    }

    public static AttributeModifierMap.MutableAttribute createMobAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23000000417232513D);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return AetherEntityTypes.SHEEPUFF.get().create(this.level);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleEntityEvent(byte id) {
        if (id == 10) {
            this.sheepTimer = 40;
        } else {
            super.handleEntityEvent(id);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationPointY(float p_70894_1_) {
        if (this.sheepTimer <= 0) {
            return 0.0F;
        } else if (this.sheepTimer >= 4 && this.sheepTimer <= 36) {
            return 1.0F;
        } else {
            return this.sheepTimer < 4 ? (this.sheepTimer - p_70894_1_) / 4.0F : -(this.sheepTimer - 40 - p_70894_1_) / 4.0F;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationAngleX(float p_70890_1_) {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
            float f = (this.sheepTimer - 4 - p_70890_1_) / 32.0F;
            return ((float)Math.PI / 5.0F) + 0.21991149F * MathHelper.sin(f * 28.7F);
        } else {
            return this.sheepTimer > 0 ? ((float)Math.PI / 5.0F) : this.xRot * ((float)Math.PI / 180.0F);
        }
    }

    /**
     * Handles the functionality for when the player attempts to right click the sheepuff. Dyes are handled here, shearing is done in the setSheared method.
     */
    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() instanceof DyeItem && !this.getSheared())
        {
            DyeColor color = ((DyeItem) itemstack.getItem()).getDyeColor();

            if (this.getFleeceColor() != color)
            {
                if (this.getPuffed() && itemstack.getCount() >= 2)
                {
                    this.setFleeceColor(color);
                    itemstack.shrink(2);
                }
                else if (!this.getPuffed())
                {
                    this.setFleeceColor(color);
                    itemstack.shrink(1);
                }
            }
        }

        return super.mobInteract(player, hand);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AetherSoundEvents.ENTITY_SHEEPUFF_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return AetherSoundEvents.ENTITY_SHEEPUFF_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_SHEEPUFF_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState par4)
    {
        this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.SHEEP_STEP, SoundCategory.NEUTRAL, 0.15F, 1.0F);
    }

    @Override
    public void ate()
    {
        ++this.amountEaten;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Sheared", this.getSheared());
        compound.putBoolean("Puffed", this.getPuffed());
        compound.putByte("Color", (byte)this.getFleeceColor().getId());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setSheared(compound.getBoolean("Sheared"));
        this.setPuffed(compound.getBoolean("Puffed"));
        this.setFleeceColor(DyeColor.byId(compound.getByte("Color")));
    }

    public DyeColor getFleeceColor() {
        return DyeColor.byId(this.entityData.get(FLEECE_COLOR) & 15);
    }


    public void setFleeceColor(DyeColor color) {
        byte b0 = this.entityData.get(FLEECE_COLOR);
        this.entityData.set(FLEECE_COLOR, (byte)(b0 & 240 | color.getId() & 15));
    }

    public boolean getSheared() {
        return this.entityData.get(SHEARED);
    }

    public void setSheared(boolean flag) {
        this.entityData.set(SHEARED, flag);
    }

    @Override
    protected void jumpFromGround()
    {
        if (this.getPuffed()) {
            this.setDeltaMovement(getDeltaMovement().x, 1.8, getDeltaMovement().z);
        }
        else {
            this.setDeltaMovement(getDeltaMovement().x, 0.41999998688697815, getDeltaMovement().z);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getPuffed()) {
            this.fallDistance = 0;

            if (this.getDeltaMovement().y < -0.05) {
                this.setDeltaMovement(getDeltaMovement().x, -0.05, getDeltaMovement().z);
            }
            this.navigation = fallNavigation;
        } else {
            this.navigation = groundNavigation;
        }

        if (this.amountEaten >= 2 && !this.getSheared() && !this.getPuffed()) {
            this.setPuffed(true);
            this.amountEaten = 0;
        }

        if (this.amountEaten == 1 && this.getSheared() && !this.getPuffed()) {
            this.setSheared(false);
            this.setFleeceColor(DyeColor.WHITE);
            this.amountEaten = 0;
        }
    }

    @Override
    public void travel(Vector3d vector3d1) {
        float f = this.flyingSpeed;
        if (this.isEffectiveAi() && this.getPuffed()) {
            this.flyingSpeed = this.getSpeed() * (0.24F / (0.91F * 0.91F * 0.91F));
            super.travel(vector3d1);
            this.flyingSpeed = f;
        } else {
            this.flyingSpeed = f;
            super.travel(vector3d1);
        }
    }

    @Override
    public int getMaxFallDistance() {
        return !this.isOnGround() && this.getPuffed() ? 20 : super.getMaxFallDistance();
    }

    @Override
    public void aiStep() {
        if (this.level.isClientSide) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }
        super.aiStep();
    }


    public boolean getPuffed() {
        return this.entityData.get(PUFFY);
    }

    public void setPuffed(boolean flag) {
        this.entityData.set(PUFFY, flag);
    }

    /**
     * Chooses a "vanilla" sheep color based on the provided random.
     */
    public static DyeColor getRandomFleeceColor(Random random) {
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
            return random.nextInt(500) == 0 ? DyeColor.WHITE : DyeColor.PURPLE;
        }
    }

    @Override
    public void shear(SoundCategory category) {
        this.level.playSound(null, this, SoundEvents.SHEEP_SHEAR, category, 1.0F, 1.0F);
        this.setSheared(true);
        int i = 1 + this.random.nextInt(3);

        for(int j = 0; j < i; ++j) {
            ItemEntity itementity = this.spawnAtLocation(WOOL_BY_COLOR.get(this.getFleeceColor()), 1);
            if (itementity != null) {
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, (this.random.nextFloat() * 0.05F), ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F)));
            }
        }

    }

    @Override
    public boolean readyForShearing() {
        return this.isAlive() && !this.getSheared() && !this.isBaby();
    }

    /*@Override
    public ResourceLocation getLootTable() {
        if (this.getSheared()) {
            return this.getType().getLootTable();
        } else {
            switch(this.getFleeceColor()) {
                case WHITE:
                default:
                    return LootTables.ENTITIES_SHEEP_WHITE;
                case ORANGE:
                    return LootTables.ENTITIES_SHEEP_ORANGE;
                case MAGENTA:
                    return LootTables.ENTITIES_SHEEP_MAGENTA;
                case LIGHT_BLUE:
                    return LootTables.ENTITIES_SHEEP_LIGHT_BLUE;
                case YELLOW:
                    return LootTables.ENTITIES_SHEEP_YELLOW;
                case LIME:
                    return LootTables.ENTITIES_SHEEP_LIME;
                case PINK:
                    return LootTables.ENTITIES_SHEEP_PINK;
                case GRAY:
                    return LootTables.ENTITIES_SHEEP_GRAY;
                case LIGHT_GRAY:
                    return LootTables.ENTITIES_SHEEP_LIGHT_GRAY;
                case CYAN:
                    return LootTables.ENTITIES_SHEEP_CYAN;
                case PURPLE:
                    return LootTables.ENTITIES_SHEEP_PURPLE;
                case BLUE:
                    return LootTables.ENTITIES_SHEEP_BLUE;
                case BROWN:
                    return LootTables.ENTITIES_SHEEP_BROWN;
                case GREEN:
                    return LootTables.ENTITIES_SHEEP_GREEN;
                case RED:
                    return LootTables.ENTITIES_SHEEP_RED;
                case BLACK:
                    return LootTables.ENTITIES_SHEEP_BLACK;
            }
        }
    }*/
}
