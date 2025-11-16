package com.jia6261.redstonecomputer;

import com.jia6261.redstonecomputer.block.ModBlocks;
import com.jia6261.redstonecomputer.item.ModItems;
import com.jia6261.redstonecomputer.world.structure.ModStructures;
import com.jia6261.redstonecomputer.world.structure.ModStructureTypes;
import com.jia6261.redstonecomputer.block.ModBlockEntities;
import com.jia6261.redstonecomputer.menu.ModMenuTypes;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.client.gui.screens.MenuScreens;
import com.jia6261.redstonecomputer.screen.ComputerScreen;
import com.jia6261.redstonecomputer.screen.ShrinkerScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RedstoneComputer.MOD_ID)
public class RedstoneComputer
{
    // Define mod id
    public static final String MOD_ID = "redstone_computer";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LoggerFactory.getLogger(RedstoneComputer.class);

    public RedstoneComputer()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register custom blocks, items, and structures
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus); // 注册方块实体
        ModMenuTypes.MENUS.register(modEventBus); // 注册容器类型
        ModStructures.STRUCTURES.register(modEventBus);
        ModStructureTypes.STRUCTURE_TYPES.register(modEventBus);

        // Register the mod event bus to the main event bus
        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // 客户端设置
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuTypes.COMPUTER_MENU.get(), ComputerScreen::new);
            MenuScreens.register(ModMenuTypes.SHRINKER_MENU.get(), ShrinkerScreen::new);
        });
    }
}
