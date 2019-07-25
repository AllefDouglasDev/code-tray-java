package utils;

public class Terminal {
    public static void exe(String path) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "code " + path);
        pb.start();
    }
    
    public static void openVSCode(String path)  throws Exception {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "code " + path);
        pb.start();
    }
    
    public static void openFolder(String path)  throws Exception {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "start " + path);
        pb.start();
    }
}
