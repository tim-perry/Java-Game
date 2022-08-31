package Engine;

import static org.lwjgl.opengl.GL20C.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//TODO: Add error checking for shader compilation
public class Shader {
    private int id;

    public void use() {
        glUseProgram(this.id);
    }
    public void unbind() {glUseProgram(0);}
    public int getId() {
        return this.id;
    }

    public Shader(String vs_source, String fs_source) {
            String source;
            Scanner reader;
            this.id = glCreateProgram();

            int vs_id = glCreateShader(GL_VERTEX_SHADER);
            try {
                reader = new Scanner(new File(vs_source));
                source = "";
                while (reader.hasNextLine()) {
                    source += reader.nextLine() + "\n";
                }
                glShaderSource(vs_id, source);
            } catch (FileNotFoundException e) {
                System.out.println("File not found error");
                e.printStackTrace();
            }
            glCompileShader(vs_id);
            if (glGetShaderi(vs_id, GL_COMPILE_STATUS) == 0)
                System.out.println("failed to compile vertex shader");
            glAttachShader(this.id, vs_id);

            int fs_id = glCreateShader(GL_FRAGMENT_SHADER);
            try {
                reader = new Scanner(new File(fs_source));
                source = "";
                while (reader.hasNextLine()) {
                    source += reader.nextLine() + "\n";
                }
                glShaderSource(fs_id, source);
            } catch (FileNotFoundException e) {
                System.out.println("File not found error");
                e.printStackTrace();
            }
            glCompileShader(fs_id);
            glAttachShader(this.id, fs_id);

            glLinkProgram(this.id);
            glDeleteShader(vs_id);
            glDeleteShader(fs_id);
    }
}
