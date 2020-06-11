#version 330 core

// Access the positions on the location 0
layout(location = 0) in vec3 position;

// Access the colors on the location 1, where the cbo is
layout(location = 1) in vec3 color;

// Using it to pass from shader to shader
out vec3 passColor;

void main(void){
    // Give the position
    // gl_position is a vec4, so we convert the position
    gl_Position = vec4(position, 1.0);

    // Pass the position for using different colors
    // pasColor = position;

    // Pass the colors from the ibo
    passColor=color;
}