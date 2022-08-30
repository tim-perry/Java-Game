package Engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    private Vector3f position;
    private float rot;
    private float aspect;
    private float fov;
    private boolean perspective;

    public Camera(Vector3f position, float rot, float aspect, boolean perspective, float fov) {
        this.position = position;
        this.rot = rot;
        this.aspect = aspect;
        this.perspective = perspective;
        this.fov = fov;
    }

    public Camera(Vector3f position, float rot, float aspect) {
        this(position, rot, aspect, false, 0);
    }

    public Matrix4f getViewMatrix() {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.translate(this.position).invert();
        return viewMatrix;
    }

    public Matrix4f getProjMatrix() {
        Matrix4f projMatrix = new Matrix4f();
        if (this.perspective == false) return projMatrix; //tried to generate projection matrix for an orthographic camera
        projMatrix.perspective(this.fov, this.aspect, 0.1f, 500f);
        return projMatrix;
    }



    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public float getRot() {
        return rot;
    }

    public void setRot(float rot) {
        this.rot = rot;
    }

    public boolean isPerspective() {
        return perspective;
    }
}
