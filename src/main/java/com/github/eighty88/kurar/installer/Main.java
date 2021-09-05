package com.github.eighty88.kurar.installer;

import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        downloadFileFromUrl("https://github.com/Palpunte-Union/KurarClient-Launcher-Java16/releases/download/1.0.0/KurarClient-Java16.jar", new File(getKurarDirectory(), "KurarClient.jar"));
        downloadFileFromUrl("https://github.com/Palpunte-Union/KurarClient-Launcher-Java16/releases/download/1.0.0/KurarClient-Java16.json", new File(getKurarDirectory(), "KurarClient.json"));
        JOptionPane.showMessageDialog(null, "KurarClient was successfully installed.");
    }

    public static void downloadFileFromUrl(String url, File file) {
        if (file.getParentFile() != null && !file.getParentFile().exists())
            file.getParentFile().mkdirs();
        try {
            file.delete();
            HttpURLConnection con = (HttpURLConnection)(new URL(url)).openConnection();
            try(InputStream is = con.getInputStream(); FileOutputStream fos = new FileOutputStream(file)) {
                byte[] buff = new byte[8192];
                int readedLen;
                while ((readedLen = is.read(buff)) > -1) {
                    fos.write(buff, 0, readedLen);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPlatformName() {
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Mac") || osName.startsWith("Darwin"))
            return "osx";
        if (osName.toLowerCase().contains("windows"))
            return "windows";
        return "linux";
    }

    public static File getWorkingDirectory() {
        String applicationData, folder, userHome = System.getProperty("user.home", ".");
        switch (getPlatformName()) {
            case "linux":
                return new File(userHome, ".minecraft/");
            case "windows":
                applicationData = System.getenv("APPDATA");
                folder = (applicationData != null) ? applicationData : userHome;
                return new File(folder, ".minecraft/");
            case "osx":
                return new File(userHome, "Library/Application Support/minecraft");
        }
        return new File(userHome, "minecraft/");
    }

    public static File getVersionsDirectory() {
        return new File(getWorkingDirectory(), "versions");
    }

    public static File getKurarDirectory() {
        return new File(getVersionsDirectory(), "KurarClient-Java16");
    }
}
