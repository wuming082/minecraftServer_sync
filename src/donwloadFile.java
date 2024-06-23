import java.io.File;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.net.URL;
import java.nio.file.*;
import java.util.zip.*;

public class donwloadFile {
    public static boolean donwloadHmcl (File directory) {
        //赋值下载的文件的url
        String fileUrl = status.HMCLUrl; // 下载文件的 URL
        String targetDirectory = directory.toString(); // 指定目录
        System.out.println("目标下载文件地址：" + directory);
        try {//调用下载函数
            downloadFileHmclYD(fileUrl, directory);
        } catch (IOException e) {
            System.err.println("下载HMCL主体文件出错\n请查看网络以及其他问题原因：" + e.getMessage());
            return false;
        }
        return true;
    }
    //下载函数(常用下载HMCL启动器
    static String downloadFileHmclYD(String fileUrl, File targetDirectory) throws IOException {
        URL url = new URL(fileUrl);
        String fileName = url.getFile();
        String destinationPath = targetDirectory + File.separator + new File(fileName).getName();
        Path targetPath = Path.of(destinationPath);
        Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("文件已下载到：" + targetPath);
        //记录下载的文件名
        int cutNum = 0;
        for(int Stringlong = fileUrl.length() - 1 ; Stringlong > 0 ; Stringlong--){
            if(fileUrl.charAt(Stringlong) == '/'){
                cutNum = Stringlong;
                break;
            }
        }
        String zipfilename = fileUrl.substring(cutNum + 1 );
        //将目标文件名称代入方法内
        //进行解压
        unzipfaction.unzipdonwLoad(targetDirectory.toString(),zipfilename);
        //删除zip压缩包
        File file = new File(zipfilename);
        try{
            file.delete();
            System.out.println("HMCL下载完成");
        }catch (Exception e){
            System.out.println("解压出错，请查询原因：" + e.getMessage());;
        }
        return zipfilename;
    }
    //下载通用方法（不删除文件
    private static String downloadFileBaseND(String fileUrl, File targetDirectory) throws IOException {
        URL url = new URL(fileUrl);//赋值下载地址
        String fileName = url.getFile();//赋值下载文件名称
        String destinationPath = targetDirectory + File.separator + new File(fileName).getName();
        Path targetPath = Path.of(destinationPath);
        Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("文件已下载到：" + targetPath);
        //记录下载的文件名
        int cutNum = 0;
        for(int Stringlong = fileUrl.length() - 1 ; Stringlong > 0 ; Stringlong--){
            if(fileUrl.charAt(Stringlong) == '/'){
                cutNum = Stringlong;
                break;
            }
        }
        String filename = fileUrl.substring(cutNum + 1 );
        return filename;
    }
    //下载文件方法
    public static String donwloadfaction(String donwUrl) {//返回下载到的文件的名称
        //赋值下载的文件的url
        String fileUrl = donwUrl; // 下载文件的 URL
        File directory = status.WorkDirectory;//将工作目录赋值（Status成员）
        String targetDirectory = directory.toString(); // 指定目录
        System.out.println("目标下载文件地址：" + directory);
        try {//调用下载函数
            return(downloadFileBaseND(fileUrl, directory));
        } catch (IOException e) {
            System.err.println("下载文件出错\n请查看网络以及其他问题原因：" + e.getMessage());
            return "Error";
        }
    }
    //删除文件通用方法
    public static void DeleFile(String filenameLocation){
        //创建对象
        File delefile = new File(filenameLocation);
        try{
            delefile.delete();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    //下载Mod方法
    public static String donwloadfactionMode(String Modefilename) {//返回下载到的文件的名称
        int donwModeflag = 0;
        for(int i = 0 ; i < status.ModeListInt.size(); i++){
            if(Modefilename.equals(status.ModeListInt.get(i))){
                donwModeflag = i;
            }
        }
        String fileUrl = status.ModeListIntUrl.get(donwModeflag); // 下载文件的 URL
        File directory = status.WorkModeDirectory;//将工作目录赋值（Status成员）
        String targetDirectory = directory.toString(); // 指定目录
        System.out.println("目标下载文件地址：" + directory);
        String ModefilenameNum;
        try {//调用下载函数
            ModefilenameNum = (downloadFileBaseND(fileUrl, directory));
        } catch (IOException e) {
            System.err.println("下载文件出错\n请查看网络以及其他问题原因：" + e.getMessage());
            return "Error";
        }
        //修改文件名称
        ChageName.ModeChage(ModefilenameNum,Modefilename);
        return Modefilename;
    }
}
