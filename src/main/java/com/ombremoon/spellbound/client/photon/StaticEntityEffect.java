package com.ombremoon.spellbound.client.photon;

import com.lowdragmc.photon.client.fx.EntityEffectExecutor;
import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.gameobject.IFXObject;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.joml.Math;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.Objects;

public class StaticEntityEffect extends EntityEffectExecutor {
    public StaticEntityEffect(FX fx, Level level, Entity entity, AutoRotate autoRotate) {
        super(fx, level, entity, autoRotate);
    }

    @Override
    public void updateFXObjectFrame(IFXObject fxObject, float partialTicks) {}

    private void applyRotation(Entity entity, AutoRotate autoRotate, IFXObject fxObject) {
        if (this.runtime != null && fxObject == this.runtime.root) {
            if (autoRotate != AutoRotate.NONE) {
                switch (autoRotate) {
                    case FORWARD -> {
                        var forward = entity.getForward();
                        var newRotation = new Quaternionf(rotation).rotateXYZ(
                                0,
                                (float) org.joml.Math.atan2(-forward.z, forward.x),
                                (float) forward.y
                        );
                        runtime.root.updateRotation(newRotation);
                    }
                    case LOOK -> {
                        var lookAngles = entity.getLookAngle();
                        var newRotation = new Quaternionf(rotation).rotateXYZ(
                                0,
                                (float) org.joml.Math.atan2(-lookAngles.z, lookAngles.x),
                                (float) lookAngles.y
                        );
                        runtime.root.updateRotation(newRotation);
                    }
                    case XROT -> {
                        var newRotation = new Quaternionf(rotation).rotateXYZ(
                                0,
                                Math.toRadians(-90 - entity.getVisualRotationYInDegrees()),
                                0
                        );
                        runtime.root.updateRotation(newRotation);
                    }
                }
            }
        }
    }

    @Override
    public void start() {
        if (!entity.isAlive()) return;

        var effects = CACHE.computeIfAbsent(entity, p -> new ArrayList<>());
        if (!allowMulti) {
            var iter = effects.iterator();
            while (iter.hasNext()) {
                var effect = iter.next();
                boolean removed = false;
                if (effect.getRuntime() != null && !effect.getRuntime().isAlive()) {
                    iter.remove();
                    removed = true;
                }
                if ((effect.fx.equals(fx) || Objects.equals(effect.fx.getFxLocation(), fx.getFxLocation())) && !removed) {
                    return;
                }
            }
        }

        this.runtime = fx.createRuntime();
        var root = this.runtime.getRoot();
        root.updatePos(entity.getEyePosition().toVector3f().add(offset.x, offset.y, offset.z));
        this.applyRotation(entity, autoRotate, root);
        root.updateScale(scale);
        this.runtime.emmit(this, delay);
        effects.add(this);
    }
}
