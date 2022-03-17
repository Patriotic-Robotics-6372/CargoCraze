package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Constants;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous (name = "resetIMU", group = "PRTest")
@Disabled
public class TestResetIMU extends LinearOpMode {

    Robot prbot = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {
        prbot.init(hardwareMap, telemetry);
        prbot.setMode(Constants.Status.AUTO);
        telemetry.setMsTransmissionInterval(50);

        waitForStart();
        prbot.imu.resetAngle();
        while (opModeIsActive()) {
//            prbot.drivetrain.turnIMU(45);
//            sleep(4000);
//            telemetry.addData("angle", prbot.imu.getAngle());
//            telemetry.update();

            //telemetry.addData("Orientation", prbot.imu.get)
            break;
        }
    }
}
