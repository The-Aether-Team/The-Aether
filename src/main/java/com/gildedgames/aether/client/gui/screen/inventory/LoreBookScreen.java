package com.gildedgames.aether.client.gui.screen.inventory;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.gui.button.LorePageButton;
import com.gildedgames.aether.common.inventory.container.LoreBookContainer;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoreBookScreen extends ContainerScreen<LoreBookContainer>
{
    private static final ResourceLocation TEXTURE_LORE_BACKING = new ResourceLocation(Aether.MODID, "textures/gui/lore_backing.png");
    private static final ResourceLocation TEXTURE_LORE_BOOK = new ResourceLocation(Aether.MODID, "textures/gui/lore_book.png");

    private Map<Integer, List<IReorderingProcessor>> pages = new HashMap<>();

    private LorePageButton previousButton, nextButton;
    private int currentPageNumber;

    public LoreBookScreen(LoreBookContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        this.xSize = 256;
        this.ySize = 199;
    }

    @Override
    protected void init() {
        super.init();
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - (this.ySize)) / 2;

        this.previousButton = this.addButton(new LorePageButton(i + 14, j + 169, 20, 20, new StringTextComponent("<"), (p_214201_1_) -> {
            if (this.currentPageNumber > 0) currentPageNumber--;
        }));

        this.nextButton = this.addButton(new LorePageButton(i + 221, j + 169, 20, 20, new StringTextComponent(">"), (p_214201_1_) -> {
            if (this.currentPageNumber < this.pages.size() - 1) currentPageNumber++;
        }));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        this.drawGuiContainerBackgroundLayer(matrixStack, partialTicks, mouseX, mouseY);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        ITextComponent previous = new TranslationTextComponent("gui.aether.book_of_lore.previous");
        ITextComponent next = new TranslationTextComponent("gui.aether.book_of_lore.next");
        this.drawBookText(matrixStack, this.font, previous, 13, 158);
        this.drawBookText(matrixStack, this.font, next, 221, 158);

        ITextComponent book = new TranslationTextComponent("gui.aether.book_of_lore.book");
        ITextComponent ofLore = new TranslationTextComponent("gui.aether.book_of_lore.of_lore");
        this.drawCenteredBookText(matrixStack, this.font, book, 75, 20);
        this.drawCenteredBookText(matrixStack, this.font, ofLore, 75, 20 + 10);

        ITextComponent item = new TranslationTextComponent("gui.aether.book_of_lore.item");
        this.drawRightBookText(matrixStack, this.font, item, 78, 67);

        ItemStack itemStack = this.container.inventorySlots.get(0).getStack();
        if (!itemStack.isEmpty()) {
            String entryKey = this.container.getLoreEntryKey(itemStack);

            if (I18n.hasKey(entryKey)) {
                ITextComponent entry = new TranslationTextComponent(entryKey);
                this.createPages(entry);

                if (this.currentPageNumber == 0) {
                    ITextComponent title = new TranslationTextComponent(itemStack.getTranslationKey());
                    createText(matrixStack, this.font.trimStringToWidth(title, 98), 136, 10);

                    createText(matrixStack, this.pages.get(0), 136, 32);
                } else {
                    createText(matrixStack, this.pages.get(this.currentPageNumber), 136, 10);
                }
            }
        } else {
            this.pages.clear();
            this.currentPageNumber = 0;
        }

        this.previousButton.setIsActive(this.currentPageNumber > 0);
        this.nextButton.setIsActive(this.currentPageNumber < this.pages.size() - 1);
    }

    private void createText(MatrixStack matrixStack, List<IReorderingProcessor> reorderingProcessors, int x, int y) {
        int length = 0;
        for (IReorderingProcessor line : reorderingProcessors) {
            this.drawBookText(matrixStack, this.font, line, x, y + (length * 10));
            length++;
        }
    }

    private void createPages(ITextComponent entry) {
        List<IReorderingProcessor> formattedText = new ArrayList<>(this.font.trimStringToWidth(entry, 98));
        List<IReorderingProcessor> firstPage;
        if (formattedText.size() < 6) {
            firstPage = formattedText.subList(0, formattedText.size());
            this.pages.put(0, firstPage);
        } else {
            firstPage = formattedText.subList(0, 6);
            this.pages.put(0, firstPage);

            List<IReorderingProcessor> remainingPages = formattedText.subList(6, formattedText.size());

            final List<List<IReorderingProcessor>> list = Lists.partition(remainingPages, 8);

            for (int i = 1; i < list.size() + 1; i++) {
                this.pages.put(i, list.get(i - 1));
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - (this.ySize)) / 2;

        this.getMinecraft().getTextureManager().bindTexture(TEXTURE_LORE_BACKING);
        blit(matrixStack, i, j - 4, 0, 0, this.xSize, this.ySize + 56, 256, 256);

        this.getMinecraft().getTextureManager().bindTexture(TEXTURE_LORE_BOOK);
        blit(matrixStack, i + 12, j + 2, 0, 0, this.xSize, this.ySize + 56, 256, 256);
    }

    private void drawBookText(MatrixStack matrixStack, FontRenderer fontRenderer, IReorderingProcessor font, int x, int y) {
        fontRenderer.func_238422_b_(matrixStack, font, (float) x, (float) y, 4210752);
    }

    private void drawBookText(MatrixStack matrixStack, FontRenderer fontRenderer, ITextComponent font, int x, int y) {
        IReorderingProcessor text = font.func_241878_f();
        fontRenderer.func_238422_b_(matrixStack, text, (float) x, (float) y, 4210752);
    }

    private void drawRightBookText(MatrixStack matrixStack, FontRenderer fontRenderer, ITextComponent font, int x, int y) {
        IReorderingProcessor text = font.func_241878_f();
        fontRenderer.func_238422_b_(matrixStack, text, (float) (x - fontRenderer.func_243245_a(text)), (float) y, 4210752);
    }

    private void drawCenteredBookText(MatrixStack matrixStack, FontRenderer fontRenderer, ITextComponent font, int x, int y) {
        IReorderingProcessor text = font.func_241878_f();
        fontRenderer.func_238422_b_(matrixStack, text, (float) (x - fontRenderer.func_243245_a(text) / 2), (float) y, 4210752);
    }
}
