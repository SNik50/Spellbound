package com.ombremoon.spellbound.mixin;

import com.ombremoon.spellbound.util.MerchantAccessor;
import net.minecraft.world.inventory.MerchantMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(MerchantMenu.class)
public abstract class MerchantMenuMixin implements MerchantAccessor {

    @Unique
    private boolean spellbound$isBroker;

    @Unique
    private int spellbound$merchantId;

    @Override
    public boolean spellbound$isBroker() {
        return spellbound$isBroker;
    }

    @Override
    public void spellbound$setBroker(boolean isBroker) {
        this.spellbound$isBroker = isBroker;
    }

    @Override
    public int spellbound$getMerchantId() {
        return spellbound$merchantId;
    }

    @Override
    public void spellbound$setMerchantId(int id) {
        this.spellbound$merchantId = id;
    }
}
