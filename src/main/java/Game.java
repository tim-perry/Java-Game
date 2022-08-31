import Engine.Camera;
import Engine.Model;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class Game {
    public Camera camera;
    public long glfwWindow;
    public ArrayList<Entity> entities = new ArrayList<>();

    public Game(long glfwWindow) {
        this.camera = new Camera(new Vector3f(0, 0f, 20f), 1f);
        this.glfwWindow = glfwWindow;

        entities.add(new Entity(-1, 0, -1, 1));
        entities.add(new Entity(1, 0, -1,1 ));
        entities.add(new Entity(1, 0, 1,1 ));
        entities.add(new Entity(-1, 0, 1,1 ));

        entities.add(new Entity(0, -10, 0, 100));
        entities.add(new Entity(0, -52, 0, 100));
    }

    public void gameloop() {
        //if (GLFW.glfwGetKey(glfwWindow, GLFW_KEY_W) == GLFW_PRESS)

        float time = (float) GLFW.glfwGetTime();
        float value = ((float)Math.sin(5 * time) + 1) /2;

        camera.setPosition(new Vector3f(20 * (float)Math.cos(0.5 * time), 0, 20 * (float)Math.sin(0.5 * time)));
        camera.setDirection(camera.getPosition().negate());

        for (int i = 0; i < 4; i++) {
            entities.get(i).rotX = time;
            entities.get(i).rotY = time;
            entities.get(i).rotZ = time;
            entities.get(i).posY = value - 0.5f;
        }
    }
}
