import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class synchronousTxtTest {
    //外部MODE清单
    public static List<String> ModeListInt = new ArrayList<>();
    //外部MODEUrl清单
    public static List<String> ModeListIntUrl = new ArrayList<>();
    //次数flag
    //用于记录间隔
    public static int flagMode = 0;
    public static void main(String[] args) {
        String filePath = "E:\\MC1.18.2整合包\\donwloadList.txt"; // 替换为你的文件路径
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if(flagMode == 0){ModeListInt.add(line);flagMode++;}
                else if(flagMode == 1){ModeListIntUrl.add(line);flagMode++;}
                if(line.equals("")){flagMode = 0;}
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(ModeListInt);
        System.out.println(ModeListIntUrl);
        System.out.println("mod总数为：" + ModeListInt.size());
    }
}
