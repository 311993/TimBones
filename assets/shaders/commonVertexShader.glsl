attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;

uniform mat4 u_projTrans;

out vec4 vColor;
out vec2 vCoord;
out vec2 vPos;

void main() {
    gl_Position = u_projTrans * a_position;
    vColor = a_color;
    vCoord = a_texCoord0;
    vPos = vec2(a_position);
}
