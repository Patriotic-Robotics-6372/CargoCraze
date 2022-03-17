package org.firstinspires.ftc.teamcode.auton.working.blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Constants;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@Disabled
@Autonomous(name = "BlueHubRight", group = "PRTEST")
public class BlueHubRight extends LinearOpMode {

    Robot zoom = new Robot();
    @Override

    public void runOpMode() throws InterruptedException {
        zoom.init(hardwareMap, telemetry);
        zoom.drivetrain.setPower(.4);
        zoom.drivetrain.setTelemetry(telemetry);
        zoom.drivetrain.useBrake(true);
        zoom.outtake.neutralPosition();

        zoom.lift.init();
        zoom.lift.setTelemetry(telemetry);
        zoom.lift.useEncoders(true);
        zoom.lift.useBrake(true);
        zoom.lift.setMaxPower(.3);
        waitForStart();
        while (opModeIsActive()) {

            //forward
            zoom.drivetrain.forward(12);
            sleep(250);
            //turn
            zoom.drivetrain.pointTurnRight();
            sleep(250);
            //backward
            zoom.drivetrain.backward(21);
            sleep(250);
            //turn
            zoom.drivetrain.pointTurnLeft();
            sleep(250);
            // forward
            zoom.drivetrain.forward(6);
            sleep(250);

            //drop lift
            zoom.lift.setLevel(2);
            zoom.lift.updateLevel();
            sleep(250);

            zoom.outtake.backPosition();
            sleep(2000);

            zoom.outtake.neutralPosition();
            zoom.lift.setLevel(0);
            zoom.lift.updateLevel();
            sleep(250);

            zoom.drivetrain.backward(16);
            sleep(250);

            zoom.drivetrain.pointTurn(Constants.Status.LEFT, 11.7);
            sleep(250);

            zoom.drivetrain.backward(53);
            sleep(250);

            zoom.carousel.rightSpin(.5);
            sleep(3500);
            zoom.carousel.stopSpin();

            //forward
            zoom.drivetrain.forward(2);
            //turn right
            zoom.drivetrain.pointTurnRight();
            sleep(500);
            //forward
            zoom.drivetrain.forward(18);
            //stop
            zoom.drivetrain.stop();
            //lift down
            //backward
            //turn
            //forward
            //STOP
            //
            break;

        }
    }
}
