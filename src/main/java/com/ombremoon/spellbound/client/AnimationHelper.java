package com.ombremoon.spellbound.client;

import com.ombremoon.spellbound.common.magic.api.AbstractSpell;
import com.ombremoon.spellbound.main.CommonClass;
import com.ombremoon.spellbound.networking.PayloadHandler;
import com.ombremoon.spellbound.util.SpellUtil;
import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractFadeModifier;
import com.zigythebird.playeranimcore.animation.layered.modifier.SpeedModifier;
import com.zigythebird.playeranimcore.easing.EasingType;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;

public class AnimationHelper {

    public static void playAnimation(AbstractClientPlayer player, ResourceLocation animationName, float animationSpeed) {
        PlayerAnimationController controller = (PlayerAnimationController) PlayerAnimationAccess.getPlayerAnimationLayer(player, CommonClass.customLocation("spell_cast"));
        if (controller != null) {
            controller.addModifier(new SpeedModifier(animationSpeed), 0);
            controller.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(2, EasingType.EASE_IN_OUT_SINE), animationName, !controller.isPlayingTriggeredAnimation());
        }
    }

    public static void stopAnimation(AbstractClientPlayer player, ResourceLocation animationName) {
        PlayerAnimationController controller = (PlayerAnimationController) PlayerAnimationAccess.getPlayerAnimationLayer(player, CommonClass.customLocation("spell_cast"));
        if (controller == null)
            return;

        if (controller.getCurrentAnimation() != null && controller.getCurrentAnimation().animation().getNameOrId().equals(animationName.getPath())) {
            controller.stopTriggeredAnimation();
        }
    }

    public static void tick(AbstractClientPlayer player) {
        PlayerAnimationController controller = (PlayerAnimationController) PlayerAnimationAccess.getPlayerAnimationLayer(player, CommonClass.customLocation("spell_cast"));
        if (controller != null) {
            if (!controller.isActive() && controller.getModifierCount() > 0) {
                controller.removeAllModifiers();
            } else if (controller.isActive()) {
                player.setYBodyRot(player.getYHeadRot());
                PayloadHandler.updateRotation(player.yBodyRot);
                var handler = SpellUtil.getSpellHandler(player);
                AbstractSpell spell = handler.getCurrentlyCastSpell();

                if (spell != null && spell.isCasting() && spell.isStationaryCast(spell.getCastContext()) && !handler.isChargingOrChannelling())
                    handler.setStationaryTicks(1);
            }
        }
    }
}
