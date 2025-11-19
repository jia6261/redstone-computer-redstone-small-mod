package com.jia6261.redstonecomputer.block;

import com.jia6261.redstonecomputer.block.ModBlockEntities;
import com.jia6261.redstonecomputer.block.BaseMachineBlockEntity;
import com.jia6261.redstonecomputer.item.ModItems;
import com.jia6261.redstonecomputer.menu.LithographyMachineMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

// 光刻机方块实体：用于处理硅晶圆加工为高级芯片的逻辑
public class LithographyMachineEntity extends BaseMachineBlockEntity {
    private int cores = 0; // 用于存储当前正在处理的芯片核心数

    public LithographyMachineEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LITHOGRAPHY_MACHINE_ENTITY.get(), pos, state, 3, 0); // 3 slots, maxProgress will be set dynamically
        // 重新定义 data，因为 maxProgress 是动态的，需要双向同步
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> LithographyMachineEntity.this.progress;
                    case 1 -> LithographyMachineEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> LithographyMachineEntity.this.progress = value;
                    case 1 -> LithographyMachineEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("cores", cores);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        cores = tag.getInt("cores");
    }

    @Override
    protected boolean canProcess() {
        ItemStack waferStack = this.itemHandler.getStackInSlot(0);
        ItemStack blueprintStack = this.itemHandler.getStackInSlot(1);
        ItemStack outputStack = this.itemHandler.getStackInSlot(2);

        if (waferStack.getItem() != ModItems.SILICON_WAFER.get() || waferStack.getCount() < 1) {
            return false;
        }
        if (blueprintStack.getItem() != ModItems.BLUEPRINT.get() || blueprintStack.getCount() < 1) {
            return false;
        }

        int targetCores = getCoresFromBlueprint(blueprintStack);
        if (targetCores == 0) {
            return false;
        }

        // 检查输出槽位是否有空间
        ItemStack resultStack = getOutputStack(targetCores);
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
        int targetCores = getCoresFromBlueprint(this.itemHandler.getStackInSlot(1));
        if (targetCores == 0) return;

        // 消耗输入
        this.itemHandler.extractItem(0, 1, false); // 消耗 1 个硅晶圆

        // 产出输出
        ItemStack outputStack = getOutputStack(targetCores);
        this.itemHandler.insertItem(2, outputStack, false);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new LithographyMachineMenu(id, inventory, this, this.data);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {
        if (level.isClientSide) {
            return;
        }

        if (be instanceof LithographyMachineEntity entity) {
            ItemStack blueprintStack = entity.itemHandler.getStackInSlot(1);
            int targetCores = getCoresFromBlueprint(blueprintStack);

            if (entity.canProcess()) {
                if (entity.progress == 0) {
                    entity.maxProgress = getProcessingTime(targetCores);
                }

                entity.progress++;
                if (entity.progress >= entity.maxProgress) {
                    entity.processItem();
                    entity.progress = 0;
                    entity.maxProgress = 0;
                }
                entity.setChanged();
            } else {
                entity.progress = 0;
                entity.maxProgress = 0;
                entity.setChanged();
            }
        }
    }

    private static int getCoresFromBlueprint(ItemStack blueprintStack) {
        // 假设图纸的 NBT 标签中存储了核心数
        CompoundTag tag = blueprintStack.getTag();
        if (tag != null && tag.contains("cores")) {
            return tag.getInt("cores");
        }
        // 默认返回 1 核，或者可以根据图纸的 Item ID 来判断
        // 鉴于目前没有实现图纸的 NBT 写入，我们暂时使用一个硬编码的逻辑
        // 假设图纸的名称决定了核心数，例如 "Blueprint (4-Core)"
        String name = blueprintStack.getHoverName().getString();
        if (name.contains("4-Core")) return 4;
        if (name.contains("3-Core")) return 3;
        if (name.contains("2-Core")) return 2;
        if (name.contains("1-Core")) return 1;
        return 0;
    }

    private static int getProcessingTime(int cores) {
        // 10 minutes per core * 20 ticks/second * 60 seconds/minute = 12000 ticks per core
        return cores * 12000;
    }

    private static ItemStack getOutputStack(int cores) {
        return switch (cores) {
            case 1 -> new ItemStack(ModItems.CHIP_1_CORE.get());
            case 2 -> new ItemStack(ModItems.CHIP_2_CORE.get());
            case 3 -> new ItemStack(ModItems.CHIP_3_CORE.get());
            case 4 -> new ItemStack(ModItems.CHIP_4_CORE.get());
            default -> ItemStack.EMPTY;
        };
    }
}
