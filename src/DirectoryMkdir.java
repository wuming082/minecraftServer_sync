import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryMkdir {
    //创建残缺文件夹
    public static void modeMkdir(){
        Path dirPath = Paths.get(status.WorkDirectory.toString() + "\\.minecraft");
        try {
            Path createdDir = Files.createDirectory(dirPath);
            dirPath = Paths.get(status.WorkDirectory.toString() + "\\.minecraft\\mods");
            try {
                Path createdDird = Files.createDirectory(dirPath);
            }catch (IOException ei){
                System.out.println(ei);
            }
        } catch (IOException e) {
            dirPath = Paths.get(status.WorkDirectory.toString() + "\\.minecraft\\mods");
            try {
                Path createdDird = Files.createDirectory(dirPath);
            }catch (IOException ei){
                System.out.println(ei);
            }
        }
        finally {
            System.out.println("修复成功！");
        }
    }
    //通用创建方法
    public static void modeMkdirFile(String filename){//在总工作区域安装
        Path dirPath = Paths.get(status.WorkDirectory.toString() + "\\" + filename);
        try {
            Path createdDir = Files.createDirectory(dirPath);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    //通用创建txt文本方法
    public static void modeMkdirFileTxt(String filename){//在总工作区域安装
        try {
            File file = new File(status.WorkDirectory + "\\" +filename);
            file.createNewFile();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
