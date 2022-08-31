package Game;

import Engine.Model;
import org.joml.Matrix4f;

public class Entity {
    public Model model;
    public float posX;
    public float posY;
    public float posZ;
    public float rotX;
    public float rotY;
    public float rotZ;
    public float scale;

    public Entity() {
        model = new Model();
        model.giveTexture("assets/texture.png");
        posX = 0;
        posY = 0;
        posZ = 0;
        rotX = 0;
        rotY = 0;
        rotZ = 0;
    }

    public Entity(float x, float y, float z, float scale) {
        this();
        posX = x;
        posY = y;
        posZ = z;
        this.scale = scale;
    }

    public Matrix4f getMatrix() {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(posX, posY, posZ);
        matrix.rotate(rotX, 1f, 0, 0);
        matrix.rotate(rotY, 0f, 1f, 0);
        matrix.rotate(rotZ, 0f, 0f, 1f);
        matrix.scale(scale);
        return matrix;
    }
}
