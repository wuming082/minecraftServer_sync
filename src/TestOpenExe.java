import java.io.IOException;

public class TestOpenExe {
    public static void main(String[] args) {
        try {
            String exePath = status.WorkDirectory.toString() + "\\" + status.HMCLFileName; // 替换为您的 .exe 文件路径
            ProcessBuilder processBuilder = new ProcessBuilder(exePath);
            processBuilder.start();
            System.out.println("已启动exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
