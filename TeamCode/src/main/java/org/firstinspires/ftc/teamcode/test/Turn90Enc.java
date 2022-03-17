package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(name = "Turn90", group = "PRTest")
//@Disabled
public class Turn90Enc extends LinearOpMode {

    Robot zoom = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {
        zoom.drivetrain.setPower(.5);
        zoom.drivetrain.setTelemetry(telemetry);
        zoom.drivetrain.useBrake(true);
        telemetry.addData("Start Angle", zoom.imu.getFirstAngleNum());
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            zoom.drivetrain.pointTurnLeft();
            sleep(100);
            zoom.drivetrain.stop();
            telemetry.addData("End Angle", zoom.imu.getFirstAngleNum());
            telemetry.update();
            break;
        }
    }
}
