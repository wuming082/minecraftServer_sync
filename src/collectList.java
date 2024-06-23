import java.net.InetAddress;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.IOException;


//超时检测
class PingExample {
    public static boolean ping(String ipAddress) throws Exception {
        int timeout = 3000; // 超时时间（毫秒）
        return InetAddress.getByName(ipAddress).isReachable(timeout);
    }
}
public class collectList {
    //用于中转（将mod清单以及下载URL清单进行转存）
    public static List<String> ModeListIntUrlTmp = new ArrayList<>();
    public static List<String> ModeListIntTmp = new ArrayList<>();
    //用于记录间隔
    public static int flagMode = 0;
    //网络收集函数
    public static void cllectforIntnet() {
        String DonwFilename =  donwloadFile.donwloadfaction("https://45789i7t61.zicp.vip:31504/down/Okus8NnlxTs9.txt");
        String filePath = status.WorkDirectory.toString() + "\\" + DonwFilename; // 替换为你的文件路径
        System.out.println("清单目录为：" + filePath);
        System.out.println("网络mod清单为：");
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            //打印相关资料
            while ((line = reader.readLine()) != null) {
                if(flagMode == 0){
                    ModeListIntTmp.add(line);
                    flagMode++;
                    //System.out.println(line);
                    }
                else if(flagMode == 1){
                    ModeListIntUrlTmp.add(line);
                    flagMode++;
                    //System.out.println("donwloadUrl:" + line);
                }
                if(line.equals("")){
                    flagMode = 0;
                }
            }
            status.HMCLUrl = ModeListIntUrlTmp.get(0);
            for(int i = 1 ; i < ModeListIntUrlTmp.size() ; i++){
                System.out.println(ModeListIntTmp.get(i) + "\n");
                //进行转移数据
                status.ModeListIntUrl.add(ModeListIntUrlTmp.get(i));
                status.ModeListInt.add(ModeListIntTmp.get(i));
            }
            System.out.println("清单mod数量为：" + status.ModeListIntUrl.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //删除缓存文件
        donwloadFile.DeleFile(DonwFilename);
//        System.out.println(status.ModeListInt);
//        System.out.println(status.ModeListIntUrl);
//        System.out.println("mod总数为：" + status.ModeListInt.size());
    }
    //网络收集方法(先网络测试）
    public static boolean collecIntnet() throws InterruptedException {
        String ipAddress = "www.baidu.com"; // 要 ping 的 IP 地址
        boolean pingBoolean = false;
        try{
            //测试网络连接性
            pingBoolean = PingExample.ping(ipAddress);
        }catch(Exception e){
            System.out.println(e);
        }
        if(pingBoolean){
            System.out.println("网络连接正常\n正在获取更新日志");
//            for(int i = 0 ; i < 10 ; i++){
//                System.out.printf(".");
//                Thread.sleep(500);
//            }
//            System.out.println("");
            //进行网络日志调用
            cllectforIntnet();
            return true;
        }else{
            System.out.println("网络连接失败，请检查连接失败原因");
            return false;
        }
    }
    //本地收集
    public static boolean collecLocation(){
        File desktop = new File(status.WorkModeDirectory.toString());
        try{
            String[] arr = desktop.list();
            for (String fileName : arr) {
                if (fileName.endsWith(".jar")) {
                    status.ModeListLocation.add(fileName);
                }
            }
        }catch(Exception e){
            System.out.println("\n" + e + "\n本地不存在mod目录！\n正在修复！");
            //创建文件夹
            DirectoryMkdir.modeMkdir();
        }
        return true;
    }
    public static void fix() {

    }
}
