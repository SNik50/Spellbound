package com.ombremoon.spellbound.common.magic.acquisition.guides.elements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.ElementPosition;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.ItemListExtras;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record GuideItemListElement(List<ItemListEntry> items, ItemListExtras extras, ElementPosition position) implements IPageElement {
    public static final MapCodec<GuideItemListElement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ItemListEntry.CODEC.listOf().fieldOf("items").forGetter(GuideItemListElement::items),
            ItemListExtras.CODEC.optionalFieldOf("extras", ItemListExtras.getDefault()).forGetter(GuideItemListElement::extras),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideItemListElement::position)
    ).apply(inst, GuideItemListElement::new));

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }


    public record ItemListEntry(List<Ingredient> items, int count) {
        public static final Codec<ItemListEntry> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Ingredient.CODEC.listOf().fieldOf("items").forGetter(ItemListEntry::items),
                Codec.INT.optionalFieldOf("count", 1).forGetter(ItemListEntry::count)
        ).apply(inst, ItemListEntry::new));
    }
}
