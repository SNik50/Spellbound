package com.ombremoon.spellbound.client.gui.toasts;

import com.ombremoon.spellbound.common.init.SBPageScraps;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class PageScrapUnlockedToast implements Toast {
    private static final String translationKey = "spellbound.toast.scrap_unlocked";
    private final ResourceLocation scrap;

    public PageScrapUnlockedToast(ResourceLocation scrap) {
        this.scrap = scrap;
    }

    @Override
    public Visibility render(GuiGraphics guiGraphics, ToastComponent toastComponent, long timeVisible) {
        Font font = toastComponent.getMinecraft().font;

        guiGraphics.blit(
                SBPageScraps.getTexture(scrap),
                0, 0,
                0, 0,
                this.width(), this.height(),
                this.width(), this.height());

        String popupText = I18n.get(translationKey);
        int lettersRevealed = Math.clamp(timeVisible / 120, 0, popupText.length());
        Component comp = Component.translatable(popupText.substring(0, lettersRevealed))
                .append(Component.translatable(popupText.substring(lettersRevealed))
                        .withStyle(ChatFormatting.OBFUSCATED));

        guiGraphics.drawString(toastComponent.getMinecraft().font,
                comp,
                (width()-font.width(comp))/2,
                (height()-8)/2,
                0,
                false);

        return timeVisible < 8000 ? Visibility.SHOW : Visibility.HIDE;
    }
}
