package com.ombremoon.spellbound.client.gui.toasts;

import com.ombremoon.spellbound.common.magic.SpellPath;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public enum SpellboundToasts {
    DECEPTION("deception_scrap_toast", SpellPath.DECEPTION),
    DIVINE("divine_scrap_toast", SpellPath.DIVINE),
    RUIN("ruin_scrap_toast", SpellPath.RUIN),
    SUMMON("summon_scrap_toast", SpellPath.SUMMONS),
    TRANSFIG("transfig_scrap_toast", SpellPath.TRANSFIGURATION);

    private final String textureName;
    private final SpellPath path;

    SpellboundToasts(String texture, SpellPath path) {
        this.textureName = texture;
        this.path = path;
    }

    public SpellPath getPath() {
        return this.path;
    }

    public ResourceLocation getTexture() {
        return CommonClass.customLocation("textures/gui/toasts/" + textureName + ".png");
    }
}