package com.jia6261.redstonecomputer.block;

import com.jia6261.redstonecomputer.block.ModBlockEntities;
import com.jia6261.redstonecomputer.item.ModItems;
import com.jia6261.redstonecomputer.menu.RefiningDeviceMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

// 精炼设备方块实体：用于处理玻璃精炼为单质硅的逻辑
public class RefiningDeviceEntity extends BaseMachineBlockEntity {

    public RefiningDeviceEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REFINING_DEVICE_ENTITY.get(), pos, state, 2, 400); // 2 slots, 400 ticks (20s)
    }

    @Override
    protected boolean canProcess() {
        ItemStack inputStack = this.itemHandler.getStackInSlot(0);
        ItemStack outputStack = this.itemHandler.getStackInSlot(1);

        if (inputStack.getItem() != Items.GLASS || inputStack.getCount() < 1) {
            return false;
        }

        ItemStack resultStack = new ItemStack(ModItems.PURE_SILICON.get());
        if (outputStack.isEmpty()) {
            return true;
        }
        if (outputStack.getItem() != resultStack.getItem()) {
            return false;
        }
        return outputStack.getCount() + resultStack.getCount() <= outputStack.getMaxStackSize();
    }

    @Override
    protected void processItem() {
        // 消耗输入
        this.itemHandler.extractItem(0, 1, false);

        // 产出输出
        ItemStack resultStack = new ItemStack(ModItems.PURE_SILICON.get());
        this.itemHandler.insertItem(1, resultStack, false);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new RefiningDeviceMenu(id, inventory, this, this.data);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {
        BaseMachineBlockEntity.tick(level, pos, state, be);
    }
}
