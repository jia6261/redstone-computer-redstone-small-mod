package com.jia6261.redstonecomputer.block;

import com.jia6261.redstonecomputer.menu.SiliconWaferFabricatorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import com.jia6261.redstonecomputer.block.ModBlockEntities;
import com.jia6261.redstonecomputer.block.BaseMachineBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import com.jia6261.redstonecomputer.item.ModItems;

// 硅晶圆加工机方块实体：用于处理单质硅和图纸加工为硅晶圆的逻辑
public class SiliconWaferFabricatorEntity extends BaseMachineBlockEntity {

    public SiliconWaferFabricatorEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SILICON_WAFER_FABRICATOR_ENTITY.get(), pos, state, 3, 600); // 3 slots, 600 ticks (30s)
    }

    @Override
    protected boolean canProcess() {
        ItemStack siliconStack = this.itemHandler.getStackInSlot(0);
        ItemStack blueprintStack = this.itemHandler.getStackInSlot(1);
        ItemStack outputStack = this.itemHandler.getStackInSlot(2);

        // 检查输入：需要 1 个单质硅
        if (siliconStack.getItem() != ModItems.PURE_SILICON.get() || siliconStack.getCount() < 1) {
            return false;
        }
        // 检查图纸：需要 1 张图纸 (图纸不消耗)
        if (blueprintStack.getItem() != ModItems.BLUEPRINT.get() || blueprintStack.getCount() < 1) {
            return false;
        }

        // 检查输出：产出硅晶圆
        ItemStack resultStack = new ItemStack(ModItems.SILICON_WAFER.get());
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
        this.itemHandler.extractItem(0, 1, false); // 消耗 1 个单质硅
        // 图纸不消耗

        // 产出输出
        ItemStack resultStack = new ItemStack(ModItems.SILICON_WAFER.get());
        this.itemHandler.insertItem(2, resultStack, false);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new SiliconWaferFabricatorMenu(id, inventory, this, this.data);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {
        BaseMachineBlockEntity.tick(level, pos, state, be);
    }
}
