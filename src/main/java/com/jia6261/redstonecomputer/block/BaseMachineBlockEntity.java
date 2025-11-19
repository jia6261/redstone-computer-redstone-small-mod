package com.jia6261.redstonecomputer.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseMachineBlockEntity extends BlockEntity implements MenuProvider {
    protected final ItemStackHandler itemHandler;
    protected final ContainerData data;
    protected LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected int progress = 0;
    protected final int maxProgress;

    public BaseMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize, int maxProgress) {
        super(type, pos, state);
        this.itemHandler = new ItemStackHandler(inventorySize);
        this.maxProgress = maxProgress;
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> BaseMachineBlockEntity.this.progress;
                    case 1 -> BaseMachineBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> BaseMachineBlockEntity.this.progress = value;
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
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        progress = tag.getInt("progress");
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public ContainerData getData() {
        return data;
    }

    // MenuProvider methods
    @Override
    public Component getDisplayName() {
        return Component.translatable("block." + RedstoneComputer.MOD_ID + "." + getBlockState().getBlock().getDescriptionId());
    }

    @Nullable
    @Override
    public abstract AbstractContainerMenu createMenu(int id, Inventory inventory, Player player);

    // Tick logic for all machines
    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {
        if (level.isClientSide) {
            return;
        }

        if (be instanceof BaseMachineBlockEntity entity) {
            if (entity.canProcess()) {
                entity.progress++;
                if (entity.progress >= entity.maxProgress) {
                    entity.processItem();
                    entity.progress = 0;
                }
                entity.setChanged();
            } else {
                entity.progress = 0;
                entity.setChanged();
            }
        }
    }

    protected abstract boolean canProcess();
    protected abstract void processItem();
}
