import java.io.IOException;

public class OpenFileExe {
    public static void OpenHmcl (String HmclFileName){
        try {
            String exePath = status.WorkDirectory.toString() + "\\" + HmclFileName; // 替换为您的 .exe 文件路径
            ProcessBuilder processBuilder = new ProcessBuilder(exePath);
            processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
