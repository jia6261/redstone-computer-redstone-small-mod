package com.jia6261.redstonecomputer.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import com.jia6261.redstonecomputer.item.ModItems;

// 硅晶圆加工机方块实体：用于处理单质硅和图纸加工为硅晶圆的逻辑
public class SiliconWaferFabricatorEntity extends BlockEntity {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3); // 0: 输入单质硅, 1: 输入图纸, 2: 输出硅晶圆
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private int progress = 0;
    private final int maxProgress = 600; // 30 seconds (600 ticks) to fabricate one wafer

    protected final ContainerData data;

    public SiliconWaferFabricatorEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SILICON_WAFER_FABRICATOR_ENTITY.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> SiliconWaferFabricatorEntity.this.progress;
                    case 1 -> SiliconWaferFabricatorEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> SiliconWaferFabricatorEntity.this.progress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("progress", progress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        progress = tag.getInt("progress");
        super.load(tag);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public ContainerData getData() {
        return data;
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {
        if (level.isClientSide) {
            return;
        }

        SiliconWaferFabricatorEntity entity = (SiliconWaferFabricatorEntity) be;
        ItemStack siliconStack = entity.itemHandler.getStackInSlot(0);
        ItemStack blueprintStack = entity.itemHandler.getStackInSlot(1);

        if (canFabricate(siliconStack, blueprintStack, entity.itemHandler.getStackInSlot(2))) {
            entity.progress++;
            if (entity.progress >= entity.maxProgress) {
                fabricateItem(entity);
                entity.progress = 0;
            }
            entity.setChanged();
        } else {
            entity.progress = 0;
            entity.setChanged();
        }
    }

    private static boolean canFabricate(ItemStack siliconStack, ItemStack blueprintStack, ItemStack outputStack) {
        if (siliconStack.getItem() != ModItems.PURE_SILICON.get() || siliconStack.getCount() < 1) {
            return false;
        }
        // 检查图纸是否存在
        if (blueprintStack.getItem() != ModItems.BLUEPRINT.get() || blueprintStack.getCount() < 1) {
            return false;
        }

        ItemStack resultStack = new ItemStack(ModItems.SILICON_WAFER.get());
        if (outputStack.isEmpty()) {
            return true;
        }
        if (outputStack.getItem() != resultStack.getItem()) {
            return false;
        }
        return outputStack.getCount() + resultStack.getCount() <= outputStack.getMaxStackSize();
    }

    private static void fabricateItem(SiliconWaferFabricatorEntity entity) {
        // 消耗输入
        entity.itemHandler.extractItem(0, 1, false); // 消耗 1 个单质硅
        // 图纸不消耗

        // 产出输出
        ItemStack resultStack = new ItemStack(ModItems.SILICON_WAFER.get());
        entity.itemHandler.insertItem(2, resultStack, false);
    }
}
