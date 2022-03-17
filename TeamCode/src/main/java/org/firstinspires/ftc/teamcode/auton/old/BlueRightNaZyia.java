package org.firstinspires.ftc.teamcode.auton.old;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(name = "BlueRightNaZyia", group = "PRTest")
@Disabled
public class BlueRightNaZyia extends LinearOpMode {

    Robot zoom = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {
        zoom.init(hardwareMap, telemetry);
        zoom.drivetrain.setPower(.5);
        zoom.drivetrain.setTelemetry(telemetry);
        waitForStart();
        while (opModeIsActive()) {
            // forward
            zoom.drivetrain.forward(22.5);
            //turn right
            zoom.drivetrain.stop();
            sleep(500);
            zoom.drivetrain.pointTurnRight();
           //stop
            zoom.drivetrain.stop();
            sleep(500);
            //forward
            zoom.drivetrain.forward(23);
            //stop
            zoom.drivetrain.stop();
            break;
        }
    }
}
