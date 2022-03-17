package org.firstinspires.ftc.teamcode.auton.working;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Button;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous (name = "ForwardTuner", group = "PRTest")
public class ForwardTuner extends LinearOpMode {

    Robot zoom = new Robot();

    double power;
    double inches;

    Button y, a, dpad_up, dpad_down;

    @Override
    public void runOpMode() throws InterruptedException {
        zoom.init(hardwareMap, telemetry);
        zoom.drivetrain.setPower(.3);
        zoom.drivetrain.setTelemetry(telemetry);
        zoom.drivetrain.useBrake(true);
        zoom.outtake.neutralPosition();

        y = new Button();
        a = new Button();
        dpad_up = new Button();
        dpad_down = new Button();

        telemetry.addData("Desc", "Tuner class to test going over the barrier")
                .addData("Power", "y/a to increase/decrease power")
                .addData("Inches", "dpad up/down to increase/decrease inches")
                .addData("Start", "Right bumper to confirm this message, and start the auto");
        telemetry.update();

        waitForStart();

        while (!gamepad1.right_bumper) {
            y.previous();
            y.setState(gamepad1.y);

            a.previous();
            a.setState(gamepad1.a);

            dpad_up.previous();
            dpad_up.setState(gamepad1.dpad_up);

            dpad_down.previous();
            dpad_down.setState(gamepad1.dpad_down);

            if (y.isPressed()) power += 0.05;
            if (a.isPressed()) power -= 0.05;
            if (power > 1) power = 1;
            if (power < -1) power = -1;
            if (dpad_up.isPressed()) inches += 5;
            if (dpad_down.isPressed()) inches -= 1;
            if (inches < 0) inches = 0;
            telemetry.addData("Power", power);
            telemetry.addData("Inches", inches);
            telemetry.update();
        }

        telemetry.addData("Confirmed Settings", "");
        telemetry.addData("Power", power);
        telemetry.addData("Inches", inches);
        telemetry.update();

        zoom.drivetrain.setPower(power);

        //Line Up With Second Panel from Right, Left Seam
        while (opModeIsActive()) {
            //forward
            zoom.drivetrain.backward(inches);
            sleep(3000);
            break;
            //LETS FUCKING GO
        }
    }
}
