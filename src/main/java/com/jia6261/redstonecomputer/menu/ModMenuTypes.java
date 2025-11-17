package com.jia6261.redstonecomputer.menu;

import com.jia6261.redstonecomputer.RedstoneComputer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, RedstoneComputer.MOD_ID);

    public static final RegistryObject<MenuType<ComputerMenu>> COMPUTER_MENU =
            registerMenuType("computer_menu", ComputerMenu::new);

    public static final RegistryObject<MenuType<ShrinkerMenu>> SHRINKER_MENU =
            registerMenuType("shrinker_menu", ShrinkerMenu::new);

    public static final RegistryObject<MenuType<RefiningDeviceMenu>> REFINING_DEVICE_MENU =
            registerMenuType("refining_device_menu", RefiningDeviceMenu::new);

    public static final RegistryObject<MenuType<SiliconWaferFabricatorMenu>> SILICON_WAFER_FABRICATOR_MENU =
            registerMenuType("silicon_wafer_fabricator_menu", SiliconWaferFabricatorMenu::new);

    public static final RegistryObject<MenuType<LithographyMachineMenu>> LITHOGRAPHY_MACHINE_MENU =
            registerMenuType("lithography_machine_menu", LithographyMachineMenu::new);

    // 占位符：红石缩小器材方块的 GUI
    // public static final RegistryObject<MenuType<ShrinkerMenu>> SHRINKER_MENU =
    //         registerMenuType("shrinker_menu", ShrinkerMenu::new);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String name, IForgeMenuType.MenuSupplier<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
