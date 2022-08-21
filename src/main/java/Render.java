import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import java.lang.Math;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;

import static org.lwjgl.opengl.GL20C.*;
import static org.lwjgl.opengl.GL30C.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.*;

public class Render {
    private static Shader shader;

    public Render() {
        Render.shader = new Shader("assets/vertexshader.glsl", "assets/fragshader.glsl");
    }

    static void render() {

        float[] vertices = {
                -0.8f, -0.4f, 0.0f,
                0.0f, 0.4f, 0.0f,
                0.8f, -0.4f, 0.0f
        };

        FloatBuffer buffer = memAllocFloat(vertices.length);
        buffer.put(vertices).flip();

        int VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

        int VBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        float[] color = {0.0f, ((float)Math.sin(5 * GLFW.glfwGetTime()) + 1) /2, 0.0f, 1.0f};
        int location = glGetUniformLocation(shader.id, "triangleColor");
        glUniform4fv(location, color);
        Render.shader.use();
        glDrawArrays(GL_TRIANGLES, 0, 3);

        memFree(buffer);
    }
}
