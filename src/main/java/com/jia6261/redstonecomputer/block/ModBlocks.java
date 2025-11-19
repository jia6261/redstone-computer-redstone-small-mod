package com.jia6261.redstonecomputer.block;

import com.jia6261.redstonecomputer.RedstoneComputer;
import com.jia6261.redstonecomputer.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, RedstoneComputer.MOD_ID);

    // 核心方块 (Core Blocks)
    // 电脑方块 (Computer Block)
    // 继承 DirectionalKineticBlock 以支持 Create Mod 动力
    public static final RegistryObject<Block> COMPUTER_BLOCK = registerBlock("computer_block",
            () -> new ComputerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.0f)));

    // 服务器方块 (Server Block) - 100台电脑合成
    public static final RegistryObject<Block> SERVER_BLOCK = registerBlock("server_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(8.0f)));

    // 芯片生产设备 (Chip Production Devices)
    // 精炼设备 (Refining Device) - 将玻璃精炼为单质硅
    public static final RegistryObject<Block> REFINING_DEVICE = registerBlock("refining_device",
            () -> new RefiningDeviceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.0f)));

    // 硅晶圆加工机 (Silicon Wafer Fabricator) - 将单质硅加工为硅晶圆
    public static final RegistryObject<Block> SILICON_WAFER_FABRICATOR = registerBlock("silicon_wafer_fabricator",
            () -> new SiliconWaferFabricatorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.0f)));

    // 光刻机 (Lithography Machine) - 将硅晶圆加工为高级芯片
    public static final RegistryObject<Block> LITHOGRAPHY_MACHINE = registerBlock("lithography_machine",
            () -> new LithographyMachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(10.0f)));

    // 红石缩小器材方块 (Redstone Shrinker Block)
    public static final RegistryObject<Block> REDSTONE_SHRINKER_BLOCK = registerBlock("redstone_shrinker_block",
            () -> new ShrinkerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.0f).noOcclusion()));

    // 辅助方法：注册方块及其对应的物品
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    // 辅助方法：注册方块对应的物品
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
