import Engine.Model;
import Engine.Renderer;
import org.joml.Matrix4f;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private int width;
    private int height;
    private String title;
    private long glfwWindow;

    private static Window window = null;

    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "The Game";
    }

    static public Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run() {
        //Create window
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindow = GLFW.glfwCreateWindow(this.width, this.height, this.title, NULL, NULL); //fullscreen (set 4th argument to NULL for window)
        if (glfwWindow == NULL) {
            throw new RuntimeException("Failed to create window");
        }

        //Window Position
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(glfwWindow, vidMode.width() - this.width, 100);

        //Initialization
        glfwMakeContextCurrent(glfwWindow);
        glfwSwapInterval(0);
        glfwShowWindow(glfwWindow);
        GL.createCapabilities();
        glViewport(0,0, this.width, this.height);

        //input
        //glfwSetInputMode(glfwWindow, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        //glfwSetInputMode(glfwWindow, GLFW_RAW_MOUSE_MOTION, GLFW_TRUE);

        Renderer.setup();
        Game game = new Game(glfwWindow);

        //loop
        while(!glfwWindowShouldClose(glfwWindow)) {
            GLFW.glfwPollEvents();
            game.gameloop();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            //for each entity in game.entityarray
            for (Entity i :game.entities) {
                Renderer.render(game.camera, i.model, i.getMatrix());
            }


            glfwSwapBuffers(glfwWindow);
        }

        //Destroy window
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);
    }
}
