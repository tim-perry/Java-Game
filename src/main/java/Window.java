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
        this.width = 1000;
        this.height = 1000;
        this.title = "The Game";
    }

    static public Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    private void loop() {
        while(!glfwWindowShouldClose(glfwWindow)) {
            GLFW.glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);

            Renderer.render();

            glfwSwapBuffers(glfwWindow);
        }
    }

    public void run() {
        //Create window
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindow = GLFW.glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new RuntimeException("Failed to create window");
        }

        //Window Position
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(glfwWindow, vidMode.width() - this.width, 0);

        //Initialization
        glfwMakeContextCurrent(glfwWindow);
        glfwSwapInterval(1);
        glfwShowWindow(glfwWindow);
        GL.createCapabilities();
        glClearColor(0f, 0f, 0f, 1.0f);
        glViewport(0,0, this.width, this.height);
        Renderer.setup();

        loop();

        //Destroy window
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);
    }
}
