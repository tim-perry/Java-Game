package Engine;

import org.lwjgl.glfw.GLFW;

public class Time {
    private static float lastTime;
    private static float time = 0;

    public static void update() {
        if (time == 0) //first time being called
            time = (float)GLFW.glfwGetTime();

        lastTime = time;
        time = (float)GLFW.glfwGetTime();
    }

    public static float getTime() {
        return time;
    }

    public static float delta() {
        return time - lastTime;
    }
}
