#version 330 core
out vec4 FragColor;
in vec2 texpos;
uniform vec4 triangleColor;
uniform sampler2D thetexture;
void main() {
    //FragColor = triangleColor;
    FragColor = mix(triangleColor, texture(thetexture, texpos), 0.5f);
}