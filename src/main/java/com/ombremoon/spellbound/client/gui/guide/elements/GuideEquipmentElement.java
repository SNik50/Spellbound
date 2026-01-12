package com.ombremoon.spellbound.client.gui.guide.elements;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.client.gui.guide.elements.extras.ElementPosition;
import com.ombremoon.spellbound.client.gui.guide.elements.extras.EquipmentExtras;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record GuideEquipmentElement(Optional<ItemStack> helmet, Optional<ItemStack> chestplate, Optional<ItemStack> leggings, Optional<ItemStack> boots, Optional<ItemStack> offHand, Optional<ItemStack> mainHand, ElementPosition position, EquipmentExtras extras) implements IPageElement {
    public static final MapCodec<GuideEquipmentElement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ItemStack.STRICT_CODEC.optionalFieldOf("helmet").forGetter(GuideEquipmentElement::helmet),
            ItemStack.STRICT_CODEC.optionalFieldOf("chestplate").forGetter(GuideEquipmentElement::chestplate),
            ItemStack.STRICT_CODEC.optionalFieldOf("leggings").forGetter(GuideEquipmentElement::leggings),
            ItemStack.STRICT_CODEC.optionalFieldOf("boots").forGetter(GuideEquipmentElement::boots),
            ItemStack.STRICT_CODEC.optionalFieldOf("off_hand").forGetter(GuideEquipmentElement::offHand),
            ItemStack.STRICT_CODEC.optionalFieldOf("main_hand").forGetter(GuideEquipmentElement::mainHand),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideEquipmentElement::position),
            EquipmentExtras.CODEC.optionalFieldOf("extras", EquipmentExtras.getDefault()).forGetter(GuideEquipmentElement::extras)
    ).apply(inst, GuideEquipmentElement::new));

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }
}
