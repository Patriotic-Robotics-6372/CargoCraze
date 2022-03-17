package org.firstinspires.ftc.teamcode.hardware;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;

public class SensorColor {

    ColorSensor colorSensor;
    float hsvValues[] = {0F,0F,0F};
    boolean led;

    public SensorColor(ColorSensor cS) {
        this.colorSensor = cS;
    }

    public ColorSensor getColorSensor() {
        return colorSensor;
    }
    //ur doing great
    /**
     * Turns on the light on the color sensor
     */
    public void turnOnLED() {
        led = true;
        colorSensor.enableLed(led);
    }

    /**
     * Turns off the light on the colorsensor
     */
    public void turnOffLED() {
        led = false;
        colorSensor.enableLed(led);
    }

    /**
     * @return state of light
     */
    public boolean getLEDState() {
        return led;
    }

    /**
     * @return red value
     */
    public int getRed() {
        return colorSensor.red();
    }

    /**
     * @return green value
     */
    public int getGreen() {
        return colorSensor.green();
    }

    /**
     * @return blue value
     */
    public int getBlue() {
        return colorSensor.blue();
    }

    /**
     * @return alpha value
     */
    public int getAlpha() {
        return colorSensor.alpha();
    }

    /**
     * @return hue value
     */
    public float getHue() {
        Color.RGBToHSV(getRed() * 8, getGreen() * 8, getBlue() * 8, hsvValues);
        return hsvValues[0];
    }
}
