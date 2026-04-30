package com.ombremoon.spellbound.client.photon;

import com.lowdragmc.photon.client.fx.FXHelper;
import com.ombremoon.spellbound.common.init.SBData;
import com.ombremoon.spellbound.common.magic.api.ImbuementSpell;
import com.ombremoon.spellbound.common.magic.api.SpellType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

public class ImbuementFXManager {
    private static final Map<Key, ImbuementFX> ACTIVE = new HashMap<>();

    public static void clientTick() {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level == null || mc.isPaused()) {
            stopAll();
            return;
        }

        for (Entity entity : level.entitiesForRendering()) {
            if (entity instanceof LivingEntity living) {
                visitHand(living, InteractionHand.MAIN_HAND);
                visitHand(living, InteractionHand.OFF_HAND);
            } else if (entity instanceof ItemEntity itemEntity) {
                visitItemEntity(itemEntity);
            }
        }

        Iterator<Map.Entry<Key, ImbuementFX>> it = ACTIVE.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Key, ImbuementFX> entry = it.next();
            ImbuementFX fx = entry.getValue();
            if (!fx.isValid()) {
                fx.stop();
                it.remove();
            }
        }
    }

    public static void stopAll() {
        for (ImbuementFX fx : ACTIVE.values()) {
            fx.stop();
        }
        ACTIVE.clear();
    }

    private static ResourceLocation resolveFXLocation(ItemStack stack) {
        if (stack.isEmpty()) return null;
        ResourceLocation override = stack.get(SBData.IMBUEMENT_FX_OVERRIDE.get());
        if (override != null) return override;
        SpellType<?> imbuement = stack.get(SBData.IMBUEMENT.get());
        if (imbuement == null) return null;
        var instance = imbuement.createSpell();
        return instance instanceof ImbuementSpell s ? s.getImbuementFX() : null;
    }

    private static void visitHand(LivingEntity living, InteractionHand hand) {
        ItemStack stack = living.getItemInHand(hand);
        ResourceLocation fxLoc = resolveFXLocation(stack);
        Key key = Key.living(living.getId(), hand);

        if (fxLoc == null) return;
        ImbuementFX existing = ACTIVE.get(key);
        if (existing != null && Objects.equals(existing.fx.getFxLocation(), fxLoc)) return;
        if (existing != null) {
            existing.stop();
            ACTIVE.remove(key);
        }

        int entityId = living.getId();
        Function<Float, Vec3> positionSupplier = partialTicks -> heldItemPosition(entityId, hand, partialTicks);
        Function<Float, Quaternionf> rotationSupplier = partialTicks -> {
            HeldItemTransformCapture.Captured c = HeldItemTransformCapture.get(entityId, hand);
            return c == null ? null : c.rotation();
        };
        BooleanSupplier validitySupplier = () -> {
            Entity e = Minecraft.getInstance().level == null ? null : Minecraft.getInstance().level.getEntity(entityId);
            if (!(e instanceof LivingEntity le) || !le.isAlive()) return false;
            ResourceLocation cur = resolveFXLocation(le.getItemInHand(hand));
            return Objects.equals(cur, fxLoc);
        };
        startFX(fxLoc, living.level(), positionSupplier, rotationSupplier, validitySupplier, key);
    }

    private static void visitItemEntity(ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();
        ResourceLocation fxLoc = resolveFXLocation(stack);
        Key key = Key.item(itemEntity.getId());

        if (fxLoc == null) return;
        ImbuementFX existing = ACTIVE.get(key);
        if (existing != null && Objects.equals(existing.fx.getFxLocation(), fxLoc)) return;
        if (existing != null) {
            existing.stop();
            ACTIVE.remove(key);
        }

        int entityId = itemEntity.getId();
        Function<Float, Vec3> positionSupplier = partialTicks -> {
            Entity e = Minecraft.getInstance().level == null ? null : Minecraft.getInstance().level.getEntity(entityId);
            return e == null ? itemEntity.position() : e.getPosition(partialTicks).add(0, 0.25, 0);
        };
        BooleanSupplier validitySupplier = () -> {
            Entity e = Minecraft.getInstance().level == null ? null : Minecraft.getInstance().level.getEntity(entityId);
            if (!(e instanceof ItemEntity ie) || !ie.isAlive()) return false;
            ResourceLocation cur = resolveFXLocation(ie.getItem());
            return Objects.equals(cur, fxLoc);
        };
        startFX(fxLoc, itemEntity.level(), positionSupplier, null, validitySupplier, key);
    }

    private static void startFX(ResourceLocation fxLoc, Level level,
                                Function<Float, Vec3> positionSupplier,
                                Function<Float, Quaternionf> rotationSupplier,
                                BooleanSupplier validitySupplier, Key key) {
        var fx = FXHelper.getFX(fxLoc);
        if (fx == null) return;
        ImbuementFX effect = new ImbuementFX(fx, level, positionSupplier, rotationSupplier, validitySupplier);
        effect.start();
        ACTIVE.put(key, effect);
    }

    private static Vec3 heldItemPosition(int entityId, InteractionHand hand, float partialTicks) {
        HeldItemTransformCapture.Captured captured = HeldItemTransformCapture.get(entityId, hand);
        if (captured != null) return captured.worldPos();

        Entity e = Minecraft.getInstance().level == null ? null : Minecraft.getInstance().level.getEntity(entityId);
        if (!(e instanceof LivingEntity living)) return Vec3.ZERO;
        return fallbackHeldItemPosition(living, hand, partialTicks);
    }

    private static Vec3 fallbackHeldItemPosition(LivingEntity living, InteractionHand hand, float partialTicks) {
        Vec3 base = living.getPosition(partialTicks);
        return base.add(0, living.getEyeHeight() - 0.3, 0);
    }

    private record Key(int kind, int entityId, int hand) {
        static Key living(int id, InteractionHand h) { return new Key(0, id, h.ordinal()); }
        static Key item(int id) { return new Key(1, id, 0); }
    }
}
