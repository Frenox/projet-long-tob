#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform vec2 u_resolution;
uniform vec2 u_sunLocation;

void main() {
    vec2 uv = gl_FragCoord.xy / u_resolution;//position du pixel de 0à1
    float vrai = 1.0;
    if (uv.y > 0.539){
    vec2 sunloc = u_sunLocation / u_resolution; 
    float dist = distance(uv, sunloc);
    //recupère la position du pixel et sa couleur
    vec4 texColor = texture2D(u_texture, v_texCoords);
    vec4 baseColor = v_color * texColor;

    //changement
    
    float Boost = smoothstep(0.5, 0.0, dist); // max à distance 0, 0 à distance 0.5+

    vec3 lightColor = vec3(1.0, 0.85, 0.7) * Boost * 0.3;

    baseColor.rgb +=  lightColor;
    gl_FragColor = baseColor;
    }else {
        vec2 temp = v_texCoords;
        temp.y = temp.y;
        vec4 texColor = texture2D(u_texture, temp);
        vec4 baseColor = v_color * texColor;
        gl_FragColor = baseColor;
    }
    
    

   
}