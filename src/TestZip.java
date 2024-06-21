import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TestZip {
    public static void main(String[] args) throws IOException {
        String zipFilePath = "D:\\MCwebBuild\\MClanuchBuild\\MClauch\\SrOfWylBDGjJ.zip"; // ZIP 文件路径
        String outputFolder = "D:\\MCwebBuild\\MClanuchBuild\\MClauch\\"; // 解压缩后的输出目录
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry = zipInputStream.getNextEntry();
            while (entry != null) {
                String entryName = entry.getName(); // 获取 ZIP 文件中的文件名
                String outputPath = outputFolder + entryName; // 构建输出路径

                if (!entry.isDirectory()) {
                    try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipInputStream.read(buffer)) >= 0) {
                            fos.write(buffer, 0, length); // 将 ZIP 文件内容写入到文件系统中
                        }
                    }
                }

                entry = zipInputStream.getNextEntry();
            }
        }
    }
}
