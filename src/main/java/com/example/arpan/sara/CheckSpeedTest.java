package com.example.arpan.sara;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.herokuapp.darkfire.sara.Action;
import com.herokuapp.darkfire.sara.R;
import com.herokuapp.darkfire.sara.interfaces.Machine;

import fr.bmartel.speedtest.ISpeedTestListener;
import fr.bmartel.speedtest.SpeedTestError;
import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;

import static android.widget.Toast.*;

/**
 * Created by Arpan on 7/9/2016.
 */
public class CheckSpeedTest extends AsyncTask<Void, Void, Void> {

    Machine machine;
    String speed = null;

    public CheckSpeedTest(Machine machine) {
        this.machine = machine;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        machine.sendInput("I will post the results soon");
    }

    @Override
    protected Void doInBackground(Void... params) {


        final SpeedTestSocket speedTestSocket = new SpeedTestSocket();
        System.out.println("Adding speedtest listener");
        speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {


            @Override
            public void onDownloadError(SpeedTestError errorCode, String message) {
                //  Log.i("speed-test-apdap","Download error " + errorCode + " occured with message : " + message);
            }

            @Override
            public void onUploadPacketsReceived(long packetSize, float transferRateBps, float transferRateOps) {
                System.out.println("download transfer rate  : " + transferRateBps + " bps");
                System.out.println("download transfer rate  : " + transferRateBps + "Bps");
            }


            @Override
            public void onUploadError(SpeedTestError errorCode, String message) {
                // Log.i("speed-test-app","Upload error " + errorCode + " occured with message : " + message);
            }

            @Override
            public void onDownloadPacketsReceived(long packetSize, float transferRateBps, float transferRateOps) {
                System.out.println("Inside onDownloadPackage");
                speed = "Your download speed is: " + transferRateBps / (8 * 1024) + " KBps";
                Log.i("Result", speed);
                machine.sendInput(speed);
            }

            @Override
            public void onDownloadProgress(float percent, SpeedTestReport downloadReport) {
            }

            @Override
            public void onUploadProgress(float percent, SpeedTestReport uploadReport) {
            }

        });
        System.out.println("Starting download");
        speedTestSocket.startDownload("1.testdebit.info", 80,"/fichiers/5Mo.dat"); //will block until upload is finished
        return null;
    }
}

