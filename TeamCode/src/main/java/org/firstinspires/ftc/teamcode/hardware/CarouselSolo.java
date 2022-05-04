package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Author: Slimeafro
 * Date: 10.12.21
 * System: Carousel
 */

//Essentially an exact replica of the lift system with different variable and method names

public class CarouselSolo implements Constants {

    private DcMotor carousel;
    private double power;

    public enum CarouselState {
        IDLE, SPIN_INCREASE, SPIN_MAX
    }

    private Status carouselSide = Status.RIGHT;

    private CarouselState carouselState = CarouselState.IDLE;

    private ElapsedTime carouselTimer;

    public CarouselSolo(DcMotor c) {
        this.carousel = c ;

        carousel.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    //Different spins depending on what side of the field we are on

    public void spin(double rotation) {
        carousel.setPower(-rotation);
    }

    public void stopSpin() {
        spin(0);
    }

    public void powerSpin() {
        carousel.setPower(power);
    }

    public void increaseSpin() {
        if (carouselTimer.milliseconds() % Constants.SPIN_RATE_MS > Constants.SPIN_RATE_MS - 20) power *= Constants.SPIN_RATE_MULT;
        if (power > 1) power = 1;
        if (power < -1) power = -1;
    }

    public void resetSpin() {
        power = -(Constants.SPIN_RATE_START);
    }

    public void setPower(double power) {
        this.power = power;
    }

    public void setState(CarouselState state) {
        carouselState = state;
    }

    public void setTimer(ElapsedTime timer) {
        carouselTimer = timer;
    }

    public void setSide(Status side) {
        carouselSide = side;
    }

    public DcMotor getCarousel() {
        return carousel;
    }

    public CarouselState getState() {
        return carouselState;
    }

    public ElapsedTime getTimer() {
        return carouselTimer;
    }

    public double getPower() {
        return carousel.getPower();
    }

    public double getPowerVar() { return power; }

    public Status getSide() {
        return carouselSide;
    }
}
