package com.jia6261.redstonecomputer;

import com.jia6261.redstonecomputer.block.ModBlocks;
import com.jia6261.redstonecomputer.item.ModItems;
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

        // Register custom blocks and items
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);

        // Register the mod event bus to the main event bus
        MinecraftForge.EVENT_BUS.register(this);
    }
}
