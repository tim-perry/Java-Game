import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;

public class Main {
    public static void main(String[] args) {
        //Print versions
        System.out.println("LWJGL " + Version.getVersion());
        System.out.println("GLFW " + glfwGetVersionString());

        //Initialize GLFW
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        //Start window
        Window window = Window.get();
        window.run();

        //Terminate GLFW
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
