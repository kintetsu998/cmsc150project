package com.kintetsu.cmsc150.artificialdietician.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Aspire V3 on 11/22/2016.
 */

public class FileUtil {
    public static final String FILE_NAME = "tableu.txt";
    public static final String TAG = FileUtil.class.getSimpleName();

    public static boolean write(String text, Context context) {
        File root, dir, file;

        if(!isExternalStorageWriteable()) {
            return false;
        }

        root = android.os.Environment.getExternalStorageDirectory();
        dir = new File(root.getAbsolutePath() + "/simplex");
        dir.mkdirs();
        file = new File(dir, FILE_NAME);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(text + "\n");
            writer.close();

            return true;
        } catch(IOException e) {
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    //code from http://stackoverflow.com/questions/8330276/write-a-file-in-external-storage-in-android
    public static boolean writeTableu(double[][] tableu, Context context, String header) {
        File root, dir, file;

        if(!isExternalStorageWriteable()) {
            return false;
        }

        root = android.os.Environment.getExternalStorageDirectory();
        dir = new File(root.getAbsolutePath() + "/simplex");
        dir.mkdirs();
        file = new File(dir, FILE_NAME);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

            writer.write(header + "\n");
            for(int i=0; i<tableu.length; i++) {
                for(int j=0; j<tableu[i].length; j++) {
                    writer.write(Double.toString(tableu[i][j]) + "|");
                }
                writer.write("//\n");
            }
            writer.write("\n");
            writer.close();
            return true;
        } catch(IOException e) {
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public static boolean writeBasicAns(double[] ans, Context context) {
        File root, dir, file;

        if(!isExternalStorageWriteable()) {
            return false;
        }

        root = android.os.Environment.getExternalStorageDirectory();
        dir = new File(root.getAbsolutePath() + "/simplex");
        dir.mkdirs();
        file = new File(dir, FILE_NAME);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

            writer.write("Basic Solution:\n");
            for(double d : ans) {
                writer.write(Double.toString(d) + "\t");
            }
            writer.write("\n\n");
            writer.close();

            return true;
        } catch(IOException e) {
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public static boolean clearFile() {
        File root, dir, file;

        if(!isExternalStorageWriteable()) {
            return false;
        }

        root = android.os.Environment.getExternalStorageDirectory();
        dir = new File(root.getAbsolutePath() + "/simplex");
        dir.mkdirs();
        file = new File(dir, FILE_NAME);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("");
            writer.close();
            return true;
        } catch(IOException e) {
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    //code feom https://developer.android.com/training/basics/data-storage/files.html#WriteExternalStorage
    private static boolean isExternalStorageWriteable() {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }

        Log.e(TAG, "Cannot write to file.");
        return false;
    }
}
