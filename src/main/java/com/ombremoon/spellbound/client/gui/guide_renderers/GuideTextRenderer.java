package com.ombremoon.spellbound.client.gui.guide_renderers;

import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.GuideText;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.IPageElement;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.*;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.util.FormattedCharSequence;
import org.apache.logging.log4j.core.pattern.PatternFormatter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuideTextRenderer implements IPageElementRenderer<GuideText> {
    private int maxWidth = 0;
    private int height = 0;

    @Override
    public void render(GuideText element, GuiGraphics graphics, int leftPos, int topPos, int mouseX, int mouseY, float partialTick) {
        Font font = Minecraft.getInstance().font;

        boolean visible = isVisible(element.extras().pageScrap());
        Style style = Style.EMPTY;
        if (!visible) style = style.applyFormat(ChatFormatting.OBFUSCATED);

        style = style.withBold(element.extras().bold());
        style = style.withUnderlined(element.extras().underline());

        List<FormattedCharSequence> lines = font.split(
                Component.translatable(
                        element.translationKey())
                        .withStyle(style),
                element.extras().maxLineLength());
        this.height = lines.size() * font.lineHeight;

        for (int i = 0; i < lines.size(); i++) {
            int width = font.width(lines.get(i));
            if (this.maxWidth < width) this.maxWidth = width;
            graphics.drawString(font, lines.get(i), leftPos + element.position().xOffset(), topPos + element.position().yOffset() + (i * font.lineHeight), element.extras().colour(), element.extras().dropShadow());
        }
    }

    @Override
    public void handleClick(GuideText element) {
        if (element.extras().link().isBlank()) return;
        Minecraft minecraft = Minecraft.getInstance();
        String url = element.extras().link();

        try {
            URI uri = Util.parseAndValidateUntrustedUri(url);
            if (minecraft.options.chatLinksPrompt().get()) {
                minecraft.setScreen(new ConfirmLinkScreen(p_351659_ -> {
                    if (p_351659_) {
                        Util.getPlatform().openUri(uri);
                    }

                    minecraft.setScreen(null);
                }, url, true));
            } else {
                Util.getPlatform().openUri(uri);
            }
        } catch (URISyntaxException urisyntaxexception) {
            LOGGER.error("Can't open url for {}", url, urisyntaxexception);
        }
    }

    @Override
    public void handleHover(GuideText element, GuiGraphics guiGraphics, int leftPos, int topPos, int mouseX, int mouseY, float partialTick) {
        if (element.extras().hoverText().isBlank()) return;

        guiGraphics.drawString(Minecraft.getInstance().font,
                "IS IT WORKING YET?",
                leftPos + element.position().xOffset(),
                topPos + element.position().yOffset() + this.height + 10,
                element.extras().colour(), element.extras().dropShadow());
    }

    @Override
    public boolean isHovering(int mouseX, int mouseY, int leftPos, int topPos, GuideText element) {
        int startX = leftPos + element.position().xOffset();
        int startY = topPos + element.position().yOffset();
        return mouseX > startX && mouseX < startX + this.maxWidth
                && mouseY > startY && mouseY < startY + this.height;
    }
}
