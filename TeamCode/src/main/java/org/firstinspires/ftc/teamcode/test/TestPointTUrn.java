package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Constants;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous (name = "TestPointTurn", group = "Test")
public class TestPointTUrn extends LinearOpMode {

    Robot prbot = new Robot();
    @Override
    public void runOpMode() throws InterruptedException {
        prbot.init(hardwareMap, telemetry);
        prbot.setMode(Constants.Status.AUTO);
        waitForStart();
        while (opModeIsActive()) {
            prbot.drivetrain.pointTurn(Constants.Status.RIGHT, 10.6);
            sleep(500);
            prbot.drivetrain.pointTurn(Constants.Status.LEFT, 10.6);
            break;
        }
    }
}
