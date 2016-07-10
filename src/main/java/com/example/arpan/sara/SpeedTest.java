package com.example.arpan.sara;

import android.app.Activity;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.*;


public class SpeedTest{
    /**
     * socket timeout used in ms.
     */

    public static float speed = 0;
    private final static int SOCKET_TIMEOUT = 5000;

    /**
     * speed examples server host name.
     */
    private final static String SPEED_TEST_SERVER_HOST = "2.testdebit.info";

    /**
     * spedd examples server uri.
     */
    private final static String SPEED_TEST_SERVER_URI_DL = "/fichiers/1Mo.dat";

    /**
     * speed examples server port.
     */
    private final static int SPEED_TEST_SERVER_PORT = 80;

   public static void main(final String[] args) {
       getSpeedResult();
   }

    public static float getSpeedResult(){
        final SpeedTestSocket speedTestSocket = new SpeedTestSocket();

        //set timeout for download
        speedTestSocket.setSocketTimeout(SOCKET_TIMEOUT);

        // add a listener to wait for speed examples completion and progress
        speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {

            @Override
            public void onDownloadPacketsReceived(final long packetSize, final float transferRateBitPerSeconds, final
            float transferRateOctetPerSeconds) {
                speed =  transferRateBitPerSeconds;
            }

            @Override
            public void onDownloadError(final SpeedTestError speedTestError, final String errorMessage) {
                speed = -1;
            }

            @Override
            public void onUploadPacketsReceived(final long packetSize, final float transferRateBitPerSeconds, final
            float transferRateOctetPerSeconds) {


            }

            @Override
            public void onUploadError(final SpeedTestError speedTestError, final String errorMessage) {

            }

            @Override
            public void onDownloadProgress(final float percent, final SpeedTestReport downloadReport) {


            }

            @Override
            public void onUploadProgress(final float percent, final SpeedTestReport uploadReport) {


            }
        });


        speedTestSocket.startDownload(SPEED_TEST_SERVER_HOST, SPEED_TEST_SERVER_PORT, SPEED_TEST_SERVER_URI_DL);
        return speed;
    }
}