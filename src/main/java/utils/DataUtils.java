package utils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import common.MyPath;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DataUtils {

    private static final Logger logger = LoggerFactory.getLogger(DataUtils.class);

    public static String getPropertyValue(String propertyFileName, String propertyName) {
        FileInputStream fis;
        Properties prop = new Properties();
        try {
            fis = new FileInputStream(MyPath.RESOURCES_PATH.getPath() + propertyFileName);
            prop.load(fis);
        } catch (IOException e) {
            System.err.println("No property file found:" + propertyFileName);
        }
        return prop.getProperty(propertyName);
    }

    public static Properties getProperty(String propertyFileName) {
        FileInputStream fis;
        Properties prop = new Properties();
        try {
            fis = new FileInputStream(MyPath.RESOURCES_PATH.getPath() + propertyFileName);
            prop.load(fis);
        } catch (IOException e) {
            System.err.println("No property file found:" + propertyFileName);
        }
        return prop;
    }

    public static void saveProperty(Properties p, File file) {
        try {
            FileOutputStream fr = new FileOutputStream(file);
            p.store(fr, "Properties");
            fr.close();
            System.out.println("After saving properties: " + p);
        } catch (IOException e) {
            System.err.println("No property file found:" + file);
        }
    }

    public static String getAbsolutePathToRoot() {
        return System.getProperty("user.dir");
    }

    public static String getCurrentTimestamp() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(ts);
        cal.add(Calendar.HOUR, -4);
        return sdf.format(cal.getTime());
    }

    public static String getCurrentDate() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(ts);
        cal.add(Calendar.HOUR, -4);
        return sdf.format(cal.getTime());
    }

    public static void updatePropertyParameter(String propertyFileName, String parameter, String value) throws IOException {
        Properties props = getProperty(propertyFileName);
        FileOutputStream out = new FileOutputStream(propertyFileName);
        props.setProperty(parameter, value);
        props.store(out, null);
        out.close();
    }

    public static String convertUnicodeToAscii(String input) {
        return StringEscapeUtils.unescapeJava(input);
    }

    public static String getAlphaNumericRandom(int alphaLength, int digitsLength) {
        final String Alpha = "abcdefghijklmnopqrstuvwxyz";
        final String Numeric = "0123456789";
        SecureRandom rnd = new SecureRandom();
        int len1 = alphaLength;
        int len2 = digitsLength;
        StringBuilder sb = new StringBuilder(alphaLength + digitsLength);
        for (int i = 0; i < len1; i++)
            sb.append(Alpha.charAt(rnd.nextInt(Alpha.length())));
        for (int i = 0; i < len2; i++)
            sb.append(Numeric.charAt(rnd.nextInt(Numeric.length())));
        return sb.toString();
    }

    public static void executeShellCommand(String cmd) {
        try {
            Process process
                    = Runtime.getRuntime().exec(cmd);

            StringBuilder output = new StringBuilder();

            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println(
                        "**************************** The Output is ******************************");
                System.out.println(output);
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String removeUnicode(String text) {
        String result = text;
        if (text.contains("®")) {
            result = text.replace("®", "%");
        }
        if (text.contains("\t")) {
            result = text.replace("\t", "%");
        } else if (text.contains("\\t")) {
            result = text.replace("\\t", "%");
        }
        if (text.contains("\\u00ae")) {
            result = text.replace("\\u00ae", "%");
        }
        return result;
    }

    public static void clearFolder(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
    }

    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    public static void zipFolder(String sourceDirPath, String zipFilePath) throws IOException {
        Files.deleteIfExists(Path.of(zipFilePath));
        Path p = Files.createFile(Paths.get(zipFilePath));
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
            Path pp = Paths.get(sourceDirPath);
            Files.walk(pp)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            Files.copy(path, zs);
                            zs.closeEntry();
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                    });
        }
    }
}



