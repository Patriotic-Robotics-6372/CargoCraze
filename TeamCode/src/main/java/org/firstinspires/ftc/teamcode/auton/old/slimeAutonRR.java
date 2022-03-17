package org.firstinspires.ftc.teamcode.auton.old;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@Disabled
public class slimeAutonRR extends LinearOpMode {

Robot robot = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        while(opModeIsActive()) {
            // forward x inches
            robot.drivetrain.forward(24);
            // stop
            robot.drivetrain.stop();
            sleep(500);
            // turn left 90 degrees
            robot.drivetrain.pointTurnRight();
            // stop
            robot.drivetrain.stop();
            // forwad x inches
            robot.drivetrain.forward(12);
            // stop
            robot.drivetrain.stop();

        }
    }
}
