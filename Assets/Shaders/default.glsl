#type vertex

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec4 aColor;
layout (location = 2) in vec2 aTexCords;
layout (location = 3) in float aTexID;


uniform mat4 uProjMatix;
uniform mat4 uView;

out vec4 fColor;
out vec2 fTextCords;
out float fTextID;


void main(){
    fColor = aColor;
    fTextCords = aTexCords;
    fTextID = aTexID;

    gl_Position = uProjMatix * uView * vec4(aPos, 1.0);
}

#type fragment


in vec4 fColor;
in vec2 fTextCords;
in float fTextID;

uniform sampler2D uTextures[8];

out vec4 color;

void main()
{
    if(fTextID > 0){
        int id = int(fTextID);
        color = fColor * texture(uTextures[id],fTextCords);
    }else {
        color = fColor;
    }

}