package org.firstinspires.ftc.teamcode.auton.working.red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@Disabled
@Autonomous(name = "RedWarehouseLeft", group = "PRTest")
public class RedWarehouseLeft extends LinearOpMode {

    Robot zoom = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {
        zoom.init(hardwareMap, telemetry);
        zoom.drivetrain.setPower(.3);
        zoom.drivetrain.setTelemetry(telemetry);
        zoom.drivetrain.useBrake(true);
        zoom.outtake.neutralPosition();
//        telemetry.setMsTransmissionInterval(50);
//        telemetry.addData("Desc", "");
//        telemetry.addData("Align", "2nd Panel from Left, Right Seam");
//        telemetry.update();
        waitForStart();
        //Line Up With Second Panel from Right, Left Seam
        while (opModeIsActive()) {
            //forward
            zoom.drivetrain.forward(16);
            sleep(1000);
            zoom.drivetrain.pointTurnRight();
            sleep(500);
            zoom.drivetrain.setPower(.5);
            zoom.drivetrain.forward(90);
            break;
            //LETS FUCKING GO
        }
    }
}
