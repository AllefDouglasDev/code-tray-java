package utils;

public class Terminal {
    public static void executeWindows(String command) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", command);
        pb.start();
    }
    
    public static void openVSCode(String path)  throws Exception {
    	Terminal.executeWindows("code " + path);
    }
    
    public static void openFolder(String path)  throws Exception {
    	Terminal.executeWindows("start " + path);
    }
}
