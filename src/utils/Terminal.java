package utils;

public class Terminal {
    public static void exec(String path) throws Exception{
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "code " + path);
        pb.start();
    }
}
