package com.ombremoon.spellbound.common.magic.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.init.SBMagicEffects;
import com.ombremoon.spellbound.common.world.multiblock.Multiblock;
import com.ombremoon.spellbound.common.magic.acquisition.transfiguration.DataComponentStorage;
import com.ombremoon.spellbound.common.magic.acquisition.transfiguration.RitualHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.Optional;

public record CreateItem(Item item, Optional<DataComponentStorage> data) implements MagicEffect {

    public CreateItem(Item item) {
        this(item, Optional.empty());
    }

    public static CreateItem withData(Item item, TypedDataComponent<?>... data) {
        DataComponentStorage storage = new DataComponentStorage(Arrays.asList(data));
        return new CreateItem(item, Optional.of(storage));
    }

    @Override
    public void onActivated(ServerLevel level, int tier, LivingEntity caster, BlockPos centerPos, Multiblock.MultiblockPattern pattern) {
        Vec3 pos = centerPos.getBottomCenter();
        RitualHelper.createItem(level, pos, new ItemStack(item), this.data);
    }

    @Override
    public MagicEffect.Serializer<? extends MagicEffect> getSerializer() {
        return SBMagicEffects.CREATE_ITEM.get();
    }

    public static class Serializer implements MagicEffect.Serializer<CreateItem> {
        public static final MapCodec<CreateItem> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(CreateItem::item),
                        DataComponentStorage.CODEC.optionalFieldOf("data").forGetter(CreateItem::data)
                ).apply(instance, CreateItem::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, CreateItem> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.registry(Registries.ITEM), CreateItem::item,
                ByteBufCodecs.optional(DataComponentStorage.STREAM_CODEC), CreateItem::data,
                CreateItem::new
        );

        @Override
        public MapCodec<CreateItem> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CreateItem> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
