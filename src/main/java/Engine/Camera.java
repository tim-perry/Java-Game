package Engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class Camera {
    private Vector3f position;
    private Vector3f direction;
    private Vector3f up;

    private float aspect;
    private float fov;

    public Camera(Vector3f position, Vector3f direction, Vector3f up, float aspect, float fov) {
        this.position = position;
        this.direction = direction;
        this.up = up;
        this.aspect = aspect;
        this.fov = fov;
    }

    public Camera(Vector3f position, float aspect) {
        this(position, new Vector3f(position).negate(), new Vector3f(0, 1, 0), aspect, 0.50f);
    }

    public Matrix4f getViewMatrix() {
        Matrix4f viewMatrix = new Matrix4f();
        Vector3f centre = new Vector3f(position);
        centre.add(direction);
        viewMatrix.lookAt(position, centre, up);
        //viewMatrix.translate(this.position).invert();
        return viewMatrix;
    }

    public Matrix4f getProjMatrix() {
        Matrix4f projMatrix = new Matrix4f();
        projMatrix.perspective(this.fov, this.aspect, 0.1f, 500f);
        return projMatrix;
    }



    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public Vector3f getPosition() {
        return new Vector3f(position); //return new vector that can be changed without altering private attribute
    }
}
