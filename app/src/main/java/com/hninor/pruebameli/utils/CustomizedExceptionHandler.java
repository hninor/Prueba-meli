package com.hninor.pruebameli.utils;

/**
 * Created by apinedo on 4/10/2017.
 */

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomizedExceptionHandler implements UncaughtExceptionHandler {

    private UncaughtExceptionHandler defaultUEH;
    private String localPath;
    private StringBuilder header = new StringBuilder();

    public CustomizedExceptionHandler(Context context, String localPath) {
        this.localPath = localPath;
        this.header.append("Android version: ").append(Build.VERSION.SDK_INT).append("\n");
        String model = Build.MODEL;
        if (!model.startsWith(Build.MANUFACTURER)) {
            this.header.append("Device Model: ").append(Build.MANUFACTURER).append(" ").append(model).append("\n");
        }
        if (context != null) {
            try {
                PackageManager manager = context.getPackageManager();
                PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
                this.header.append("Package name: ").append(info == null ? "(null)" : info.packageName).append("\n");
                this.header.append("App version: ").append(info == null ? "(null)" : info.versionCode).append("\n");
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("PackageManager", e.getMessage());
                e.printStackTrace();
            }
        }
        //Getting the the default exception handler
        //that's executed when uncaught exception terminates a thread
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
    }

    public void uncaughtException(Thread t, Throwable e) {

        //Write a printable representation of this Throwable
        //The StringWriter gives the lock used to synchronize access to this writer.
        final Writer stringBuffSync = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringBuffSync);

        header.append("cause: ").append(e.getCause()).append("\n");
        header.append("message: ").append(e.getMessage()).append("\n");
        header.append("stackTrace: ").append("\n");
        e.printStackTrace(printWriter);
        header.append(stringBuffSync.toString()).append("\n");
        printWriter.close();

        if (localPath != null) {
            writeToFile(header.toString());
        }
        //Used only to prevent from any code getting executed.
        // Not needed in this example
        defaultUEH.uncaughtException(t, e);
    }

    private void writeToFile(String currentStacktrace) {
        try {
            //Gets the Android external storage directory & Create new folder Crash_Reports
            File dir = new File(localPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            Date date = new Date();
            String filename = dateFormat.format(date) + ".log";
            // Write the file into the folder
            File reportFile = new File(dir, filename);
            FileWriter fileWriter = new FileWriter(reportFile);
            fileWriter.append(currentStacktrace);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            Log.e("ExceptionHandler", e.getMessage());
            e.printStackTrace();
        }
    }

}

