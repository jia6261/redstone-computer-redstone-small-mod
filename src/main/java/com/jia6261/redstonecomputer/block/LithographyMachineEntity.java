package com.jia6261.redstonecomputer.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
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
import com.jia6261.redstonecomputer.item.ModItems;

// 光刻机方块实体：用于处理硅晶圆加工为高级芯片的逻辑
public class LithographyMachineEntity extends BlockEntity {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3); // 0: 输入硅晶圆, 1: 输入图纸, 2: 输出芯片
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private int progress = 0;
    private int maxProgress = 0; // 10 minutes per core * 20 ticks/second * 60 seconds/minute = 12000 ticks per core

    protected final ContainerData data;

    public LithographyMachineEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LITHOGRAPHY_MACHINE_ENTITY.get(), pos, state);
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
        tag.putInt("maxProgress", maxProgress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        progress = tag.getInt("progress");
        maxProgress = tag.getInt("maxProgress");
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

        LithographyMachineEntity entity = (LithographyMachineEntity) be;
        ItemStack waferStack = entity.itemHandler.getStackInSlot(0);
        ItemStack blueprintStack = entity.itemHandler.getStackInSlot(1);

        if (canStartProcessing(waferStack, blueprintStack)) {
            if (entity.progress == 0) {
                entity.maxProgress = getProcessingTime(blueprintStack);
            }

            entity.progress++;
            if (entity.progress >= entity.maxProgress) {
                processItem(entity, blueprintStack);
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

    private static boolean canStartProcessing(ItemStack waferStack, ItemStack blueprintStack) {
        if (waferStack.getItem() != ModItems.SILICON_WAFER.get() || waferStack.getCount() < 1) {
            return false;
        }
        if (blueprintStack.getItem() != ModItems.BLUEPRINT.get() || blueprintStack.getCount() < 1) {
            return false;
        }

        int cores = getCoresFromBlueprint(blueprintStack);
        if (cores == 0) {
            return false;
        }

        // 检查输出槽位是否有空间
        ItemStack outputStack = getOutputStack(cores);
        ItemStack currentOutput = entity.itemHandler.getStackInSlot(2);
        if (currentOutput.isEmpty()) {
            return true;
        }
        if (currentOutput.getItem() == outputStack.getItem() && currentOutput.getCount() + outputStack.getCount() <= currentOutput.getMaxStackSize()) {
            return true;
        }
        return false;
    }

    private static int getCoresFromBlueprint(ItemStack blueprintStack) {
        // TODO: 实际图纸系统需要更复杂的逻辑来确定核心数
        // 暂时通过图纸的 NBT 或其他方式来模拟
        // 假设图纸的名称决定了核心数，例如 "Blueprint (4-Core)"
        String name = blueprintStack.getHoverName().getString();
        if (name.contains("1-Core")) return 1;
        if (name.contains("2-Core")) return 2;
        if (name.contains("3-Core")) return 3;
        if (name.contains("4-Core")) return 4;
        return 0;
    }

    private static int getProcessingTime(ItemStack blueprintStack) {
        int cores = getCoresFromBlueprint(blueprintStack);
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

    private static void processItem(LithographyMachineEntity entity, ItemStack blueprintStack) {
        int cores = getCoresFromBlueprint(blueprintStack);
        if (cores == 0) return;

        // 消耗输入
        entity.itemHandler.extractItem(0, 1, false); // 消耗 1 个硅晶圆

        // 产出输出
        ItemStack outputStack = getOutputStack(cores);
        entity.itemHandler.insertItem(2, outputStack, false);
    }
}
