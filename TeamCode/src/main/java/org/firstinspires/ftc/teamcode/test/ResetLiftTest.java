package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(name = "ResetLiftTest", group = "PRTest")
public class ResetLiftTest extends LinearOpMode {

    Robot zoom = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {
        zoom.init(hardwareMap, telemetry);
        zoom.drivetrain.setPower(.3);
        zoom.drivetrain.setTelemetry(telemetry);
        zoom.drivetrain.useBrake(true);
        zoom.outtake.neutralPosition();

        zoom.lift.init();
        zoom.lift.setTelemetry(telemetry);
        zoom.lift.useEncoders(true);
        zoom.lift.useBrake(true);
        zoom.lift.setMaxPower(.3);

        waitForStart();
        //Line Up With Second Panel from Right, Left Seam
        while (opModeIsActive()) {
            zoom.lift.setLevel(0);

            sleep(1000);

            //zoom.lift.setLevel(1);

            //sleep(1000);

            zoom.lift.setLevel(2);

            sleep(1000);
            zoom.lift.setLevel(0);
            break;
        }
    }
}
