package ru.Katyanka8bit;

import java.io.File;
import java.util.List;
import java.util.Map;

public class FileSize {
    public static long getSumFilesSize(Map<String, List<String>> fileDuplicate) {
        final long[] sumFilesize = new long[1];
        if (fileDuplicate==null) {
            return 0;
        }
        fileDuplicate.forEach((a, b) -> {
            if (b.size() > 1) {
                long filesize = 0;
                File file = new File(b.get(0));
                filesize = file.length();
                sumFilesize[0] += ((filesize * (b.size() - 1)) / 1048576);
            }
        });
        return sumFilesize[0] ;
    }
}
