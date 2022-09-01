package Engine;

import Game.Game;
import Game.Entity;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private static Window window = null;
    private long glfwWindow;

    private int width;
    private int height;
    private String title;
    private boolean fullscreen;

    public static void close() {
        GLFW.glfwSetWindowShouldClose(window.glfwWindow, true);
    }

    private Window() {
        //TODO: Read resolution and window/fullscreen from config file else use default and generate one
        this.width = 1920;
        this.height = 1080;
        this.title = "The Engine.Engine.Game";
        this.fullscreen = false;
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
        glfwWindow = GLFW.glfwCreateWindow(this.width, this.height, this.title, fullscreen ? glfwGetPrimaryMonitor() : NULL, NULL);
        if (glfwWindow == NULL) throw new RuntimeException("Failed to create window");

        //Engine.Window Position
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(glfwWindow, vidMode.width() - this.width, 100);

        //OpenGL Initialization
        glfwMakeContextCurrent(glfwWindow);
        glfwSwapInterval(1);
        glfwShowWindow(glfwWindow);
        GL.createCapabilities();
        glViewport(0,0, this.width, this.height);

        //input Initialization
        Input.setGlfwWindow(glfwWindow);
        //glfwSetInputMode(glfwWindow, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        //glfwSetInputMode(glfwWindow, GLFW_RAW_MOUSE_MOTION, GLFW_TRUE);

        //Engine and game initialization
        Renderer.setup();
        Game game = new Game();

        //Engine.Window loop
        while(!glfwWindowShouldClose(glfwWindow)) {
            GLFW.glfwPollEvents();
            Time.update();
            game.gameloop();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
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
