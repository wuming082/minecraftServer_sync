import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.net.URL;

public class Test {
    public static void main(String[] args) {
        String fileUrl = "https://github.com/HMCL-dev/HMCL/releases/download/v3.5.8.249/HMCL-3.5.8.249.exe"; // 下载文件的 URL
        String targetDirectory = "D:\\MCwebBuild"; // 指定目录

        try {
            downloadFile(fileUrl, targetDirectory);
        } catch (IOException e) {
            System.err.println("下载文件时出现错误：" + e.getMessage());
        }
    }

    static void downloadFile(String fileUrl, String targetDirectory) throws IOException {
        URL url = new URL(fileUrl);
        String fileName = url.getFile();
        String destinationPath = targetDirectory + File.separator + new File(fileName).getName();

        Path targetPath = Path.of(destinationPath);
        Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        System.out.println("文件已下载到：" + targetPath);
    }
}
