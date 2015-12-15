package com.example.lukasz.myapplication;

/**
 * Created by lukasz on 15.12.15.
 */
public class RepeatingThread extends Thread {

    private IFollowsDownloadCallback callback;
    private String token;
    private int timeout;
    public RepeatingThread(String token,int timeout,IFollowsDownloadCallback callback) {
        this.callback=callback;
        this.token=token;
        this.timeout=timeout;
    }
    @Override
    public void run() {
        super.run();
        while(true)
        {
            new FollowsDownloadAsyncTask(callback).execute(token);
            try {
                sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
