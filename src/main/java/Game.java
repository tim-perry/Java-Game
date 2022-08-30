import Engine.Camera;
import org.joml.Vector3f;

public class Game {
    public Camera camera;
    public long glfwWindow;

    public Game(long glfwWindow) {
        this.camera = new Camera(new Vector3f(0, 0f, 10f), 1f, 1f, true, 0.25f);
        this.glfwWindow = glfwWindow;
    }

    public void gameloop() {
        //if (GLFW.glfwGetKey(glfwWindow, GLFW_KEY_W) == GLFW_PRESS)

    }
}
