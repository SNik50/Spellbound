#version 330

#moj_import <fog.glsl>

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform float Power;
uniform vec4 HDRColor;

in float vertexDistance;
in vec2 texCoord0;
in vec4 vertexColor;
in vec3 ViewDir;
in vec3 ViewNormal;

out vec4 fragColor;

void main() {
    float fresnelValue = pow(clamp(dot(ViewDir, ViewNormal), 0.0, 1.0), Power)
    vec4 color = linear_fog(vertexColor * ColorModulator, vertexDistance, FogStart, FogEnd, FogColor);
    color.xyz = HDRColor.rgb * HDRColor.a;
    fragColor = vec4(color.xyz, fresnelValue);
}