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
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraft.world.item.Items;
import com.jia6261.redstonecomputer.block.RefiningDeviceEntity;
import com.jia6261.redstonecomputer.item.ModItems;

import static com.jia6261.redstonecomputer.block.ModBlockEntities.REFINING_DEVICE_ENTITY;

public class RefiningDeviceMenu extends AbstractContainerMenu {
    private final BlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public RefiningDeviceMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public RefiningDeviceMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.REFINING_DEVICE_MENU.get(), id);
        this.blockEntity = entity;
        this.level = inv.player.level();
        this.data = data;
        ContainerLevelAccess.create(level, entity.getBlockPos());

        if (entity instanceof RefiningDeviceEntity refiningDeviceEntity) {
            // 输入槽位 0: 玻璃
            this.addSlot(new SlotItemHandler(refiningDeviceEntity.getItemHandler(), 0, 56, 35) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() == Items.GLASS;
                }
            });
            // 输出槽位 1: 单质硅
            this.addSlot(new SlotItemHandler(refiningDeviceEntity.getItemHandler(), 1, 116, 35) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });
        }

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

        addDataSlots(data);
    }

    public int getProgress() {
        return this.data.get(0);
    }

    public int getMaxProgress() {
        return this.data.get(1);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot sourceSlot = this.slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // 槽位索引 0-1 是方块实体槽位，2-29 是背包，30-38 是快捷栏
        if (index < 2) {
            // 从方块实体槽位移动到玩家背包/快捷栏
            if (!this.moveItemStackTo(sourceStack, 2, 38, true)) {
                return ItemStack.EMPTY;
            }
        } else if (index < 38) {
            // 从玩家背包/快捷栏移动到方块实体槽位
            if (sourceStack.getItem() == Items.GLASS) {
                if (!this.moveItemStackTo(sourceStack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, REFINING_DEVICE_ENTITY.get().getBlock());
    }
}
