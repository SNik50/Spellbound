package com.ombremoon.spellbound.client.gui.toasts;

import com.ombremoon.spellbound.common.magic.api.SpellType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class LevelUpToast implements Toast {
    public static final String SPELL_TRANS = "spellbound.toast.spell_level_up";
    public static final String PATH_TRANS = "spellbound.toast.path_level_up";
    private int level;
    private SpellboundToasts toast;
    private SpellType<?> spell;

    public LevelUpToast(int level, SpellboundToasts toast, @Nullable SpellType<?> spell) {
        this.level = level;
        this.toast = toast;
        this.spell = spell;
    }

    @Override
    public Visibility render(GuiGraphics guiGraphics, ToastComponent toastComponent, long timeVisible) {
        Font font = toastComponent.getMinecraft().font;

        guiGraphics.blit(
                toast.getTexture(),
                0, 0,
                0, 0,
                this.width(), this.height(),
                this.width(), this.height());

        String popupText = this.spell == null
                ? I18n.get(PATH_TRANS, this.toast.getPath().name().toLowerCase(), level)
                : I18n.get(SPELL_TRANS, this.spell.createSpell().getName(), level);
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
