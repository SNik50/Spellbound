package com.ombremoon.spellbound.common.magic.api.events;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.Nullable;

public class UseItemEvent extends SpellEvent {
    private final PlayerInteractEvent.RightClickItem event;
    private final InteractionHand hand;
    private final BlockPos pos;
    @Nullable
    private final Direction face;

    public UseItemEvent(LivingEntity caster, PlayerInteractEvent.RightClickItem event) {
        super(caster, event);
        this.event = event;
        this.hand = Preconditions.checkNotNull(event.getHand(), "Null hand in PlayerInteractEvent!");
        this.pos = Preconditions.checkNotNull(event.getPos(), "Null position in PlayerInteractEvent!");
        this.face = event.getFace();
    }

    public InteractionHand getHand() {
        return this.hand;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public @Nullable Direction getFace() {
        return this.face;
    }

    public InteractionResult getCancellationResult() {
        return this.event.getCancellationResult();
    }

    public void setCancellationResult(InteractionResult result) {
        this.event.setCancellationResult(result);
    }
}
