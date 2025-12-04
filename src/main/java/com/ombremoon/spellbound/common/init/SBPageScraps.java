package com.ombremoon.spellbound.common.init;

import com.ombremoon.spellbound.common.magic.api.SpellType;
import com.ombremoon.spellbound.main.CommonClass;
import com.ombremoon.spellbound.main.Constants;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SBPageScraps {
    private static final Map<ResourceLocation, ResourceLocation> TEXTURES = new HashMap<>();

    public static final ResourceLocation UNLOCKED_SOLAR_RAY = scrap("solar_ray_unlock", SpellboundToasts.RUIN);

    public static ResourceLocation scrap(String path, SpellboundToasts toast) {
        return registerToast(CommonClass.customLocation(path), toast.getTexture());
    }

    public static ResourceLocation registerToast(ResourceLocation scrap, ResourceLocation texture) {
        TEXTURES.put(scrap, texture);
        return scrap;
    }

    public static ResourceLocation getTexture(ResourceLocation scrap) {
        return TEXTURES.get(scrap);
    }

    public enum SpellboundToasts {
        DECEPTION("deception_scrap_toast"),
        DIVINE("divine_scrap_toast"),
        RUIN("ruin_scrap_toast"),
        SUMMON("summon_scrap_toast"),
        TRANSFIG("transfig_scrap_toast");

        private final String textureName;

        SpellboundToasts(String texture) {
            this.textureName = texture;
        }

        public ResourceLocation getTexture() {
            return CommonClass.customLocation("textures/gui/toasts/" + textureName + ".png");
        }
    }
}
