package org.despacito696969.mi_batch_crafting_addon.client;

import aztech.modern_industrialization.machines.gui.ClientComponentRenderer;
import aztech.modern_industrialization.machines.gui.GuiComponentClient;
import aztech.modern_industrialization.machines.gui.MachineScreen;
import aztech.modern_industrialization.util.Rectangle;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.FriendlyByteBuf;
import org.despacito696969.mi_batch_crafting_addon.BatchSelection;
import org.despacito696969.mi_batch_crafting_addon.MIBatchCraftingAddon;

import java.util.List;

public class BatchSelectionClient implements GuiComponentClient {
    Integer currentData;
    private Renderer renderer;

    public BatchSelectionClient(FriendlyByteBuf buf) {
        readCurrentData(buf);
    }

    @Override
    public ClientComponentRenderer createRenderer(MachineScreen machineScreen) {
        renderer = new Renderer(machineScreen.getGuiParams().backgroundWidth);
        return renderer;
    }

    @Override
    public void readCurrentData(FriendlyByteBuf buf) {
        currentData = buf.readVarInt();
    }

    class Renderer implements ClientComponentRenderer {
        private int mainPanelWidth;
        private static int btnSize = 12;
        private static int outerPadding = 2;
        private static int innerPadding = 2;

        private static int boxWidth = btnSize * 2 + outerPadding * 2 + innerPadding + 10;

        private static int borderSize = 3;

        int baseU = 150;
        int v = 58;

        private Renderer(int mainPanelWidth) {
            this.mainPanelWidth = mainPanelWidth;
        }

        private static final int yPos = 10 + 14 + 2 * 20 + 10;

        @Override
        public void addButtons(ButtonContainer container) {
            // This means that we expect casing and upgrade,
            // Might not be correct for fusion!

            int mid = (boxWidth - borderSize) / 2;
            int buttonY = yPos + btnSize + outerPadding;


            container.addButton(mainPanelWidth + outerPadding, buttonY, btnSize, btnSize,
                    syncId -> {
                        ClientPlayNetworking.send(BatchSelection.PacketsC2S.CHANGE_BATCH_SIZE, BatchSelection.PacketsC2S.encodeChangeBatchSize(syncId, false));
                    }, List::of,
                    (screen, button, matrices, mouseX, mouseY, delta) -> {
                        if (currentData <= 1) {
                            screen.blitButtonNoHighlight(button, matrices, baseU, v + 12);
                        }
                        else {
                            screen.blitButtonSmall(button, matrices, baseU, v);
                        }
                    });

            container.addButton(mainPanelWidth + boxWidth - borderSize - outerPadding - btnSize, buttonY, btnSize, btnSize,
                    syncId -> {
                        ClientPlayNetworking.send(BatchSelection.PacketsC2S.CHANGE_BATCH_SIZE, BatchSelection.PacketsC2S.encodeChangeBatchSize(syncId, true));
                    }, List::of,
                    (screen, button, matrices, mouseX, mouseY, delta) -> {
                        if (currentData >= MIBatchCraftingAddon.BATCH_MAX) {
                            screen.blitButtonNoHighlight(button, matrices, baseU + 12, v + 12);
                        }
                        else {
                            screen.blitButtonSmall(button, matrices, baseU + 12, v);
                        }
                    });
        }

        public Rectangle getBox(int leftPos, int topPos) {
            int height = btnSize + 10 + 14;
            return new Rectangle(leftPos + mainPanelWidth, topPos + yPos - 5, boxWidth, height);
        }

        @Override
        public void renderBackground(GuiGraphics guiGraphics, int x, int y) {
            var box = getBox(x, y);
            int textureX = box.x() - x - box.w();

            guiGraphics.blit(MachineScreen.BACKGROUND, box.x(), box.y(), textureX, 0, box.w(), box.h() - 4);
            guiGraphics.blit(MachineScreen.BACKGROUND, box.x(), box.y() + box.h() - 4, textureX, 252, box.w(), 4);

            var tooltip = "x" + currentData.toString();
            var width = Minecraft.getInstance().font.width(tooltip);
            guiGraphics.drawString(Minecraft.getInstance().font, tooltip,
                    (int)(box.x() + (boxWidth - width - borderSize) / 2f),
                    y + yPos, 0x404040, false);
        }

        @Override
        public void addExtraBoxes(List<Rectangle> rectangles, int leftPos, int topPos) {
            rectangles.add(getBox(leftPos, topPos));
        }
    }
}
