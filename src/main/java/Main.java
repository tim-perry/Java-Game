import org.lwjgl.Version;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello LWJGL " + Version.getVersion());
        Window window = Window.get();
        window.run();
    }
}
