#version 120

uniform sampler2D DiffuseSampler;

varying vec2 tex;

void main(){
    gl_FragColor = texture2D(DiffuseSampler, tex);
}
