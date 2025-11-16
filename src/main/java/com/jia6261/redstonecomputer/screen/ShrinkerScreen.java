package com.jia6261.redstonecomputer.screen;

import com.jia6261.redstonecomputer.RedstoneComputer;
import com.jia6261.redstonecomputer.menu.ShrinkerMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ShrinkerScreen extends AbstractContainerScreen<ShrinkerMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(RedstoneComputer.MOD_ID, "textures/gui/shrinker_gui.png");

    public ShrinkerScreen(ShrinkerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 256; // 假设 GUI 宽度
        this.imageHeight = 256; // 假设 GUI 高度
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        // 在这里添加按钮等交互组件，例如 ENTER MICRO-WORLD 按钮
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        // 占位符：渲染配置信息
        graphics.drawString(this.font, "Micro-World Config Terminal", this.leftPos + 10, this.topPos + 10, 0x00FFFF, false);
        graphics.drawString(this.font, "Input Slots: 3/3", this.leftPos + 10, this.topPos + 30, 0x00FF00, false);
        graphics.drawString(this.font, "Output Slots: 10/10", this.leftPos + 10, this.topPos + 45, 0xFF0000, false);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}
