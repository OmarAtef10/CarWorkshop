package CustomizedUtilities;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BackupUtility {
    public static void backup(){
        Thread thread = new Thread(() -> {
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            File source = new File(s+"\\db");
            String fname = TimeUtility.getReformedDate(TimeUtility.getCurrentDate());
            try {
                File f = new File(s + "\\BACKUP\\" + fname);
                if(f.exists()){
                    return;
                }
                Files.createDirectory(Paths.get(s + "\\BACKUP\\" + fname));
                File dest = new File(s+"\\BACKUP\\"+fname);
                FileUtils.copyDirectory(source, dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
