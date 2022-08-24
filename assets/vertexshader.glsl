#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 atexpos;

uniform mat4 transform;

out vec2 texpos;

void main() {
    gl_Position = transform * vec4(aPos, 1.0f);
    texpos = atexpos;
}