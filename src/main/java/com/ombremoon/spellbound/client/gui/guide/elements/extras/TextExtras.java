package com.ombremoon.spellbound.client.gui.guide.elements.extras;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.resources.ResourceLocation;

public record TextExtras(ResourceLocation pageScrap, int colour, int maxLineLength, int lineGap, boolean dropShadow, boolean textWrapping, boolean centered, String link, boolean requireUnlockForLink, boolean underline, boolean bold, String hoverText, boolean italic) implements IElementExtra {
    public static final Codec<TextExtras> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.optionalFieldOf("pageScrap", CommonClass.customLocation("default")).forGetter(TextExtras::pageScrap),
            Codec.INT.optionalFieldOf("colour", 0).forGetter(TextExtras::colour),
            Codec.INT.optionalFieldOf("maxLineLength", 150).forGetter(TextExtras::maxLineLength),
            Codec.INT.optionalFieldOf("lineGap", 9).forGetter(TextExtras::lineGap),
            Codec.BOOL.optionalFieldOf("dropShadow", false).forGetter(TextExtras::dropShadow),
            Codec.BOOL.optionalFieldOf("textWrapping", true).forGetter(TextExtras::textWrapping),
            Codec.BOOL.optionalFieldOf("centered", false).forGetter(TextExtras::centered),
            Codec.STRING.optionalFieldOf("link", "").forGetter(TextExtras::link),
            Codec.BOOL.optionalFieldOf("unlock_for_link", true).forGetter(TextExtras::requireUnlockForLink),
            Codec.BOOL.optionalFieldOf("underline", false).forGetter(TextExtras::underline),
            Codec.BOOL.optionalFieldOf("bold", false).forGetter(TextExtras::bold),
            Codec.STRING.optionalFieldOf("hoverText", "").forGetter(TextExtras::hoverText),
            Codec.BOOL.optionalFieldOf("italic", false).forGetter(TextExtras::italic)
    ).apply(inst, TextExtras::new));

    public static TextExtras getDefault() {
        return new TextExtras(CommonClass.customLocation("default"), 0, 150, 9, false, true, false, "", true, false, false, "", false);
    }


}
