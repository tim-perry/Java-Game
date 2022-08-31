package Engine;

import org.joml.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL20C;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.lang.Math;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20C.*;
import static org.lwjgl.opengl.GL30C.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Renderer {
    private static Shader shader = null;

    public static void setup() {
        Renderer.shader = new Shader("assets/vertexshader.glsl", "assets/fragshader.glsl");
        Renderer.shader.use();
        int texloc = GL20C.glGetUniformLocation(Renderer.shader.getId(), "thetexture");
        glUniform1i(texloc, 0);

        //options
        glEnable(GL_DEPTH_TEST);
        glClearColor(0f, 0f, 0f, 1.0f);
    }
    public static void render(Camera camera, Model model, Matrix4f matrix) {
        glBindVertexArray(model.getVAO_id());
        glBindTexture(GL_TEXTURE_2D, model.getTextureID());
        Renderer.shader.use();

        float time = (float)GLFW.glfwGetTime();
        float value = ((float)Math.sin(5 * time) + 1) /2;

        float[] color = {0.0f, value, 0.0f, value};
        int color_loc = GL20C.glGetUniformLocation(shader.getId(), "triangleColor");
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

        //Clear and render
        //glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glDrawArrays(GL_TRIANGLES, 0, 36);
    }
}