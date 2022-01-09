package com.gildedgames.aether.core.util.math;

import com.mojang.serialization.Codec;
import net.minecraft.Util;

import java.util.List;

public record Matrix3x3(
        float a00, float a01, float a02,
        float a10, float a11, float a12,
        float a20, float a21, float a22
) {
    public static final Codec<Matrix3x3> CODEC = Codec.FLOAT.listOf().comapFlatMap(
            list -> Util.fixedSize(list, 9).map(floats -> new Matrix3x3(floats.get(0), floats.get(1), floats.get(2), floats.get(3), floats.get(4), floats.get(5), floats.get(6), floats.get(7), floats.get(8))),
            Matrix3x3::values
    ).stable();

    public static Matrix3x3 identity() {
        return new Matrix3x3(
                1, 0, 0,
                0,  1, 0,
                0, 0, 1
        );
    }

    public static Matrix3x3 identityScaled(float scalar) {
        return new Matrix3x3(
                scalar, 0, 0,
                0,  scalar, 0,
                0, 0, scalar
        );
    }

    // Multiplies vector horizontally across the top matrix row for new Vector X coordinate
    public float multiplyXRow(float x, float y, float z) {
        return x * this.a00 + y * this.a01 + z * this.a02;
    }

    // Multiplies vector horizontally across the middle matrix row for new Vector Y coordinate
    public float multiplyYRow(float x, float y, float z) {
        return x * this.a10 + y * this.a11 + z * this.a12;
    }

    // Multiplies vector horizontally across the bottom matrix row for new Vector Z coordinate
    public float multiplyZRow(float x, float y, float z) {
        return x * this.a20 + y * this.a21 + z * this.a22;
    }

    public Matrix3x3 scale(float scalar) {
        return this.scale(scalar, scalar, scalar, scalar, scalar, scalar, scalar, scalar, scalar);
    }

    public Matrix3x3 scale(float s00, float s01, float s02, float s10, float s11, float s12, float s20, float s21, float s22) {
        return new Matrix3x3(
                this.a00 * s00, this.a01 * s01, this.a02 * s02,
                this.a10 * s10, this.a11 * s11, this.a12 * s12,
                this.a20 * s20, this.a21 * s21, this.a22 * s22
        );
    }

    private Matrix3x3 multiply(float b00, float b01, float b02, float b10, float b11, float b12, float b20, float b21, float b22) {
        return new Matrix3x3(
                this.multiplyXRow(b00, b10, b20), this.multiplyXRow(b01, b11, b21), this.multiplyXRow(b02, b12, b22),
                this.multiplyYRow(b00, b10, b20), this.multiplyYRow(b01, b11, b21), this.multiplyYRow(b02, b12, b22),
                this.multiplyZRow(b00, b10, b20), this.multiplyZRow(b01, b11, b21), this.multiplyZRow(b02, b12, b22)
        );
    }

    public Matrix3x3 add(float s00, float s01, float s02, float s10, float s11, float s12, float s20, float s21, float s22) {
        return new Matrix3x3(
                this.a00 + s00, this.a01 + s01, this.a02 + s02,
                this.a10 + s10, this.a11 + s11, this.a12 + s12,
                this.a20 + s20, this.a21 + s21, this.a22 + s22
        );
    }

    private List<Float> values() {
        return List.of(this.a00, this.a01, this.a02, this.a10, this.a11, this.a12, this.a20, this.a21, this.a22);
    }
}
