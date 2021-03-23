package com.gildedgames.aether.common.block.dungeon;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.gildedgames.aether.core.api.registers.DungeonType;
import com.gildedgames.aether.common.item.misc.DungeonKeyItem;
import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import com.gildedgames.aether.common.entity.tile.TreasureChestTileEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TreasureChestBlock extends ChestBlock implements IWaterLoggable {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected TreasureChestBlock(AbstractBlock.Properties properties, Supplier<TileEntityType<? extends TreasureChestTileEntity>> tileEntityTypeIn) {
		super(properties, (Supplier) tileEntityTypeIn);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
	}
	
	public TreasureChestBlock(AbstractBlock.Properties properties) {
		this(properties, AetherTileEntityTypes.TREASURE_CHEST::get);
	}

//	@OnlyIn(Dist.CLIENT)
//	public TileEntityMerger.ICallbackWrapper<? extends ChestTileEntity> combine(BlockState p_225536_1_, World p_225536_2_, BlockPos p_225536_3_, boolean p_225536_4_) {
//
//
//		return TileEntityMerger.ICallback::acceptNone;
//	}

	public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
		ChestType chesttype = ChestType.SINGLE;
		Direction direction = p_196258_1_.getHorizontalDirection().getOpposite();
		FluidState fluidstate = p_196258_1_.getLevel().getFluidState(p_196258_1_.getClickedPos());

		return this.defaultBlockState().setValue(FACING, direction).setValue(TYPE, chesttype).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isClientSide) {
			ItemStack itemStack = player.getItemInHand(handIn);
			Item item = itemStack.getItem();
			CompoundNBT nbt = itemStack.getOrCreateTag();

			if (item instanceof DungeonKeyItem && !nbt.contains("Lock")) {
				TileEntity tile = worldIn.getBlockEntity(pos);

				if (tile instanceof TreasureChestTileEntity) {
					String key = ((TreasureChestTileEntity) tile).createKey(((DungeonKeyItem) item).getDungeonType());

					if (key != null) {
						nbt.putString("Lock", key);
					}
				}
			}
		}

		return super.use(state, worldIn, pos, player, handIn, hit);
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader worldIn) {
		return new TreasureChestTileEntity();
	}
		
}
