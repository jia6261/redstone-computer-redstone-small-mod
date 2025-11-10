package com.jia6261.redstonecomputer.item;

import com.jia6261.redstonecomputer.RedstoneComputer;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, RedstoneComputer.MOD_ID);

    // 核心物品 (Core Items)
    // 电脑芯片 (Computer Chip) - 稀有物品
    public static final RegistryObject<Item> COMPUTER_CHIP = ITEMS.register("computer_chip",
            () -> new Item(new Item.Properties()));

    // 红石缩小器材 (Redstone Shrinker) - 稀有物品，用于进入缩小器世界
    public static final RegistryObject<Item> REDSTONE_SHRINKER = ITEMS.register("redstone_shrinker",
            () -> new Item(new Item.Properties()));

    // 合成中间件 (Crafting Intermediates)
    // 纸板 (Cardboard)
    public static final RegistryObject<Item> CARDBOARD = ITEMS.register("cardboard",
            () -> new Item(new Item.Properties()));

    // 纸盒 (Cardboard Box)
    public static final RegistryObject<Item> CARDBOARD_BOX = ITEMS.register("cardboard_box",
            () -> new Item(new Item.Properties()));

    // 电脑主板 (Computer Motherboard)
    public static final RegistryObject<Item> COMPUTER_MOTHERBOARD = ITEMS.register("computer_motherboard",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
