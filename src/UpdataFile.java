import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UpdataFile {
    public static String UpdataFIle = donwloadFile.donwloadfaction("https://baoding.dreamsky0822.asia:31504/down/Vx4TqhRHJriz.txt");
    public static String versionUP;
    public static String UpdataFileUrl;
    public static boolean Upoption = false;
    public static boolean CheckUpdataInt (){
        //向网站索取更新版本
        String filePath = status.WorkDirectory.toString() + "\\" + UpdataFIle; //下载更新日志
        //开始进行文本读取
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            int countLine = 0;
            while ((line = reader.readLine()) != null) {
                if(countLine == 0) {
                    versionUP = line;
                }
                if(countLine == 1){
                    UpdataFileUrl = line;
                }
                countLine++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        //System.out.println("版本下载Url:" + UpdataFileUrl);
        donwloadFile.DeleFile(UpdataFIle);
        //版本比较
        if(!status.version.equals(versionUP)){
            System.out.println("本地客户端版本：" + status.version + "\n服务器要求版本为：" + versionUP);
            System.out.println("版本不同，进行更新");
            try{
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Upoption = true;
        }
        if(Upoption){
            //进行更新
            try{
                Updataapplication();
            }catch (Exception e) {
                System.out.println(e);
            }
        }
        return true;
    }
    public static boolean Updataapplication() throws IOException {
        //创建临时文件夹
        Path dirPath = Paths.get(status.WorkDirectory.toString() + "\\workTransfer");
        try {
            Path createdDir = Files.createDirectory(dirPath);
        }catch (IOException e){
            System.out.println(e);
        }
        //创建启动目录txt
        DirectoryMkdir.modeMkdirFileTxt("workTransfer\\config.txt");
        //写入状态
        try {
            FileWriter writer = new FileWriter(status.WorkDirectory + "\\workTransfer\\config.txt");
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write("UpdataHistoryStatus:");
            bw.write(" true\n");
            bw.write("Delete: ");
            bw.write("MClaunch-" + status.version.substring(1) + ".exe");
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将安装包zip下载
        File fileWork = new File(status.WorkDirectory.toString());
        String fileName = donwloadFile.downloadFileHmclYD(UpdataFileUrl,fileWork);
        donwloadFile.DeleFile(fileWork + "\\" + fileName);
        //打开文件
        OpenFileExe.OpenHmcl("MClaunch-" + versionUP.substring(1) + ".exe");
        status.Updataoption =true;
        return true;
    }
}
