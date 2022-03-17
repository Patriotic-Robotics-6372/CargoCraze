package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.Locale;

public class IMU {

    private BNO055IMU imu;

    private Orientation angles;
    private Acceleration gravity;

    private Orientation lastAngles;
    private double currAngle;

    private Orientation startAngle;

    public IMU(BNO055IMU imu) {
        this.imu = imu;
        BNO055IMU.Parameters param = new BNO055IMU.Parameters();
        param.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        param.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        param.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        param.loggingEnabled      = true;
        param.loggingTag          = "IMU";
        param.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        initialize(param);
    }

    /**
     * @param parameters to initialize
     */
    public void initialize(BNO055IMU.Parameters parameters) {
        imu.initialize(parameters);
    }

    /**
     * @return Orientation object of angles
     */
    public Orientation getAngles() {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return angles;
    }

    /**
     * Update angles to orientation object of angles
     */
    public void updateAngles() {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    }

    /**
     * @return string of first angle
     */
    public String getFirstAngle() {
        return formatAngle(angles.angleUnit, angles.firstAngle);
    }

    /**
     * @return string of second angle
     */
    public String getSecondAngle() {
        return formatAngle(angles.angleUnit, angles.secondAngle);
    }

    /**
     * @return stringof third angle
     */
    public String getThirdAngle() {
        return formatAngle(angles.angleUnit, angles.thirdAngle);
    }

    /**
     * @return float of first angle
     */
    public float getFirstAngleNum() {
        return Float.parseFloat(getFirstAngle());
    }

    /**
     * @return float of second angle
     */
    public float getSecondAngleNum() {
        return Float.parseFloat(getSecondAngle());
    }

    /**
     * @return float of third angle
     */
    public float getThirdAngleNum() {
        return Float.parseFloat(getThirdAngle());
    }

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

    public void resetAngle() {
        startAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        lastAngles = startAngle;
    }

    public double getAngle() {

        Orientation orientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = orientation.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180) {
            deltaAngle += 360;
        } else if (deltaAngle > 180) {
            deltaAngle -= 360;
        }

        currAngle += deltaAngle;
        lastAngles = orientation;

        return currAngle;
    }

    public Orientation getStartAngle() {
        return startAngle;
    }

    /**
     * @return the imu
     */
    public BNO055IMU getIMU() {
        return imu;
    }
}