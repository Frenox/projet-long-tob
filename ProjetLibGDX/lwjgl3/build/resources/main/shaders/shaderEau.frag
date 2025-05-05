#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform float u_time;
uniform float u_strength;   // force des ondulations
uniform float u_opacity;    // opacité globale
uniform vec2 u_resolution;

void main() {
    vec2 uv = gl_FragCoord.xy / u_resolution.xy;//position du pixel de 0à1
    vec2 ret = uv;
    ret.y = 1.0 - uv.y;

    vec2 coor = v_texCoords;
    coor.y = 1.0- v_texCoords.y;
    //recupère la position du pixel et sa couleur
    vec3 texColor = texture2D(u_texture, coor).rgb;
    vec4 baseColor = vec4(texColor, 1.0);

    //changement
    
    

    
    

    //final
    gl_FragColor = baseColor;
}