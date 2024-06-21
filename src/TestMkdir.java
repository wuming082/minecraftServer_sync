import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class TestMkdir {
    public static void main(String[] args) {
        Path dirPath = Paths.get("D:\\MCMODEDDD");

        try {
            Path createdDir = Files.createDirectory(dirPath);
            System.out.println("创建文件夹成功：" + createdDir);
        } catch (IOException e) {
            System.out.println("创建文件夹失败：" + e.getMessage());
        }

    }
}
