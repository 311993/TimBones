in vec4 vColor;
in vec2 vCoord;
in vec2 vPos;

uniform sampler2D u_texture;
uniform vec3 palette[32];
uniform int attributeTable[15];

void main() {
    int index = int(texture2D(u_texture, vCoord).r * 255.0);
    int x = int(vPos[0]) >> 4;
    int attr = ((
        //y pos
        attributeTable[int(vPos[1]) >> 4]
        //x pos
        & (3 << (2*x))) >> (2*x));

    //fix sign bit messing with right shift
    if(attr < 0){
        attr = attr + 4;
    }

    gl_FragColor = vec4(palette[index + 4 * attr],1.0);
}
