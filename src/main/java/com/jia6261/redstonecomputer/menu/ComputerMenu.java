package com.jia6261.redstonecomputer.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import static com.jia6261.redstonecomputer.block.ModBlockEntities.COMPUTER_BLOCK_ENTITY;

public class ComputerMenu extends AbstractContainerMenu {
    private final BlockEntity blockEntity;
    private final Level level;

    public ComputerMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public ComputerMenu(int id, Inventory inv, BlockEntity entity) {
        super(ModMenuTypes.COMPUTER_MENU.get(), id);
        this.blockEntity = entity;
        this.level = inv.player.level();
        ContainerLevelAccess.create(level, entity.getBlockPos());

        // 玩家背包槽位 (Player Inventory Slots)
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // 玩家快捷栏槽位 (Player Hotbar Slots)
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inv, i, 8 + i * 18, 142));
        }

        // 电脑方块没有物品槽位，GUI 主要是用于交互
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY; // 禁用快速移动
    }

    @Override
    public boolean stillValid(Player player) {
        // 检查方块是否仍然存在
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, COMPUTER_BLOCK_ENTITY.get().getBlock());
    }
}
