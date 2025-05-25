in vec4 vColor;
in vec2 vCoord;
in vec2 vPos;

uniform sampler2D u_texture;
uniform vec3 spritePal[4];

out vec4 color;

void main() {
    int index = int(texture2D(u_texture, vCoord).r * 255.0);

    gl_FragColor = vec4(spritePal[index], index > 0 ? 1.0 : 0.0);
}
