package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Date: 1/28/21
 * Author: Jacob Marinas
 * The drivetrain subsystem
 */
public class Drivetrain implements Constants {

    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private double power;
    public int fLTickGoal, fRTickGoal, bLTickGoal, bRTickGoal;
    public Telemetry telem;
    public IMU imu;
    private FtcDashboard dashboard;

    public enum DrivetrainState {
        NEUTRAL, START_HUB, TURN_ONE, TURN_TWO, END_HUB,
        START_SHARED, TURN_SHARED, END_SHARED
    }

    private DrivetrainState drivetrainState = DrivetrainState.NEUTRAL;

    /**
     * Defines the parts needed for the subsystem
     * @param fL
     * @param fR
     * @param bL
     * @param bR
     */
    public Drivetrain(DcMotor fL, DcMotor fR, DcMotor bL, DcMotor bR) {
        this.frontLeft = fL;
        this.frontRight = fR;
        this.backLeft = bL;
        this.backRight = bR;

        init();
    }

    /**
     * Sets the direction of the motors and sets their power to 0
     */
    private void init() {
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        power = STOP;
    }

    public void setTelemetry(Telemetry telem) {
        this.telem = telem;
    }

    public void setIMU(IMU imu) { this.imu = imu; }

    /**
     * @param use RUN_USING_ENCODER
     */
    public void useEncoders(boolean use) {
        if (use) {
            setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else {
            setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    /**
     * @param use ZeroPowerBehavior.BRAKE
     */
    public void useBrake(boolean use) {
        if (use) {
            setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else {
            setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }

    /**
     * @param zpb for all motors
     */
    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zpb) {
        frontLeft.setZeroPowerBehavior(zpb);
        frontRight.setZeroPowerBehavior(zpb);
        backLeft.setZeroPowerBehavior(zpb);
        backRight.setZeroPowerBehavior(zpb);
    }

    /**
     * @param runMode for all motors
     */
    public void setRunMode(DcMotor.RunMode runMode) {
        frontLeft.setMode(runMode);
        frontRight.setMode(runMode);
        backLeft.setMode(runMode);
        backRight.setMode(runMode);
    }

    /**
     * @param fL target pos
     * @param fR target pos
     * @param bL target pos
     * @param bR target pos
     */
    public void setTargetPos(int fL, int fR, int bL, int bR) {
        frontLeft.setTargetPosition(fL);
        frontRight.setTargetPosition(fR);
        backLeft.setTargetPosition(bL);
        backRight.setTargetPosition(bR);
    }

    /**
     * @param power that the drivetrain will operate at
     */
    public void setPower(double power) { this.power = power; }

    /**
     * @param fL power
     * @param bL power
     */
    public void setLeftSide(double fL, double bL) {
        frontLeft.setPower(fL);
        backLeft.setPower(bL);
    }

    /**
     * @param fR power
     * @param bR power
     */
    public void setRightSide(double fR, double bR) {
        frontRight.setPower(fR);
        backRight.setPower(bR);
    }

    /**
     * @param fL power
     * @param fR power
     * @param bL power
     * @param bR power
     */
    public void setBase(double fL, double fR, double bL, double bR) {
        setLeftSide(fL, bL);
        setRightSide(fR, bR);
    }

    /**
     * @param power
     */
    public void setBase(double power) {
        setBase(power, power, power, power);
    }

    /**
     * Basic forward movement by power variable
     */
    public void forward() {
        setBase(-power, -power, -power, -power);
    }

    /**
     * Basic backward movement by power variable
     */
    public void backward() {
        setBase(power, power, power, power);
    }

    /**
     * Sets all motors to 0
     * @param
     */
    public void stop() {
        setBase(STOP, STOP, STOP, STOP);
    }

    /**
     * Goes forward x inches by power variable
     * @param inches distance
     */
    public void forward(double inches) {
        drive(inches, inches, inches, inches, power, power, power, power);
    }

    /**
     * Goes backward x inches by power variable
     * @param inches distance
     */
    public void backward(double inches) {
        drive(-inches, -inches, -inches, -inches, power, power, power, power);
    }

    /**
     * Pivot turn in a certain distance by power variable
     * @param dir direction to turn
     * @param inches distance
     */
    public void pivotTurn(Status dir, double inches) {
        switch (dir) {
            case LEFT:
                drive(-inches, -inches, -inches, -inches, 0, power, 0, power);
                break;
            case RIGHT:
                drive(-inches, -inches, -inches, -inches, power, 0, power, 0);
                break;
        }
    }

    /**
     * Point turn in a certain distance by power variable
     * @param dir direction to turn
     * @param inches distance
     */
    public void pointTurn(Status dir, double inches) {
        switch (dir) {
            case LEFT:
                drive(-inches, inches, -inches, inches, power, power, power, power);
                break;
            case RIGHT:
                drive(inches, -inches, inches, -inches, power, power, power, power);
                break;
        }
    }

    // 19 at field. 18.5 at home
    // 96 mm: 12.7
    // 120 mm: 26.6
    public void pointTurnRight() {
        pointTurn(Status.RIGHT,  11.5);
    }

    // 19 at field. 18.5 at home
    public void pointTurnLeft() {
        pointTurn(Status.LEFT,  11.5);
    }

    /**
     * Basic point turn by power variable
     * @param dir direction to turn
     */
    public void pointTurn(Status dir) {
        switch (dir) {
            case LEFT:
                setBase(0, -power, 0, -power);
                break;
            case RIGHT:
                setBase(-power, 0, -power, 0);
                break;
        }
    }

    /**
     * Basic pivot turn by power variable
     * @param dir direction to turn
     */
    public void pivotTurn(Status dir) {
        switch (dir) {
            case LEFT:
                setBase(power, -power, power, -power);
                break;
            case RIGHT:
                setBase(-power, power, -power, power);
                break;
        }
    }

    /*
    Relative angle
     */
    public void turnIMU(double degrees) {

        imu.resetAngle();

        double startAngle = imu.getStartAngle().firstAngle;

        double goalAngle = degrees + startAngle;

        double error = goalAngle - startAngle;

        while (Math.abs(error) > 1) {
            double motorPower = (error > 0 ? -0.3 : 0.3 );
            setBase(motorPower, -motorPower, motorPower, -motorPower);
            error = goalAngle - imu.getAngle();
            telem.addData("startAngle", startAngle);
            telem.addData("error", error);
            telem.addData("currAngle", imu.getAngle());
            telem.update();
        }

        stop();
    }

    /*
    Absolute angle
     */
    public void turnToIMU(double degrees) {

        //Orientation orientation = imu.getIMU().getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double error = degrees - imu.getAngle();

        if (error > 180) {
            error -= 360;
        } else if (error < -180) {
            error += 360;
        }

        turnIMU(error);
    }

    /*
    Turning using PID
     */
    public void PIDTurn(double targetAngle) {

        //Telemetry dTelem = dashboard.getTelemetry();

        PIDController pid = new PIDController(0.014, 0.00003, 0.001, 20);
        imu.resetAngle();

        double startAngle = imu.getAngle();

        double goalAngle = targetAngle + startAngle;

        double correction = pid.output(goalAngle, imu.getAngle());

        ElapsedTime time = new ElapsedTime();
        time.reset();
        // May need to calculate slope, rise over run to stop it from oscillating
        while (time.milliseconds() < 1500) {
            correction = Math.max(Math.min(correction, 1), -1);
            setBase(-correction, correction, -correction, correction);
            correction = pid.output(goalAngle, imu.getAngle());
            telem.addData("targetAngle", goalAngle);
            telem.addData("goalAngle", imu.getAngle());
            telem.addData("correction", correction);
            telem.update();
        }

        stop();
    }

    public void PIDTurn(double targetAngle, PIDController pid) {

        //Telemetry dTelem = dashboard.getTelemetry();
        //telem = dashboard.getTelemetry();

        imu.resetAngle();

        double startAngle = imu.getAngle();

        double goalAngle = targetAngle + startAngle;

        double correction = pid.output(goalAngle, imu.getAngle());

        ElapsedTime time = new ElapsedTime();
        time.reset();
        // May need to calculate slope, rise over run to stop it from oscillating
        while (time.milliseconds() < 1200) {
            correction = Math.max(Math.min(correction, 1), -1);
            setBase(-correction, correction, -correction, correction);
            correction = pid.output(goalAngle, imu.getAngle());
            telem.addData("targetAngle", goalAngle);
            telem.addData("goalAngle", imu.getAngle());
            telem.addData("correction", correction);
            //telem.addData("targetAngle", goalAngle);
            //telem.addData("goalAngle", imu.getAngle());
            telem.update();
        }

        stop();
        telem.addData("targetAngle", goalAngle);
        telem.addData("goalAngle", imu.getAngle());
        telem.addData("correction", correction);
        telem.update();


    }

    /**
     * Encoder method for movement. Inches determine the distance for each motor. Power determines how fast the motors will go
     * @param fLInches
     * @param fRInches
     * @param bLInches
     * @param bRInches
     * @param fLPower
     * @param fRPower
     * @param bLPower
     * @param bRPower
     */
    public void drive(double fLInches, double fRInches, double bLInches, double bRInches, double fLPower, double fRPower, double bLPower, double bRPower) {
        fLTickGoal = (int) (TICKS_PER_IN * fLInches);
        fRTickGoal = (int) (TICKS_PER_IN * fRInches);
        bLTickGoal = (int) (TICKS_PER_IN * bLInches);
        bRTickGoal = (int) (TICKS_PER_IN * bRInches);
        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setTargetPos(fLTickGoal, fRTickGoal, bLTickGoal, bRTickGoal);
        setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
        setBase(fLPower, fRPower, bLPower, bRPower);
        while (allBusy()) {
            telem.addData("fL", frontLeft.getCurrentPosition())
                    .addData("fR", frontRight.getCurrentPosition())
                    .addData("bL", backLeft.getCurrentPosition())
                    .addData("bR", backRight.getCurrentPosition())
                    .addData("tick goal", fLTickGoal + " * " + TICKS_PER_IN)
                    .addData("fLTickGoal", fLTickGoal)
                    .addData("fRTickGoal", fRTickGoal)
                    .addData("bLTickGoal", bLTickGoal)
                    .addData("bRTickGoal", bRTickGoal);
            telem.update();
        }
        stop();
        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void strafe(double leftInches, double rightInches) {
        drive(leftInches, rightInches, -leftInches, -rightInches, power, power, power, power);
    }

    public void strafeLeft(double inches) {
        strafe(inches, inches);
    }

    public void strafeRight(double inches) {
        strafe(-inches, -inches);
    }

    /**
     * @return true if all motors are busy, false otherwise
     */
    public boolean allBusy() { return frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy(); }

    /**
     * @return true if any motor is busy, false otherwise
     */
    public boolean anyBusy() { return frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backRight.isBusy();}

    /**
     * @return power variable that drivetrain moves by
     */
    public double getPower() { return power; }

    public DcMotor getFrontLeft() { return frontLeft; }

    public DcMotor getFrontRight() { return frontRight; }

    public DcMotor getBackLeft() { return backLeft; }

    public DcMotor getBackRight() { return backRight; }

    public void setState(DrivetrainState state) { drivetrainState = state; }

    public DrivetrainState getState() {
        return drivetrainState;
    }

    public void setDashboard(FtcDashboard dash) {
        this.dashboard = dash;
    }
}
