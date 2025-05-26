in vec4 vColor;
in vec2 vCoord;
in vec2 vPos;

uniform sampler2D u_texture;
uniform vec3 palette[32];
uniform int attributeTable[17];
uniform int hScroll;
uniform int vScroll;

void main() {
    int index = int(texture2D(u_texture, vCoord).r * 255.0);
    int y = (int(vPos[1] + vScroll) >> 4);
    int attr = ((
        //x pos
        attributeTable[(int(vPos[0] + 16 - hScroll) >> 4) % 17]
        //y pos
        & (3 << (2*y))) >> (2*y));

    //fix sign bit messing with right shift
    if(attr < 0){
        attr = attr + 4;
    }

    gl_FragColor = vec4(palette[index + 4 * attr],1.0);
}
