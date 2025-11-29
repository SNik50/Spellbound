package com.ombremoon.spellbound.common.magic.acquisition.guides.elements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.ElementPosition;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.ItemListExtras;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.RandomSequence;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public record GuideItemList(List<ItemListEntry> items, ItemListExtras extras, ElementPosition position) implements PageElement {
    public static final MapCodec<GuideItemList> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ItemListEntry.CODEC.listOf().fieldOf("items").forGetter(GuideItemList::items),
            ItemListExtras.CODEC.optionalFieldOf("extras", ItemListExtras.getDefault()).forGetter(GuideItemList::extras),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideItemList::position)
    ).apply(inst, GuideItemList::new));

    @Override
    public void render(GuiGraphics graphics, int leftPos, int topPos, int mouseX, int mouseY, float partialTick) {
        Registry<Item> itemRegistry = Minecraft.getInstance().level.registryAccess().registry(Registries.ITEM).get();
        RandomSource rand = Minecraft.getInstance().level.getRandom();
        rand.setSeed(Math.floorDiv(Minecraft.getInstance().player.tickCount, 10));
        for (int i = 0; i < items.size(); i++) {
            ItemListEntry entry = items.get(i);

            int maxRows = extras.maxRows();
            int xOffset;
            int yOffset;
            if (maxRows <= 0) {
                xOffset = 0;
                yOffset = i * 20;
            } else {
                xOffset = i >= maxRows ? Math.floorDiv(i, maxRows) * extras.columnGap() : 0;
                yOffset = (i >= maxRows ? (i % maxRows) : i) * extras.rowGap();
            }

            ItemStack item = extras.isVisible() ? itemRegistry.get(entry.itemLoc()).getDefaultInstance() : itemRegistry.getRandom(rand).get().value().getDefaultInstance();

            GuideItem.renderItem(graphics, item, leftPos - 10 + position.xOffset() + xOffset, topPos + position.yOffset() - 8 + yOffset, 1f);
            graphics.drawString(Minecraft.getInstance().font,
                    Component.literal(String.valueOf(entry.count)).withStyle(extras.isVisible() ? ChatFormatting.RESET : ChatFormatting.OBFUSCATED),
                    leftPos - 10 + position.xOffset() + xOffset + extras.countGap(),
                    topPos + position.yOffset() + 6 + yOffset,
                    extras.textColour(),
                    extras.dropShadow());
        }
    }

    @Override
    public @NotNull MapCodec<? extends PageElement> codec() {
        return CODEC;
    }


    public record ItemListEntry(ResourceLocation itemLoc, int count) {
        public static final Codec<ItemListEntry> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                ResourceLocation.CODEC.fieldOf("item").forGetter(ItemListEntry::itemLoc),
                Codec.INT.optionalFieldOf("count", 1).forGetter(ItemListEntry::count)
        ).apply(inst, ItemListEntry::new));
    }
}
