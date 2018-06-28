package com.benmu.drop.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.benmu.drop.R;
import com.benmu.drop.utils.AppUpdateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <p>升级下载操作</p><br/>
 *
 * @author luzhenqiang
 * @since 2.0.0
 */
public class UpdateService extends IntentService {

    private static final int NOTIFICATION_ID = 13172439;

    private static final int DOWN_OK = 2;//安装包下载成功返回2
    private static final int DOWN_ERROR = 0;//也表示升级失败
    private static final String TAG =UpdateService.class.getSimpleName();

    private InnerHandler mHandler;
    Notification mNotification;
    PendingIntent mPendingIntent;
    String mTempFilePath;
    String mAppName;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;

    public UpdateService() {
        super("UpdateService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new InnerHandler(this);
        mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        updateUrl = intent.getStringExtra("updateurl");
        mAppName = intent.getStringExtra("app_name");
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "ZD");
        if (!f.exists()) {
            f.mkdirs();
        }
        mTempFilePath = f.getAbsolutePath() + File.separator + mAppName + ".apk";
        createNotification();
        downloadUpdate();
    }

    private void downloadUpdate() {
        final Message message = mHandler.obtainMessage();
        try {
            long downloadSize = downloadUpdateFile(updateUrl, mTempFilePath);
            if (downloadSize > 0) { // 下载成功
                message.what = DOWN_OK;
                mHandler.sendMessage(message);
            }
        } catch (Exception e) {
            Log.e(TAG, "downloadUpdate: ", e);
            delFile();
            message.what = DOWN_ERROR; //下载失败意味着升级失败
            mHandler.sendMessage(message);
        }
    }

    /**
     * <p>创建通知栏</p><br/>
     * <p>TODO(详细描述)</p>
     *
     * @author luzhenqiang
     */
    private void createNotification() {
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("Start download")
                .setContentText("Connecting to NetUpdate server")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(AppUpdateUtils.drawableToBitmap(AppUpdateUtils.getAppIcon(UpdateService.this)))
                .setOngoing(true)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis());
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private String updateUrl;

    private static class InnerHandler extends Handler {

        private WeakReference<UpdateService> mOuterClass;

        private InnerHandler(UpdateService outerClass) {
            this.mOuterClass = new WeakReference<UpdateService>(outerClass);
        }

        @Override
        public void handleMessage(Message msg) {

            UpdateService service = mOuterClass.get();
            switch (msg.what) {
                case DOWN_OK:
                    service.dismissNotificationAndInstall();
                    break;
                case DOWN_ERROR:
                    service.mNotificationManager.cancel(NOTIFICATION_ID);
                    Toast.makeText(service,"Download is failed",Toast.LENGTH_SHORT).show();
                    service.stopSelf();
                    break;
                default:
                    service.stopSelf();
                    break;
            }
        }
    }

    /**
     * 下载完成取消Notification并开始弹出安装提示框
     */
    private void dismissNotificationAndInstall() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(mTempFilePath)), "application/vnd.android.package-archive");
        startActivity(intent);
        mNotificationManager.cancel(NOTIFICATION_ID);
        stopSelf();
    }


    private long downloadUpdateFile(String downurl, String file) throws IOException {

        int totalSize;// 文件总大小
        int downloadCount = 0;// 已经下载好的大小
        int downStep = 5;// 提示step
        int updateCount = 0;// 已经上传的文件大小

        InputStream inputStream;
        OutputStream outputStream;

        URL url = new URL(downurl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setReadTimeout(5000);
        // 获取下载文件的size
        totalSize = httpURLConnection.getContentLength();
        if (httpURLConnection.getResponseCode() == 404) {
            throw new NoSuchFileInServerException("could not find the file");
        }
        inputStream = httpURLConnection.getInputStream();
        File fl = new File(mTempFilePath.substring(0, mTempFilePath.lastIndexOf("/") + 1));
        fl.mkdirs();
        outputStream = new FileOutputStream(file, false);// 文件存在则覆盖掉
        byte[] buffer = new byte[1024];
        int readsize = 0;
        while ((readsize = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, readsize);
            downloadCount += readsize;// 实时获取下载到的大小
            /**
             * 每次增张5%
             */
            if (updateCount == 0 || (downloadCount * 100 / totalSize - downStep) >= updateCount) {
                updateCount += downStep;
                mBuilder.setContentTitle("Downloading：PriceDrop")
                        .setContentText(updateCount + "%")
                        .setProgress(100, updateCount, false)
                        .setWhen(System.currentTimeMillis());
                Notification notification = mBuilder.build();
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                mNotificationManager.notify(NOTIFICATION_ID, notification);
            }
        }
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        inputStream.close();
        outputStream.close();
        return downloadCount;
    }

    /**
     * 删除文件
     */
    public void delFile() {
        File myFile = new File(mTempFilePath);
        if (myFile.exists()) {
            myFile.delete();
        }
    }

    class NoSuchFileInServerException extends RuntimeException {
        public NoSuchFileInServerException(String detailMessage) {
            super(detailMessage);
        }
    }

}
