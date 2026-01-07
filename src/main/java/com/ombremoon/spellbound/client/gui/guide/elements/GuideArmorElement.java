package com.ombremoon.spellbound.client.gui.guide.elements;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.client.gui.guide.elements.extras.ElementPosition;
import com.ombremoon.spellbound.client.gui.guide.elements.extras.EquipmentExtras;
import com.ombremoon.spellbound.client.gui.guide.renderers.IPageElementRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public record GuideArmorElement(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots, ItemStack offHand, ItemStack mainHand, ElementPosition position, EquipmentExtras extras) implements IPageElement {
    public static final MapCodec<GuideArmorElement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ItemStack.OPTIONAL_CODEC.optionalFieldOf("helmet", ItemStack.EMPTY).forGetter(GuideArmorElement::helmet),
            ItemStack.OPTIONAL_CODEC.optionalFieldOf("chestplate", ItemStack.EMPTY).forGetter(GuideArmorElement::chestplate),
            ItemStack.OPTIONAL_CODEC.optionalFieldOf("leggings", ItemStack.EMPTY).forGetter(GuideArmorElement::leggings),
            ItemStack.OPTIONAL_CODEC.optionalFieldOf("boots", ItemStack.EMPTY).forGetter(GuideArmorElement::boots),
            ItemStack.OPTIONAL_CODEC.optionalFieldOf("off_hand", ItemStack.EMPTY).forGetter(GuideArmorElement::offHand),
            ItemStack.OPTIONAL_CODEC.optionalFieldOf("main_hand", ItemStack.EMPTY).forGetter(GuideArmorElement::mainHand),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideArmorElement::position),
            EquipmentExtras.CODEC.optionalFieldOf("extras", EquipmentExtras.getDefault()).forGetter(GuideArmorElement::extras)
    ).apply(inst, GuideArmorElement::new));

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }
}
