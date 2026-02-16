package com.ombremoon.spellbound.common.world.entity.spell;

import com.ombremoon.spellbound.common.world.entity.SpellProjectile;
import com.ombremoon.spellbound.common.world.spell.ruin.fire.FireballSpell;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class Fireball extends SpellProjectile<FireballSpell> {
    private static final EntityDataAccessor<Integer> SIZE = SynchedEntityData.defineId(Fireball.class, EntityDataSerializers.INT);

    public Fireball(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SIZE, 0);
    }

    public int getSize() {
        return this.entityData.get(SIZE);
    }

    public void setSize(int size) {
        this.entityData.set(SIZE, size);
    }
}
