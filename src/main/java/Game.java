import Engine.Camera;
import Engine.Model;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Game {
    public Camera camera;
    public long glfwWindow;
    public ArrayList<Entity> entities = new ArrayList<>();
    public boolean paused = false;

    public Game(long glfwWindow) {
        this.camera = new Camera(new Vector3f(0, 0f, 20f), 16/9f);
        this.glfwWindow = glfwWindow;

        entities.add(new Entity(-1, 0, -1, 1));
        entities.add(new Entity(1, 0, -1,1 ));
        entities.add(new Entity(1, 0, 1,1 ));
        entities.add(new Entity(-1, 0, 1,1 ));
        entities.add((new Entity(0, 0, 0, 0.5f)));

        entities.add((new Entity(0, -1.5f, 0, 1)));

        entities.add((new Entity(8, -0.5f, 8, 3)));
        entities.add((new Entity(-8, -0.5f, -8, 3)));

        entities.add((new Entity(40, 3f, -40, 10)));
        entities.add((new Entity(-40, 3f, 40, 10)));

        entities.add(new Entity(0, -10, 0, 100));
        entities.add(new Entity(0, -52, 0, 100));
    }

    public void gameloop() {
        if (GLFW.glfwGetKey(glfwWindow, GLFW.GLFW_KEY_ESCAPE) == GLFW_PRESS)
            GLFW.glfwSetWindowShouldClose(glfwWindow, true);


        float time = (float) GLFW.glfwGetTime();
        float value = ((float)Math.sin(5 * time) + 1) /2;

        camera.setPosition(new Vector3f(16 * (float)Math.cos(0.5 * time), 0, 16 * (float)Math.sin(0.5 * time)));
        camera.setDirection(camera.getPosition().negate());

        for (int i = 0; i < 5; i++) {
            entities.get(i).rotX = time;
            entities.get(i).rotY = time;
            entities.get(i).rotZ = time;
            entities.get(i).posY = value - 0.5f;
        }
    }
}
