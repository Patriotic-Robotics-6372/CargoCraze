/*
 * Copyright (c) 2019 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode.auton.vision.blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.hardware.Barcode;
import org.firstinspires.ftc.teamcode.hardware.Constants;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Disabled
@Autonomous (name = "BlueRightCVSide", group = "Barcode")
public class BlueRightCVFix extends LinearOpMode {

    Robot prbot = new Robot();
    OpenCvCamera webcam;

    Barcode pipeline = new Barcode(telemetry, Constants.StartPos.BLUERIGHT);

    int wait = 400;
    int waitTime = 1000;

    @Override
    public void runOpMode() {
        //initialize robot hardware
        prbot.init(hardwareMap, telemetry);
        prbot.setMode(Constants.Status.AUTO);

        //FOR THE WEBCAM
        /*
         * Instantiate an OpenCvCamera object for the camera we'll be using.
         * Webcam stream goes to RC phone
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam 1"), cameraMonitorViewId);

        webcam.setPipeline(pipeline);

        /*
         * Open the connection to the camera device. New in v1.4.0 is the ability
         * to open the camera asynchronously which allows faster init time, and
         * better behavior when pressing stop during init (i.e. less of a chance
         * of tripping the stuck watchdog)
         */
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                /*
                 * Tell the webcam to start streaming images to us! Note that you must make sure
                 * the resolution you specify is supported by the camera. If it is not, an exception
                 * will be thrown.
                 *
                 * Keep in mind that the SDK's UVC driver (what OpenCvWebcam uses under the hood) only
                 * supports streaming from the webcam in the uncompressed YUV image format. This means
                 * that the maximum resolution you can stream at and still get up to 30FPS is 480p (640x480).
                 * Streaming at e.g. 720p will limit you to up to 10FPS and so on and so forth.
                 *
                 * Also, we specify the rotation that the webcam is used in. This is so that the image
                 * from the camera sensor can be rotated such that it is always displayed with the image upright.
                 * For a front facing camera, rotation is defined assuming the user is looking at the screen.
                 * For a rear facing camera or a webcam, rotation is defined assuming the camera is facing
                 * away from the user.
                 */
                //320px x 340px
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);

                /*
                 * Specify the image processing pipeline we wish to invoke upon receipt
                 * of a frame from the camera. Note that switching pipelines on-the-fly
                 * (while a streaming session is in flight) *IS* supported.
                 */

                webcam.setPipeline(pipeline);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("errorCode", errorCode);
            }
        });
        // Tell telemetry to update faster than the default 250ms period :)
        telemetry.setMsTransmissionInterval(20);

        telemetry.addLine("Waiting for start");
        telemetry.update();

        //Wait for the user to press start on the Driver Station

        waitForStart();

        //Manages Telemetry and stopping the stream
        while (opModeIsActive()) {

            sleep(2000);
            telemetry.addData("Analysis", pipeline.getAnalysis());
            telemetry.update();

            switch (pipeline.getAnalysis()) {
                case LEFT:
                   //backward
                    prbot.drivetrain.backward(5.0);
                    sleep(wait);
                    //left
                    //prbot.drivetrain.pointTurnLeft();
                    prbot.drivetrain.pointTurn(Constants.Status.RIGHT, 11.9);
                    sleep(wait);
                    // move to carousel
                    prbot.drivetrain.backward(18.2);
                    //prbot.drivetrain.backward(27);
                    sleep(wait);
                    //
                    prbot.drivetrain.setPower(.10);
                    prbot.drivetrain.backward(0.5);
                    prbot.drivetrain.pointTurn(Constants.Status.LEFT, 1.0);
                    sleep(200);

                    //spin
                    prbot.carousel.rightSpin(.5);
                    sleep(3500);
                    prbot.carousel.stopSpin();
                    //back up a bit
                    prbot.drivetrain.setPower(.4);
                    prbot.drivetrain.forward(6);
                    //right
                    prbot.drivetrain.pointTurn(Constants.Status.LEFT, 11.6);
                    sleep(wait);
                    //forward (to warehouse)
                    prbot.drivetrain.backward(27.2);
                    sleep(wait);
                    //right
                    prbot.drivetrain.pointTurnRight();
                    sleep(wait);
                    //forward
                    prbot.drivetrain.forward(18.1);
                    sleep(wait);
                    //drop
                    prbot.drivetrain.backward();
                    prbot.outtake.backPosition();
                    sleep(100);
                    prbot.drivetrain.stop();
                    sleep(900);
                    //reset
                    prbot.outtake.neutralPosition();
                    sleep(wait);
                    //180 turn
                    prbot.drivetrain.backward(4);
                    sleep(200);
                    prbot.drivetrain.pointTurnLeft();
                    sleep(500);
                    prbot.drivetrain.pointTurnLeft();
                    sleep(500);
                    //forward (to wall)
                    prbot.drivetrain.forward(23);
                    sleep(200);
                    //park (?)
                    prbot.drivetrain.backward(3.25);
                    prbot.drivetrain.pointTurnLeft();
                    sleep(200);
                    prbot.drivetrain.backward(5.5);


                    break;
                case CENTER:
                    //backward
                    prbot.drivetrain.backward(5.0);
                    sleep(wait);
                    //left
                    //prbot.drivetrain.pointTurnLeft();
                    prbot.drivetrain.pointTurn(Constants.Status.RIGHT, 11.9);
                    sleep(wait);
                    // move to carousel
                    prbot.drivetrain.backward(18.2);
                    //prbot.drivetrain.backward(27);
                    sleep(wait);
                    //
                    prbot.drivetrain.setPower(.10);
                    prbot.drivetrain.backward(1.0);
                    prbot.drivetrain.pointTurn(Constants.Status.LEFT, 1.0);
                    sleep(200);

                    //spin
                    prbot.carousel.rightSpin(.5);
                    sleep(3500);
                    prbot.carousel.stopSpin();
                    //back up a bit
                    prbot.drivetrain.setPower(.4);
                    prbot.drivetrain.forward(6);
                    //right
                    prbot.drivetrain.pointTurn(Constants.Status.LEFT, 11.6);
                    sleep(wait);
                    //forward (to warehouse)
                    prbot.drivetrain.backward(24.3);
                    sleep(wait);
                    //right
                    prbot.drivetrain.pointTurnRight();
                    sleep(wait);
                    //forward
                    prbot.drivetrain.forward(19.2);
                    sleep(wait);
                    //drop
                    prbot.lift.setLevel(1);
                    prbot.lift.updateLevel();
                    sleep(wait);
                    prbot.drivetrain.backward();
                    prbot.outtake.backPosition();
                    sleep(100);
                    prbot.drivetrain.stop();
                    sleep(900);
                    //reset
                    prbot.outtake.neutralPosition();
                    prbot.lift.setLevel(0);
                    prbot.lift.updateLevel();
                    sleep(wait);
                    //180 turn
                    prbot.drivetrain.backward(4);
                    sleep(200);
                    prbot.drivetrain.pointTurnLeft();
                    sleep(500);
                    prbot.drivetrain.pointTurnLeft();
                    sleep(500);
                    //forward (to wall)
                    prbot.drivetrain.forward(25);
                    sleep(200);
                    //park (?)
                    prbot.drivetrain.backward(3);
                    prbot.drivetrain.pointTurnLeft();
                    sleep(200);
                    prbot.drivetrain.backward(5.5);


                    break;
                case RIGHT:
                    //backward
                    prbot.drivetrain.backward(5.0);
                    sleep(wait);
                    //left
                    //prbot.drivetrain.pointTurnLeft();
                    prbot.drivetrain.pointTurn(Constants.Status.RIGHT, 11.9);
                    sleep(wait);
                    // move to carousel
                    prbot.drivetrain.backward(18.2);
                    //prbot.drivetrain.backward(27);
                    sleep(wait);
                    //
                    prbot.drivetrain.setPower(.10);
                    prbot.drivetrain.backward(1.0);
                    prbot.drivetrain.pointTurn(Constants.Status.LEFT, 1.0);
                    sleep(200);

                    //spin
                    prbot.carousel.rightSpin(.5);
                    sleep(3500);
                    prbot.carousel.stopSpin();
                    //back up a bit
                    prbot.drivetrain.setPower(.4);
                    prbot.drivetrain.forward(6);
                    //right
                    prbot.drivetrain.pointTurn(Constants.Status.LEFT, 11.6);
                    sleep(wait);
                    //forward (to warehouse)
                    prbot.drivetrain.backward(26.2);
                    sleep(wait);
                    //right
                    prbot.drivetrain.pointTurnRight();
                    sleep(wait);
                    //forward
                    prbot.drivetrain.forward(21.4);
                    sleep(wait);
                    //drop
                    prbot.lift.setLevel(2);
                    prbot.lift.updateLevel();
                    sleep(wait);
                    //prbot.drivetrain.backward();
                    prbot.outtake.backPosition();
                    sleep(100);
                    //prbot.drivetrain.stop();
                    sleep(900);
                    //reset
                    prbot.outtake.neutralPosition();
                    prbot.lift.setLevel(0);
                    prbot.lift.updateLevel();
                    sleep(wait);
                    //180 turn
                    prbot.drivetrain.backward(4);
                    sleep(200);
                    prbot.drivetrain.pointTurnLeft();
                    sleep(500);
                    prbot.drivetrain.pointTurnLeft();
                    sleep(500);
                    //forward (to wall)
                    prbot.drivetrain.forward(25);
                    sleep(200);
                    //park (?)
                    prbot.drivetrain.backward(3);
                    prbot.drivetrain.pointTurnLeft();
                    sleep(200);
                    prbot.drivetrain.backward(5.5);



            }

            //reminder to use the KNO3 auto transitioner once this code is working
            webcam.stopStreaming();
            webcam.closeCameraDevice();
            break;
        }
    }
}