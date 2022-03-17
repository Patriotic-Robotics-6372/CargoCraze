package org.firstinspires.ftc.teamcode.auton.old;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@Disabled
public class slimeAutonRL extends LinearOpMode {

Robot robot = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        while(opModeIsActive()) {
            //move forward
            robot.drivetrain.forward(12);
            //stop
            robot.drivetrain.stop();
            //turn right
            robot.drivetrain.pointTurnLeft();
            //stop
            robot.drivetrain.stop();
            //position at carousel
            robot.drivetrain.forward(30);
            //stop
            robot.drivetrain.stop();
            //spin carousel
            robot.carousel.leftSpin(.3);
            sleep(2500);
            //turn and park in box
            robot.drivetrain.backward(18);


        }
    }
}
