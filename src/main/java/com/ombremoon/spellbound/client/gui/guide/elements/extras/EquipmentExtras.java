package com.ombremoon.spellbound.client.gui.guide.elements.extras;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Rotations;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.checkerframework.framework.qual.EnsuresQualifierIf;

public record EquipmentExtras(Rotation standRot, Rotation headRot, Rotation bodyRot, Rotation leftArmRot, Rotation rightArmRot, Rotation leftLegRot, Rotation rightLegRot, float scale) implements IElementExtra {
    public static final Codec<EquipmentExtras> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Rotation.CODEC.optionalFieldOf("stand_rot", Rotation.defaultBodyRot()).forGetter(EquipmentExtras::standRot),
            Rotation.CODEC.optionalFieldOf("head_rot", Rotation.defaultHeadRot()).forGetter(EquipmentExtras::headRot),
            Rotation.CODEC.optionalFieldOf("body_rot", Rotation.defaultBodyRot()).forGetter(EquipmentExtras::bodyRot),
            Rotation.CODEC.optionalFieldOf("left_arm_rot", Rotation.defaultLeftArmRot()).forGetter(EquipmentExtras::leftArmRot),
            Rotation.CODEC.optionalFieldOf("right_arm_rot", Rotation.defaultRightArmRot()).forGetter(EquipmentExtras::rightArmRot),
            Rotation.CODEC.optionalFieldOf("left_leg_rot", Rotation.defaultLeftLegRot()).forGetter(EquipmentExtras::leftLegRot),
            Rotation.CODEC.optionalFieldOf("right_leg_rot", Rotation.defaultRightLegRot()).forGetter(EquipmentExtras::rightLegRot),
            Codec.FLOAT.optionalFieldOf("scale", 25f).forGetter(EquipmentExtras::scale)
    ).apply(inst, EquipmentExtras::new));

    public static EquipmentExtras getDefault() {
        return new EquipmentExtras(
                Rotation.defaultBodyRot(),
                Rotation.defaultHeadRot(),
                Rotation.defaultBodyRot(),
                Rotation.defaultLeftArmRot(),
                Rotation.defaultRightArmRot(),
                Rotation.defaultLeftLegRot(),
                Rotation.defaultRightLegRot(),
                25f
        );
    }

    public record Rotation(float x, float y, float z) {
        public static final Codec<Rotation> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.FLOAT.optionalFieldOf("x", 0f).forGetter(Rotation::x),
                Codec.FLOAT.optionalFieldOf("y", 0f).forGetter(Rotation::y),
                Codec.FLOAT.optionalFieldOf("z", 0f).forGetter(Rotation::z)
        ).apply(inst, Rotation::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, Rotation> STREAM_CODEC = StreamCodec.of(
                (buf, rot) -> {
                    buf.writeFloat(rot.x());
                    buf.writeFloat(rot.y());
                    buf.writeFloat(rot.z());
                },
                (buf) -> new Rotation(
                        buf.readFloat(),
                        buf.readFloat(),
                        buf.readFloat()
                    )
        );

        public Rotation(float x, float y, float z) {
            this.x = !Float.isInfinite(x) && !Float.isNaN(x) ? x % 360.0F : 0.0F;
            this.y = !Float.isInfinite(y) && !Float.isNaN(y) ? y % 360.0F : 0.0F;
            this.z = !Float.isInfinite(z) && !Float.isNaN(z) ? z % 360.0F : 0.0F;
        }

        public Rotations asVanilla() {
            return new Rotations(x, y, z);
        }

        public static Rotation defaultHeadRot() {
            return new Rotation(0f, 0f, 0f);
        }

        public static Rotation defaultBodyRot() {
            return new Rotation(0f, 0f, 0f);
        }

        public static Rotation defaultLeftArmRot() {
            return new Rotation(-10f, 0f, -10f);
        }

        public static Rotation defaultRightArmRot() {
            return new Rotation(-15f, 0f, 10f);
        }

        public static Rotation defaultLeftLegRot() {
            return new Rotation(-1.0f, 0f, -1f);
        }

        public static Rotation defaultRightLegRot() {
            return new Rotation(1f, 0, 1f);
        }
    }
}
