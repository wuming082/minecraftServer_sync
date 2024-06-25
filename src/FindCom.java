import java.io.File;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.net.URL;

public class FindCom {
    public static void FindFileHmcl(){
        //报告当前目录环境
        File currentDirectory =  new File(System.getProperty("user.dir"));
        System.out.println("当前环境目录为：" + currentDirectory);
        System.out.println("mod目录文件夹为：" + status.WorkModeDirectory);
        String regex = "HMCL-\\d+\\.\\d+\\.\\d+(\\.\\d+)?.exe";
        boolean ifQuaFile =  SearchFileHmcl(currentDirectory,regex);
        if(ifQuaFile){
            System.out.println("HMCL主文件已找到");
        }else{
            System.out.println("HMCL主文件不存在！\n正在安装HMCL主体文件");
            String pathString = String.valueOf(currentDirectory);
            System.out.println(pathString);
            Path path = Paths.get(pathString);
            if (!Files.exists(path)) {
                System.out.println("文件或目录不存在！");
            }else{
                donwloadFile.donwloadHmcl(currentDirectory);
            }
        }

    }
    //寻找常规文件
    public static boolean SearchFile(File directory , String fileName){

        File[] files = directory.listFiles();
        if(files != null){
            for(File file : files){
                if (file.isDirectory()) {
                    SearchFile(file, fileName); // 递归搜索子目录
                }else if(file.getName().equals(fileName)){
                    System.out.println("找到目标文件" + file.getAbsolutePath());
                    return true;
                }
            }
        }
        return false;
    }
    //寻找启动器主文件
    public static boolean SearchFileHmcl(File directory , String regex){
        File[] files = directory.listFiles();
        if(files != null){
            Pattern pattern = Pattern.compile(regex);
            for(File file : files){
                //System.out.println("finding:" + file.getAbsolutePath());
                if (file.isDirectory()) {
                    SearchFileHmcl(file, regex); // 递归搜索子目录
                }else {
                    Matcher matcher = pattern.matcher(file.getName());
                    if(matcher.matches()){
                        System.out.println("HMCL主文件地址为：" + file.getAbsolutePath());
                        String HmclfileName = file.getAbsolutePath().toString();
                        //记录下载的文件名
                        int cutNum = 0;
                        for(int Stringlong = HmclfileName.length() - 1 ; Stringlong > 0 ; Stringlong--){
                            if(HmclfileName.charAt(Stringlong) == '\\'){
                                cutNum = Stringlong;
                                break;
                            }
                        }
                        //从文件名寻找版本号
                        String HmclfileCut = HmclfileName.substring(cutNum + 1 );
                        status.HMCLFileName = HmclfileCut;
                        for(int CutStart = 0 ; CutStart < HmclfileCut.length() ; CutStart++){
                            if(HmclfileCut.charAt(CutStart) == '-'){
                                for(int CutEnd = HmclfileCut.length() - 1 ; CutEnd > CutStart ; CutEnd--){
                                    if(HmclfileCut.charAt(CutEnd) == '.'){
                                        status.HMCLFileNameVer = HmclfileCut.substring(CutStart + 1,CutEnd);
                                        //System.out.println("HMCLFileNameVer: " + HmclfileCut.substring(CutStart,CutEnd));
                                        break;
                                    }

                                }
                            }
                        }

                        return true;
                    }
                }
            }
        }
        return false;
    }
    //对比mod文件
    public static void FindMods(){
        //System.out.println("location" + status.ModeListLocation.size() + "\nintnet"+ status.ModeListInt.size());
        int modsflag = 0;
        for(int i = 0 ; i < status.ModeListInt.size(); i++){
            for(int j = 0 ; j < status.ModeListLocation.size() + 1;j++){
                if(j == status.ModeListLocation.size()){
                    //将待添加的mod加入到creat清单当中
                    status.ModeListLocationCreat.add(status.ModeListInt.get(i));
                    modsflag++;
                    break;
                }
                if(status.ModeListInt.get(i).equals(status.ModeListLocation.get(j))){
                    break;
                }
            }

        }
        //检测是否有规定外的mod文件
        int falgup = 0;
        boolean falgupDisable = false;//判断是否存在不合规mod
        for(int i = 0; i < status.ModeListLocation.size(); i++){
            for(int j = 0 ;  j < status.ModeListInt.size(); j++){
                if(status.ModeListLocation.get(i).equals(status.ModeListInt.get(j))){
                    falgup = 1;
                }
            }
            if(falgup == 0){
                System.out.print("ModDisable: ");
                System.out.format("\033[31m%s\033[0m%n", status.ModeListLocation.get(i));
                status.ModeListLocationRemove.add(status.ModeListLocation.get(i));
                falgupDisable = true;//需要开启Disable模式
            }
            falgup = 0;
        }
        //将后缀名改变，取消我的世界读取机制
        if(falgupDisable){
            for(int i = 0 ; i < status.ModeListLocationRemove.size(); i++){
                ChageName.ModeChage(status.ModeListLocationRemove.get(i),status.ModeListLocationRemove.get(i) + ".disabled");
            }
        }
        //下载缺少mod名单
        if(modsflag != 0){
            System.out.println("mod缺失，即将下载以下mod：");
            for(int i = 0 ; i < status.ModeListLocationCreat.size() ; i++){
                System.out.format("\033[32m%s\033[0m%n",status.ModeListLocationCreat.get(i));
            }
            try{
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for(int i = 0 ; i < status.ModeListLocationCreat.size(); i++){
                donwloadFile.donwloadfactionMode(status.ModeListLocationCreat.get(i));
            }
            System.out.println("mod目录更新成功");
        }else{
            System.out.println("Mod无缺失");
        }
    }
}
