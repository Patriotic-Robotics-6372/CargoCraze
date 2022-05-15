package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Button;
import org.firstinspires.ftc.teamcode.hardware.Carousel;

@Disabled
@TeleOp (name = "TestCarouselTuner", group = "PRTest")
public class TestCarouselTuner extends LinearOpMode {

    Carousel carousel;

    Button left_bumper, right_bumper, a, dpad_left, dpad_right;

    double power = 0;
    boolean aToggle = false;
    boolean dpLeftToggle = false;
    boolean dpRightToggle = false;

    ElapsedTime time;
    double startSpin;

    @Override
    public void runOpMode() throws InterruptedException {
        carousel = new Carousel(hardwareMap.dcMotor.get("leftCarousel"), hardwareMap.dcMotor.get("rightCarousel"));

        left_bumper = new Button();
        right_bumper = new Button();
        a = new Button();
        dpad_left = new Button();
        dpad_right = new Button();

        time = new ElapsedTime();

        telemetry.addData("Desc", "Carousel Tuner class");
        telemetry.addData("How to Use", "GP1 l/r bumper: decrease/increase power")
                .addData("GP1 a", "Toggle carousel")
                .addData("GP1 dpad l/r", "Gradual build in power");
        telemetry.update();
        telemetry.setMsTransmissionInterval(20);
        waitForStart();
        time.reset();
        while (opModeIsActive()) {
            left_bumper.previous();
            left_bumper.setState(gamepad1.left_bumper);

            right_bumper.previous();
            right_bumper.setState(gamepad1.right_bumper);

            a.previous();
            a.setState(gamepad1.a);

            if (left_bumper.isPressed()) power -= 0.05;

            if (right_bumper.isPressed()) power += 0.05;

            dpad_left.previous();
            dpad_left.setState(gamepad1.dpad_left);

            dpad_right.previous();
            dpad_right.setState(gamepad1.dpad_right);

            /*
            if (dpad_left.isPressed()) {
                dpLeftToggle = !dpLeftToggle;
                power = -0.4;
            }

            if (dpad_right.isPressed()) {
                dpRightToggle = !dpRightToggle;
                power = 0.4;
            }

            if (dpLeftToggle) {
                startSpin = time.milliseconds();
                if (time.milliseconds() % 250 > 150) power *= 1.04;
            } else if (dpRightToggle) {
                if (time.milliseconds() % 250 > 150) power *= 1.04;
            } else {
                power = 0;
            }
             */

            if (dpad_left.isPressed()) {
                power = -0.4;
                startSpin = time.milliseconds();
                while (time.milliseconds() < startSpin + 1400) {
                    if (time.milliseconds() % 250 > 150) power *= 1.04;
                    if (power > 1) power = 1.0;
                    carousel.spin(power);
                }
                carousel.spin(0);
            }

            /*
            if (power > 1) {
                power = 1.0;
            } else if (power < -1) {
                power = -1.0;
            }


            if (a.isPressed()) aToggle = !aToggle;

            if (aToggle) {
                carousel.spin(power);
            } else {
                carousel.stopSpin();
            }
             */

            telemetry.addData("power", power);
            telemetry.addData("a toggle", aToggle);
            telemetry.addData("dpad_left toggle", dpLeftToggle);
            telemetry.addData("dpad_right toggle", dpRightToggle);
            telemetry.update();
        }
    }
}
