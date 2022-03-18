package org.firstinspires.ftc.teamcode.auton.one;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(name = "TestWheels", group = "PRTest")
public class Forward extends LinearOpMode {

    Robot zoom = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {
        zoom.init(hardwareMap, telemetry);
        zoom.drivetrain.setPower(.3);
        zoom.drivetrain.setTelemetry(telemetry);
        zoom.drivetrain.useBrake(true);
        zoom.outtake.neutralPosition();
        waitForStart();
        //Line Up With Second Panel from Right, Left Seam
        while (opModeIsActive()) {
            //forward
            zoom.drivetrain.forward(5);
            sleep(3000);
            zoom.drivetrain.forward(3);
            sleep(3000);
            zoom.drivetrain.forward(24);
            sleep(3000);
            zoom.drivetrain.pointTurnRight();
            break;
            //LETS FUCKING GO
        }
    }
}
