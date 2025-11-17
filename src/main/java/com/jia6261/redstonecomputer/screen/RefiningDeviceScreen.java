package com.jia6261.redstonecomputer.screen;

import com.jia6261.redstonecomputer.RedstoneComputer;
import com.jia6261.redstonecomputer.menu.RefiningDeviceMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class RefiningDeviceScreen extends AbstractContainerScreen<RefiningDeviceMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(RedstoneComputer.MOD_ID, "textures/gui/refining_device_gui.png");

    public RefiningDeviceScreen(RefiningDeviceMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        // TODO: 渲染进度条
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}
