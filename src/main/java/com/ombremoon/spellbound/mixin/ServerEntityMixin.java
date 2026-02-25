package com.ombremoon.spellbound.mixin;

import com.ombremoon.spellbound.common.init.SBData;
import com.ombremoon.spellbound.common.init.SBEffects;
import com.ombremoon.spellbound.common.magic.api.AbstractSpell;
import com.ombremoon.spellbound.networking.clientbound.UpdateCastModePayload;
import com.ombremoon.spellbound.networking.clientbound.UpdateInvisibilityPayload;
import com.ombremoon.spellbound.networking.clientbound.UpdateSpellsPayload;
import com.ombremoon.spellbound.util.SpellUtil;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ServerEntity.class)
public abstract class ServerEntityMixin {

    @Shadow
    @Final
    private Entity entity;

    @Shadow
    protected abstract void broadcastAndSend(Packet<?> packet);

    @Shadow
    private int tickCount;

    @Inject(method = "sendChanges", at = @At("TAIL"))
    private void sendPlayerChanges(CallbackInfo ci) {
        if (this.entity.getData(SBData.INVISIBILITY_DIRTY)) {
            this.entity.setData(SBData.INVISIBILITY_DIRTY, false);
            MobEffectInstance effect = this.entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(SBEffects.MAGI_INVISIBILITY) ? livingEntity.getEffect(SBEffects.MAGI_INVISIBILITY) : null;
            this.broadcastAndSend(new ClientboundCustomPayloadPacket(new UpdateInvisibilityPayload(this.entity.getId(), Optional.ofNullable(effect))));
        }

        if (this.entity instanceof LivingEntity livingEntity) {
            var handler = SpellUtil.getSpellHandler(livingEntity);
            if (handler.isDirty() && this.tickCount % 20 == 0) {
                this.broadcastAndSend(new ClientboundCustomPayloadPacket(new UpdateCastModePayload(livingEntity.getId(), handler.inCastMode())));
                handler.setDirty(false);
            }
        }

        /*if (this.entity instanceof LivingEntity livingEntity) {
            var handler = SpellUtil.getSpellHandler(livingEntity);
            if (handler.isDirty() && this.tickCount % 20 == 0) {
                this.broadcastAndSend(new ClientboundCustomPayloadPacket(new UpdateSpellsPayload(livingEntity.getId(), handler.serializeSpells())));
                handler.setDirty(false);
            }
        }*/
    }
}
