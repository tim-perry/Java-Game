package Game;

import Engine.Camera;
import Engine.Input;
import Engine.Time;
import Engine.Window;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Game {
    public ArrayList<Entity> entities = new ArrayList<Entity>();
    public Camera camera;
    public long glfwWindow;

    public Game() {
        this.camera = new Camera(new Vector3f(0, 0f, 20f), 16/9f);

        //floating cubes
        entities.add(new Entity(-1, 0, -1, 1));
        entities.add(new Entity(1, 0, -1,1 ));
        entities.add(new Entity(1, 0, 1,1 ));
        entities.add(new Entity(-1, 0, 1,1 ));
        entities.add((new Entity(0, 0, 0, 0.5f)));

        //Stationary cube
        entities.add((new Entity(0, -1.5f, 0, 1)));

        //Big Cubes
        entities.add((new Entity(8, -0.5f, 8, 3)));
        entities.add((new Entity(-8, -0.5f, -8, 3)));

        //Distant cubes
        entities.add((new Entity(40, 3f, -40, 10)));
        entities.add((new Entity(-40, 3f, 40, 10)));

        //Containing cubes
        entities.add(new Entity(0, -10, 0, 100));
        entities.add(new Entity(0, -52, 0, 100));

        //Camera cube
        entities.add(new Entity(0, 0, 20f, 0.3f));
    }

    public void gameloop() {
        if (Input.isPressed(Input.KEY_ESC))
            Window.close();

        entities.get(12).posX = 16 * (float)Math.cos(0.5 * Time.getTime());
        entities.get(12).posY = 1.2f * (float)Math.cos(Time.getTime());
        entities.get(12).posZ = 16 * (float)Math.sin(0.5 * Time.getTime());

        if (Input.isPressed(Input.KEY_W)) {
            Vector3f newpos = camera.getPosition().add(camera.getDirection().mul(Time.delta()));
            camera.setPosition(newpos);
        }
        if (Input.isPressed(Input.KEY_S)) {
            Vector3f newpos = camera.getPosition().add(camera.getDirection().negate().mul(Time.delta()));
            camera.setPosition(newpos);
        }
        if (Input.isPressed(Input.KEY_D)) {
            Vector3f newpos = camera.getPosition().add(camera.getDirection().cross(camera.getUp()).mul(Time.delta()));
            camera.setPosition(newpos);
        }
        if (Input.isPressed(Input.KEY_A)) {
            Vector3f newpos = camera.getPosition().add(camera.getDirection().cross(camera.getUp()).negate().mul(Time.delta()));
            camera.setPosition(newpos);
        }
        if (Input.isPressed(Input.KEY_Q)) {
            Vector3f newDir = camera.getDirection().rotateY(Time.delta() * 1.5f);
            camera.setDirection(newDir);
        }
        if (Input.isPressed(Input.KEY_E)) {
            Vector3f newDir = camera.getDirection().rotateY(-Time.delta() * 1.5f);
            camera.setDirection(newDir);
        }

        if (Input.isPressed(Input.KEY_SPACE)) {
            camera.setPosition(new Vector3f(entities.get(12).posX, entities.get(12).posY, entities.get(12).posZ));
        }

        for (int i = 0; i < 5; i++) {
            entities.get(i).rotX += Time.delta();
            entities.get(i).rotY += Time.delta();
            entities.get(i).rotZ += Time.delta();
            entities.get(i).posY = ((float)Math.sin(5 * Time.getTime()) + 1) /2 - 0.5f;
        }
    }
}
