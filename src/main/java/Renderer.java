import org.joml.*;
import org.lwjgl.glfw.GLFW;
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
    private static FloatBuffer VBO;

    public static void setup() {
        Renderer.shader = new Shader("assets/vertexshader.glsl", "assets/fragshader.glsl");
        Renderer.shader.use();

        int VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

        float vertices[] = {
                -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,
                0.5f, -0.5f, -0.5f,  1.0f, 0.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,

                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
                -0.5f,  0.5f,  0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,

                -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                0.5f, -0.5f, -0.5f,  1.0f, 1.0f,
                0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,

                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f,  0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f
        };

        VBO = memAllocFloat(vertices.length); //needs to be freed?
        VBO.put(vertices).flip();

        int VBO_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO_id);
        glBufferData(GL_ARRAY_BUFFER, VBO, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 5*4, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 5*4, 3*4);
        glEnableVertexAttribArray(1);

        //texture
        STBImage.nstbi_set_flip_vertically_on_load(1);
        glActiveTexture(GL_TEXTURE0);
        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        try (MemoryStack stack = stackPush()) {
            IntBuffer width = stack.callocInt(1);
            IntBuffer height = stack.callocInt(1);
            IntBuffer channels = stack.callocInt(1);
            ByteBuffer data = STBImage.stbi_load("assets/texture.png", width, height, channels, 0);
            if (memAddress(data) == 0) System.out.println("Failed to load image");
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
            glGenerateMipmap(GL_TEXTURE_2D);
            STBImage.stbi_image_free(data);
        }
        Renderer.shader.use();
        int texloc = glGetUniformLocation(Renderer.shader.id, "thetexture");
        glUniform1i(texloc, 0);

        //enable
        glEnable(GL_DEPTH_TEST);
    }
    public static void render() {
        Renderer.shader.use();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        float time = (float)GLFW.glfwGetTime();
        float value = ((float)Math.sin(5 * time) + 1) /2;

        float[] color = {0.0f, value, 0.0f, 1.0f};

        Matrix4f model = new Matrix4f();
        model.rotate(time, 0f, 0f, 1f);
        model.rotate(time, 1f, 0, 0);
        model.rotate(time, 0f, 1f, 0);
        model.translate(0,value - 0.5f,0f);
        FloatBuffer mb = memAllocFloat(16);
        model.get(mb);

        Matrix4f view = new Matrix4f();
        view.translate(0,0, -10f);
        FloatBuffer vb = memAllocFloat(16);
        view.get(vb);

        Matrix4f proj = new Matrix4f();
        proj.perspective(0.25f, 1, 0.1f, 100f);
        FloatBuffer pb = memAllocFloat(16);
        proj.get(pb);

        int color_loc = glGetUniformLocation(shader.id, "triangleColor");
        int model_loc = glGetUniformLocation(shader.id, "model");
        int view_loc = glGetUniformLocation(shader.id, "view");
        int proj_loc = glGetUniformLocation(shader.id, "proj");

        glUniform4fv(color_loc, color);
        glUniformMatrix4fv(model_loc, false, mb);
        glUniformMatrix4fv(view_loc, false, vb);
        glUniformMatrix4fv(proj_loc, false, pb);
        glDrawArrays(GL_TRIANGLES, 0, 36);
    }
}
