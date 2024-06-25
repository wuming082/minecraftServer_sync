import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class status {
    public static String version = "v0.9.4";
    public static boolean Updataoption = false;
    public static File WorkDirectory= new File(System.getProperty("user.dir"));
    public static File WorkModeDirectory= new File(WorkDirectory.toString()+"\\.minecraft\\mods");
    //HMCL主体文件
    public boolean HMCL;
    public static String HMCLFileName;//Hmcl文件名称
    public static String HMCLFileNameVer;//Hmcl版本号
    public static String HMCLUrl;//Hmcl下载地址
    //MODE文件
    public boolean MODE;
    //内部MODE清单
    public static List<String> ModeListLocation = new ArrayList<>();
    //外部MODE清单
    public static List<String> ModeListInt = new ArrayList<>();
    //外部MODEUrl清单
    public static List<String> ModeListIntUrl = new ArrayList<>();
    //内部缺少mod名单
    public static List<String> ModeListLocationCreat = new ArrayList<>();
    //内部不兼容mod名单
    public static List<String> ModeListLocationRemove = new ArrayList<>();
    //打印列表清单方法
    public static void printListMode(String input){//输入in/out来打印网络/本地mod清单
        if(input.equals("out")){
            int NumMode = ModeListInt.size();
            System.out.println("网络清单mod为：");
            for(int i = 0 ; i < NumMode; i++){
                System.out.println(ModeListInt.get(i));
            }
            System.out.println("网络请求mod总数为：" + NumMode);
        }else if(input.equals("in")){
            int NumMode = ModeListLocation.size();
            System.out.println("本地mod为：");
            for(int i = 0 ; i < NumMode; i++){
                System.out.println(ModeListLocation.get(i));
            }
            System.out.println("本地mod总数为：" + NumMode);
        }else{
            System.out.println("参数错误，in/out");
        }

    }
    public status() {
        this.HMCL = false;
        this.MODE = false;
    }

}
