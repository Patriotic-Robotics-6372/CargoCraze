package org.firstinspires.ftc.teamcode.auton.old;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@Disabled
public class slimeAutonBR extends LinearOpMode {

Robot robot = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, telemetry);
        robot.drivetrain.setPower(.5);
        waitForStart();
        while (opModeIsActive()) {
           //move forward
            robot.drivetrain.forward(12);
           //stop
           robot.drivetrain.stop();
           sleep(1000);
           //turn right
            robot.drivetrain.pointTurnRight();
           //stop
           robot.drivetrain.stop();
           //position at carousel
            robot.drivetrain.forward(30);
           //stop
           robot.drivetrain.stop();
           //spin carousel
            robot.carousel.rightSpin(.3);
            sleep(2500);
           //turn and park in box
            robot.drivetrain.backward(18);
        }
    }
}