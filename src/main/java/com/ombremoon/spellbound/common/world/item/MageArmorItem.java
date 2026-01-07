package com.ombremoon.spellbound.common.world.item;

import com.ombremoon.spellbound.client.renderer.types.MageArmorRenderer;
import com.ombremoon.spellbound.common.init.SBArmorMaterials;
import com.ombremoon.spellbound.common.init.SBAttributes;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MageArmorItem extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected ItemAttributeModifiers attributeModifiers;
    private static final Map<EquipmentSlot, Double> SPELL_RESISTANCES = new HashMap<>();

    public static void armorAttributeInit() {
        SPELL_RESISTANCES.put(EquipmentSlot.HEAD, 2.0);
        SPELL_RESISTANCES.put(EquipmentSlot.CHEST, 2.0);
        SPELL_RESISTANCES.put(EquipmentSlot.LEGS, 2.0);
        SPELL_RESISTANCES.put(EquipmentSlot.FEET, 2.0);
        SPELL_RESISTANCES.put(EquipmentSlot.BODY, 2.0);
    }

    public MageArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
        buildModifiers();
    }

    protected void buildModifiers() {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        
        int i = ((ArmorMaterial)material.value()).getDefense(type);
        float f = ((ArmorMaterial)material.value()).toughness();
        EquipmentSlotGroup equipmentslotgroup = EquipmentSlotGroup.bySlot(type.getSlot());
        ResourceLocation resourcelocation = ResourceLocation.withDefaultNamespace("armor." + type.getName());
        builder.add(Attributes.ARMOR, new AttributeModifier(resourcelocation, (double)i, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup);
        builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(resourcelocation, (double)f, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup);
        float f1 = ((ArmorMaterial)material.value()).knockbackResistance();
        if (f1 > 0.0F) {
            builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(resourcelocation, (double)f1, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup);
        }

        if (this.material.is(SBArmorMaterials.CRYOMANCER)) builder.add(
                SBAttributes.FROST_SPELL_RESIST, modifier(), EquipmentSlotGroup.bySlot(type.getSlot())
        );
        else if (this.material.is(SBArmorMaterials.PYROMANCER)) builder.add(
                SBAttributes.FIRE_SPELL_RESIST, modifier(), EquipmentSlotGroup.bySlot(type.getSlot())
        );
        else if (this.material.is(SBArmorMaterials.STORMWEAVER)) builder.add(
                SBAttributes.SHOCK_SPELL_RESIST, modifier(), EquipmentSlotGroup.bySlot(type.getSlot())
        );
        else if (this.material.is(SBArmorMaterials.TRANSFIGURER)) builder.add(
                SBAttributes.CAST_RANGE, modifier(0.5), EquipmentSlotGroup.bySlot(type.getSlot())
        );

        this.attributeModifiers = builder.build();
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        return this.attributeModifiers;
    }

    private AttributeModifier modifier(double amount) {
        return new AttributeModifier(CommonClass.customLocation("armor." + this.type.getName()), amount, AttributeModifier.Operation.ADD_VALUE);
    }

    private AttributeModifier modifier() {
        return modifier(SPELL_RESISTANCES.get(this.type.getSlot()));
    }


    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @Nullable <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@Nullable T livingEntity, ItemStack itemStack, @Nullable EquipmentSlot equipmentSlot, @Nullable HumanoidModel<T> original) {
                if (this.renderer == null)
                    this.renderer = new MageArmorRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
