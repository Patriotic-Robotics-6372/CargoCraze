package org.firstinspires.ftc.teamcode.auton.working.red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@Disabled
@Autonomous(name = "RedRight", group = "PRTest")
public class RedRight extends LinearOpMode {

Robot robot = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, telemetry);
        robot.drivetrain.setPower(.4);
        robot.drivetrain.setTelemetry(telemetry);
        robot.drivetrain.useBrake(true);
        robot.outtake.neutralPosition();
        telemetry.setMsTransmissionInterval(50);
        telemetry.addData("Desc", "");
        telemetry.addData("Align", "3rd Panel from Left, Right Seam");
        telemetry.update();
        waitForStart();
        //Line Up With 3rd Panel from Right, Left Seam
        while(opModeIsActive()) {
            // forward x inches
            robot.drivetrain.forward(16);;
            sleep(500);
            // turn left 90 degrees
            robot.drivetrain.pointTurnLeft();
            //backwards
            robot.drivetrain.forward(8);
            sleep(600);
            // forward
            robot.drivetrain.setPower(.6);
            robot.drivetrain.backward(90);
            // stop
            robot.drivetrain.stop();

            break;


        }
    }
}
