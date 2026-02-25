package com.ombremoon.spellbound.common.magic.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.init.SBMagicEffects;
import com.ombremoon.spellbound.common.world.item.SpellTomeItem;
import com.ombremoon.spellbound.common.world.multiblock.Multiblock;
import com.ombremoon.spellbound.common.world.multiblock.type.TransfigurationMultiblock;
import com.ombremoon.spellbound.common.init.SBSpells;
import com.ombremoon.spellbound.common.magic.acquisition.transfiguration.RitualHelper;
import com.ombremoon.spellbound.common.magic.api.SpellType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public record CreateSpellTome(SpellType<?> spell, int tier) implements MagicEffect {
    public static final MapCodec<CreateSpellTome> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    SBSpells.REGISTRY.byNameCodec().fieldOf("path").forGetter(CreateSpellTome::spell),
                    ExtraCodecs.intRange(1, 3).fieldOf("tier").forGetter(CreateSpellTome::tier)
            ).apply(instance, CreateSpellTome::new)
    );

    @Override
    public void onActivated(ServerLevel level, int tier, LivingEntity caster, BlockPos centerPos, Multiblock.MultiblockPattern pattern) {
        ItemStack spellTome = SpellTomeItem.createWithSpell(this.spell);
        Vec3 pos = centerPos.getBottomCenter();
        RitualHelper.createItem(level, pos, spellTome);
    }

    @Override
    public MagicEffect.Serializer<? extends MagicEffect> getSerializer() {
        return SBMagicEffects.CREATE_SPELL_TOME.get();
    }

    @Override
    public boolean isValid(TransfigurationMultiblock multiblock) {
        return this.tier == multiblock.getRings();
    }

    public static class Serializer implements MagicEffect.Serializer<CreateSpellTome> {
        public static final MapCodec<CreateSpellTome> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        SBSpells.REGISTRY.byNameCodec().fieldOf("spell").forGetter(CreateSpellTome::spell),
                        ExtraCodecs.intRange(1, 3).fieldOf("tier").forGetter(CreateSpellTome::tier)
                ).apply(instance, CreateSpellTome::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, CreateSpellTome> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.registry(SBSpells.SPELL_TYPE_REGISTRY_KEY), CreateSpellTome::spell,
                ByteBufCodecs.VAR_INT, CreateSpellTome::tier,
                CreateSpellTome::new
        );

        @Override
        public MapCodec<CreateSpellTome> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CreateSpellTome> streamCodec() {
            return STREAM_CODEC;
        }
    }

}
