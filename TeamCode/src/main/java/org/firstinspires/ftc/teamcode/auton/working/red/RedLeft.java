package org.firstinspires.ftc.teamcode.auton.working.red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Constants;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@Disabled
@Autonomous(name = "RedLeft", group = "PRTest")
public class RedLeft extends LinearOpMode {

    Robot zoom = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {
        zoom.init(hardwareMap, telemetry);
        zoom.drivetrain.setPower(.3);
        zoom.drivetrain.setTelemetry(telemetry);
        zoom.drivetrain.useBrake(true);
        zoom.outtake.neutralPosition();
        telemetry.setMsTransmissionInterval(50);
        telemetry.addData("Desc", "");
        telemetry.addData("Align", "2nd Panel from Left, Right Seam");
        telemetry.update();
        waitForStart();
        //Line Up With Second Panel from Right, Left Seam
        while (opModeIsActive()) {


            //forward
            zoom.drivetrain.forward(4.5);
            sleep(500);
            //turn left 135 degrees
            zoom.drivetrain.pointTurn(Constants.Status.RIGHT, 13.0);
            sleep(500);
            //backward
            zoom.drivetrain.backward(29.5);
            sleep(500);
            //turn on carousel
            zoom.carousel.leftSpin(.5);
            sleep(3500);
            zoom.carousel.stopSpin();
            //forward
            zoom.drivetrain.forward(2);
            //turn right
            //zoom.drivetrain.pointTurn(Constants.Status.LEFT, 14.8);
            sleep(500);
            //forward
            zoom.drivetrain.forward(18);
            //stop
            zoom.drivetrain.stop();
            break;
            //LETS FUCKING GO
        }
    }
}
