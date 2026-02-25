package com.ombremoon.spellbound.common.init;

import com.ombremoon.spellbound.common.magic.effects.DamageEntity;
import com.ombremoon.spellbound.common.magic.effects.MagicEffect;
import com.ombremoon.spellbound.common.magic.effects.CreateItem;
import com.ombremoon.spellbound.common.magic.effects.CreateSpellTome;
import com.ombremoon.spellbound.main.CommonClass;
import com.ombremoon.spellbound.main.Constants;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class SBMagicEffects {
    public static final ResourceKey<Registry<MagicEffect.Serializer<? extends MagicEffect>>> RITUAL_EFFECT_REGISTRY_KEY = ResourceKey.createRegistryKey(CommonClass.customLocation("magic_effect"));
    public static final Registry<MagicEffect.Serializer<? extends MagicEffect>> REGISTRY = new RegistryBuilder<>(RITUAL_EFFECT_REGISTRY_KEY).sync(true).create();
    public static final DeferredRegister<MagicEffect.Serializer<? extends MagicEffect>> RITUAL_EFFECTS = DeferredRegister.create(REGISTRY, Constants.MOD_ID);

    public static final Supplier<MagicEffect.Serializer<CreateItem>> CREATE_ITEM = RITUAL_EFFECTS.register("create_item", CreateItem.Serializer::new);
    public static final Supplier<MagicEffect.Serializer<CreateSpellTome>> CREATE_SPELL_TOME = RITUAL_EFFECTS.register("create_spell_tome", CreateSpellTome.Serializer::new);
    public static final Supplier<MagicEffect.Serializer<DamageEntity>> DAMAGE_ENTITY = RITUAL_EFFECTS.register("damage_entity", DamageEntity.Serializer::new);

    public static void register(IEventBus modEventBus) {
        RITUAL_EFFECTS.register(modEventBus);
    }
}
