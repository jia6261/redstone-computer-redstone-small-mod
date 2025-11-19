package com.jia6261.redstonecomputer.block;

import com.jia6261.redstonecomputer.RedstoneComputer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RedstoneComputer.MOD_ID);

    public static final RegistryObject<BlockEntityType<ComputerBlockEntity>> COMPUTER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("computer_block_entity", () ->
                    BlockEntityType.Builder.of(ComputerBlockEntity::new,
                            ModBlocks.COMPUTER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<RefiningDeviceEntity>> REFINING_DEVICE_ENTITY =
            BLOCK_ENTITIES.register("refining_device_entity", () ->
                    BlockEntityType.Builder.of(RefiningDeviceEntity::new,
                            ModBlocks.REFINING_DEVICE.get()).build(null));

    public static <T extends BlockEntity> BlockEntityTicker<T> getRefiningTicker(Level level, BlockEntityType<T> type, BlockEntityType<?> expectedType) {
        return level.isClientSide ? null : createTickerHelper(type, expectedType, RefiningDeviceEntity::tick);
    }

    public static final RegistryObject<BlockEntityType<SiliconWaferFabricatorEntity>> SILICON_WAFER_FABRICATOR_ENTITY =
            BLOCK_ENTITIES.register("silicon_wafer_fabricator_entity", () ->
                    BlockEntityType.Builder.of(SiliconWaferFabricatorEntity::new,
                            ModBlocks.SILICON_WAFER_FABRICATOR.get()).build(null));

    public static <T extends BlockEntity> BlockEntityTicker<T> getFabricatorTicker(Level level, BlockEntityType<T> type, BlockEntityType<?> expectedType) {
        return level.isClientSide ? null : createTickerHelper(type, expectedType, SiliconWaferFabricatorEntity::tick);
    }

    public static final RegistryObject<BlockEntityType<LithographyMachineEntity>> LITHOGRAPHY_MACHINE_ENTITY =
            BLOCK_ENTITIES.register("lithography_machine_entity", () ->
                    BlockEntityType.Builder.of(LithographyMachineEntity::new,
                            ModBlocks.LITHOGRAPHY_MACHINE.get()).build(null));

    public static <T extends BlockEntity> BlockEntityTicker<T> getLithographyTicker(Level level, BlockEntityType<T> type, BlockEntityType<?> expectedType) {
        return level.isClientSide ? null : createTickerHelper(type, expectedType, LithographyMachineEntity::tick);
    }

    private static <T extends BlockEntity> BlockEntityTicker<T> createTickerHelper(BlockEntityType<T> type, BlockEntityType<?> expectedType, BlockEntityTicker<? super T> ticker) {
        return expectedType == type ? (BlockEntityTicker<T>) ticker : null;
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
