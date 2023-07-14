package com.aetherteam.aether.client.gui.screen.inventory;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.component.LorePageButton;
import com.aetherteam.aether.inventory.menu.LoreBookMenu;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoreBookScreen extends AbstractContainerScreen<LoreBookMenu> {
    private static final ResourceLocation TEXTURE_LORE_BACKING = new ResourceLocation(Aether.MODID, "textures/gui/menu/lore_backing.png");
    private static final ResourceLocation TEXTURE_LORE_BOOK = new ResourceLocation(Aether.MODID, "textures/gui/menu/lore_book.png");

    private final Map<Integer, List<FormattedCharSequence>> pages = new HashMap<>();

    private LorePageButton previousButton, nextButton;
    private int currentPageNumber;
    private ItemStack lastStack;

    public LoreBookScreen(LoreBookMenu screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.imageWidth = 256;
        this.imageHeight = 199;
    }

    @Override
    protected void init() {
        super.init();
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - (this.imageHeight)) / 2;

        this.previousButton = this.addRenderableWidget(new LorePageButton(i + 14, j + 169, 20, 20, Component.literal("<"), (p_214201_1_) -> {
            if (this.currentPageNumber > 0) currentPageNumber--;
        }));

        this.nextButton = this.addRenderableWidget(new LorePageButton(i + 221, j + 169, 20, 20, Component.literal(">"), (p_214201_1_) -> {
            if (this.currentPageNumber < this.pages.size() - 1) currentPageNumber++;
        }));
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        this.renderBg(matrixStack, partialTicks, mouseX, mouseY);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int x, int y) {
        Component previous = Component.translatable("gui.aether.book_of_lore.previous");
        Component next = Component.translatable("gui.aether.book_of_lore.next");
        this.drawBookText(matrixStack, this.font, previous, 13, 158);
        this.drawBookText(matrixStack, this.font, next, 221, 158);

        Component book = Component.translatable("gui.aether.book_of_lore.book");
        Component ofLore = Component.translatable("gui.aether.book_of_lore.of_lore");
        this.drawCenteredBookText(matrixStack, this.font, book, 75, 20);
        this.drawCenteredBookText(matrixStack, this.font, ofLore, 75, 20 + 10);

        Component item = Component.translatable("gui.aether.book_of_lore.item");
        this.drawRightBookText(matrixStack, this.font, item, 78, 67);

        ItemStack itemStack = this.menu.slots.get(0).getItem();
        if (!itemStack.isEmpty()) {
            String entryKey = this.menu.getLoreEntryKey(itemStack);

            if (I18n.exists(entryKey)) {
                Component entry = Component.translatable(entryKey);
                this.createPages(entry);

                if (this.currentPageNumber == 0) {
                    Component title = itemStack.getHoverName();
                    createText(matrixStack, this.font.split(title, 98), 136, 10);

                    createText(matrixStack, this.pages.get(0), 136, 32);
                } else {
                    createText(matrixStack, this.pages.get(this.currentPageNumber), 136, 10);
                }
            }
        }
        if (itemStack.isEmpty() || !itemStack.is(this.lastStack.getItem())) {
            this.pages.clear();
            this.currentPageNumber = 0;
        }

        this.previousButton.setIsActive(this.currentPageNumber > 0);
        this.nextButton.setIsActive(this.currentPageNumber < this.pages.size() - 1);

        this.lastStack = itemStack;
    }

    private void createText(PoseStack matrixStack, List<FormattedCharSequence> reorderingProcessors, int x, int y) {
        int length = 0;
        for (FormattedCharSequence line : reorderingProcessors) {
            this.drawBookText(matrixStack, this.font, line, x, y + (length * 10));
            length++;
        }
    }

    private void createPages(Component entry) {
        List<FormattedCharSequence> formattedText = new ArrayList<>(this.font.split(entry, 98));
        List<FormattedCharSequence> firstPage;
        if (formattedText.size() < 6) {
            firstPage = formattedText.subList(0, formattedText.size());
            this.pages.put(0, firstPage);
        } else {
            firstPage = formattedText.subList(0, 6);
            this.pages.put(0, firstPage);

            List<FormattedCharSequence> remainingPages = formattedText.subList(6, formattedText.size());

            final List<List<FormattedCharSequence>> list = Lists.partition(remainingPages, 8);

            for (int i = 1; i < list.size() + 1; i++) {
                this.pages.put(i, list.get(i - 1));
            }
        }
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - (this.imageHeight)) / 2;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE_LORE_BACKING);
        blit(matrixStack, i, j - 4, 0, 0, this.imageWidth, this.imageHeight + 56, 256, 256);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE_LORE_BOOK);
        blit(matrixStack, i + 12, j + 2, 0, 0, this.imageWidth, this.imageHeight + 56, 256, 256);
    }

    private void drawBookText(PoseStack matrixStack, Font fontRenderer, FormattedCharSequence font, int x, int y) {
        fontRenderer.draw(matrixStack, font, (float) x, (float) y, 4210752);
    }

    private void drawBookText(PoseStack matrixStack, Font fontRenderer, Component font, int x, int y) {
        FormattedCharSequence text = font.getVisualOrderText();
        fontRenderer.draw(matrixStack, text, (float) x, (float) y, 4210752);
    }

    private void drawRightBookText(PoseStack matrixStack, Font fontRenderer, Component font, int x, int y) {
        FormattedCharSequence text = font.getVisualOrderText();
        fontRenderer.draw(matrixStack, text, (float) (x - fontRenderer.width(text)), (float) y, 4210752);
    }

    private void drawCenteredBookText(PoseStack matrixStack, Font fontRenderer, Component font, int x, int y) {
        FormattedCharSequence text = font.getVisualOrderText();
        fontRenderer.draw(matrixStack, text, (float) (x - fontRenderer.width(text) / 2), (float) y, 4210752);
    }
}
