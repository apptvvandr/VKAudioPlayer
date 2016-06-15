package github.y0rrrsh.vkaudioplayer.utils;

import java.io.File;

import github.y0rrrsh.vkaudioplayer.VKAPApplication;

/**
 * @author Artur Yorsh. 15.06.16.
 */
public class LocalStorage {

    private static final String DIR_APP = VKAPApplication.getContext().getExternalFilesDir(null).getPath();
    public static final String DIR_SUB_MUSIC = "music";
    public static final String FORMAT_MP3 = ".mp3";

    public static String buildFilePath(String relativePath) {
        return String.format("%s/%s", DIR_APP, relativePath);
    }

    public static File createFile(String dir, String name) {
        File fileDir = new File(buildFilePath(dir));
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return new File(fileDir, name);
    }

    public static File getFile(String dir, String name) {
        return new File(buildFilePath(dir), name);
    }

    public static boolean fileExists(String dir, String name) {
        return getFile(dir, name).exists();
    }
}
