package com.ombremoon.spellbound.common.events.custom;

import com.ombremoon.spellbound.common.magic.SpellPath;
import com.ombremoon.spellbound.common.magic.api.SpellType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public class PathLevelUpEvent extends LivingEvent {
    private final SpellPath path;
    private final int level;

    public PathLevelUpEvent(LivingEntity entity, SpellPath path, int level) {
        super(entity);
        this.path = path;
        this.level = level;
    }

    public SpellPath getPath() {
        return path;
    }

    public int getLevel() {
        return this.level;
    }
}
