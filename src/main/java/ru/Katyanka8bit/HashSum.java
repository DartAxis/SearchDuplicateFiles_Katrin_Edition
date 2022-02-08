package ru.Katyanka8bit;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

public class HashSum {
    public static String getHashSumFile(String path) {
        if(!Files.exists(Paths.get(path))) {
            return null;
        }
        String output;
        int read;
        byte[] buffer = new byte[8192];
        File file = new File(path);
        BufferedInputStream bufferedInputStream = null;
        try { bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            while ((read = bufferedInputStream.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
            byte[] hash = digest.digest();
            BigInteger bigInt = new BigInteger(1, hash);
            output = bigInt.toString(16);

        }
        catch (Exception e) {
            e.printStackTrace( System.err );
            return null;
        }
        return output;
    }
}
