package utils;

public class Terminal {
    public static void executeWindows(String command) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", command);
        pb.start();
    }

    public static void executeMac(String command) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
        pb.start();
    }
    
    public static void openVSCode(String path)  throws Exception {
        if (OSValidator.isWindows()) {
            executeWindows("code " + path);
            return;
        }

        if (OSValidator.isMac()) {
            executeMac("code " + path);
            return;
        }

    }
    
    public static void openFolder(String path)  throws Exception {
        if (OSValidator.isWindows()) {
            executeWindows("start " + path);
            return;
        }

        if (OSValidator.isMac()) {
            executeMac("open " + path);
            return;
        }
    }
}
