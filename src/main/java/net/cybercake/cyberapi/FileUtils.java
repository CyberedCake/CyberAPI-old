package net.cybercake.cyberapi;

import java.io.File;

public class FileUtils {

    public static boolean delete(File path) {
        if(path.exists()) {
            File[] files = path.listFiles();
            assert files != null;
            for (File file : files) {
                if (file.isDirectory()) {
                    delete(file);
                } else {
                    file.delete();
                }
            }
        }
        return(path.delete());
    }

}
