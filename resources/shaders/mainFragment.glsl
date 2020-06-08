#version 330 core

// Output for the color
out vec4 outColor;

// Passed from mainVertex shader
in vec3 color;

void main(void){
    // Solid color
    // outColor = vec4(1.0, 1.0, 0.0, 1.0);

    // Color it based on the position
    outColor = vec4(color, 1.0);
}