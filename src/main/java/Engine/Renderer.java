package Engine;

import org.joml.*;
import org.lwjgl.opengl.GL20C;
import java.nio.FloatBuffer;
import static org.lwjgl.opengl.GL20C.*;
import static org.lwjgl.opengl.GL30C.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Renderer {
    private static Shader shader = null;

    public static void setup() {
        shader = new Shader("assets/vertexshader.glsl", "assets/fragshader.glsl");
        shader.use();
        int texloc = GL20C.glGetUniformLocation(Renderer.shader.getId(), "thetexture");
        glUniform1i(texloc, 0);

        //options
        glEnable(GL_DEPTH_TEST);
        glClearColor(0f, 0f, 0f, 1.0f);
    }
    public static void render(Camera camera, Model model, Matrix4f matrix) {
        shader.use();
        glBindVertexArray(model.getVAO_id());
        glBindTexture(GL_TEXTURE_2D, model.getTextureID());

        //uniforms
        //int color_loc = GL20C.glGetUniformLocation(shader.getId(), "triangleColor");
        //glUniform4fv(color_loc, color);

        //model matrix
        FloatBuffer mb = memAllocFloat(16);
        matrix.get(mb);
        int model_loc = GL20C.glGetUniformLocation(shader.getId(), "model");
        glUniformMatrix4fv(model_loc, false, mb);

        //view matrix
        Matrix4f view = camera.getViewMatrix();
        FloatBuffer vb = memAllocFloat(16);
        view.get(vb);
        int view_loc = GL20C.glGetUniformLocation(shader.getId(), "view");
        glUniformMatrix4fv(view_loc, false, vb);

        //projection matrix
        Matrix4f proj = camera.getProjMatrix();
        FloatBuffer pb = memAllocFloat(16);
        proj.get(pb);
        int proj_loc = GL20C.glGetUniformLocation(shader.getId(), "proj");
        glUniformMatrix4fv(proj_loc, false, pb);

        //Render and unbind context
        glDrawArrays(GL_TRIANGLES, 0, model.getTriangleCount());
        glBindTexture(GL_TEXTURE_2D, 0);
        glBindVertexArray(0);
        shader.unbind();
    }
}