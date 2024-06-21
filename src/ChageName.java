import java.io.File;
//进行文件名称更换
public class ChageName {
    public static String ModeChage (String oldFile,String newFile){
        File oldFileName = new File(status.WorkModeDirectory + "\\" +oldFile);
        File newFileName = new File(status.WorkModeDirectory + "\\" + newFile);
        if (!oldFileName.renameTo(newFileName)) {
            System.out.println("Err");
        }
        return newFile;
    }
}
