package org.firstinspires.ftc.teamcode.auton.old;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Constants;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(name = "ExtraAutoNaZyia", group = "PRTest")
@Disabled
public class RedExtraAuto extends LinearOpMode {

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
            zoom.drivetrain.forward(19);
            //stop
            zoom.drivetrain.stop();


            //forward
            zoom.drivetrain.forward(22.5);
            sleep(500);
            //turn right 135 degrees
            zoom.drivetrain.pointTurn(Constants.Status.RIGHT, 17);
            sleep(500);
            //forward
            zoom.drivetrain.forward(22.5);
            sleep(500);
            //turn on carousel
            zoom.carousel.rightSpin(.5);
            sleep(3000);
            zoom.carousel.stopSpin();
            //Backwards
            zoom.drivetrain.backward(22.5);
            sleep(500);
            //turn left 45
            zoom.drivetrain.pointTurn(Constants.Status.LEFT, 6);
            sleep(500);
            //forward
            zoom.drivetrain.forward(22.5);
            sleep(500);
            //stop
            zoom.drivetrain.stop();


            break;
        }
    }
}
