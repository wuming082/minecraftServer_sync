//dreamsky著作权
//支持MIT开源协议
//wangweihe08@gmail.com(反馈邮箱)
//version: 0.9.1

import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class launchMain {
    public static boolean delefileoption = false;
    public static void main(String[] args) {
        System.out.println(status.version);
        String delefile = "";
        //首先查看是否需要删除原程序用作升级
        String filePath = status.WorkDirectory.toString() + "\\" + "workTransfer\\config.txt"; // config目录
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            int Countflag = 0;
            while ((line = reader.readLine()) != null) {
                if (Countflag == 0) {
                    if (line.substring(21).equals("true")) {
                        System.out.println("dele");
                        delefileoption = true;
                    }
                    Countflag++;
                    continue;
                }
                if(Countflag == 1){
                    if(delefileoption){
                        delefile = line.substring(8);
                    }
                }
            }
        } catch (IOException e) {//发现并没有worKTransfer文件夹
            System.out.println("Error: 不存在workTransfer文件夹\n正在创建" );
            System.out.println(e);
            //建立相应文件夹
            DirectoryMkdir.modeMkdirFile("workTransfer");
            DirectoryMkdir.modeMkdirFileTxt("workTransfer\\config.txt");
        }
        if(delefileoption){
            //删除软件
            System.out.println("升级软件");

            System.out.println(delefile);
            //donwloadFile.DeleFile(status.WorkDirectory + "\\delefile");
            File sourceFile = new File(status.WorkDirectory.toString() + "\\" + delefile);
            File targetFile = new File(status.WorkDirectory.toString() + "\\workTransfer\\" + delefile);

            if (sourceFile.renameTo(targetFile)) {
                System.out.println("文件移动成功！");
            } else {
                System.out.println("文件移动失败。");
            }
            donwloadFile.DeleFile(status.WorkDirectory + "\\workTransfer\\" + delefile);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //创建启动目录txt
            //写入状态
            try {
                DirectoryMkdir.modeMkdirFileTxt("workTransfer\\config.txt");
                FileWriter writer = new FileWriter(status.WorkDirectory + "\\workTransfer\\config.txt");
                BufferedWriter bw = new BufferedWriter(writer);
                bw.write("UpdataHistoryStatus:");
                bw.write(" false\n");
                bw.write("Delete: ");
                bw.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        status End = new status();
        try {
            //解除证书认证SSL
            PassSSL.Hmcl();
            //测试联网性能
            collectList.collecIntnet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        UpdataFile.CheckUpdataInt();
        if(status.Updataoption){
            System.exit(0);
        }
        System.out.println("\n正在检索文件完整性");
        collectList.collecLocation();
        //测试目标相对路径
        //String currentDirectory = System.getProperty("user.dir");
        //System.out.println(currentDirectory);
        status.printListMode("in");
        FindCom.FindFileHmcl();
        //对比mod清单修改本地mod数量
        FindCom.FindMods();
        System.out.println("文件检索修复完毕");
        System.out.println("HMCL版本 " + status.HMCLFileNameVer);
        System.out.format("\033[32m%s\033[0m%n","Start Hmcl...");
        OpenFileExe.OpenHmcl(status.HMCLFileName);
    }
}
