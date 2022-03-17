package org.firstinspires.ftc.teamcode.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.hardware.Barcode;
import org.firstinspires.ftc.teamcode.hardware.Button;
import org.firstinspires.ftc.teamcode.hardware.Constants;
import org.firstinspires.ftc.teamcode.hardware.PIDController;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Config
@Autonomous (name = "Turn90IMUPID", group = "PRTest")
public class Turn90IMUPID extends LinearOpMode {

    Robot prbot = new Robot();

    OpenCvCamera webcam;

    Barcode pipeline = new Barcode(telemetry, Constants.StartPos.REDLEFT);

    PIDController turnPID;

    Button a;

    boolean toggle = false;

    // 0.02
    // 0.0001
    // 0.001

    public static double P = 0.0105;
    public static double I = 0.0002;
    public static double D = 0.0011;

    // positive number = turn left
    public static double targetAngle = 90;

    @Override
    public void runOpMode() throws InterruptedException {
        prbot.init(hardwareMap, telemetry);
        prbot.setMode(Constants.Status.AUTO);

        a = new Button();

        //FtcDashboard dashboard = FtcDashboard.getInstance();

        //Telemetry telem = dashboard.getTelemetry();

        telemetry.setMsTransmissionInterval(20);
        prbot.drivetrain.setTelemetry(telemetry);

        FtcDashboard dashboard = FtcDashboard.getInstance();

        prbot.drivetrain.setDashboard(dashboard);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam 1"), cameraMonitorViewId);

        webcam.setPipeline(pipeline);

        waitForStart();

        dashboard.startCameraStream(webcam, 0);

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
                webcam.startStreaming(320 * 2, 240 * 2 , OpenCvCameraRotation.UPRIGHT);

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

        prbot.imu.resetAngle();
        telemetry.addData("startAngle", prbot.imu.getAngle());
        telemetry.update();

        while (opModeIsActive()) {
            a.previous();
            a.setState(gamepad1.a);

            if (a.isPressed()) toggle = !toggle;

            if (toggle) {
                prbot.imu.resetAngle();
                telemetry.addData("startAngle", prbot.imu.getAngle());
                telemetry.addData("goalAngle", prbot.imu.getAngle() + targetAngle);
                prbot.drivetrain.PIDTurn(targetAngle, new PIDController(P, I, D, 20));
                sleep(500);
                //telemetry.addData("PIDTurn", "finished");
                //telemetry.update();
                toggle = false;
            }

        }
    }
}
