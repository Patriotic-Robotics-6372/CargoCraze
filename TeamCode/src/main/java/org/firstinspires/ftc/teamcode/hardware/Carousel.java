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

public class Carousel implements Constants {

   private DcMotor leftCarousel, rightCarousel;
   private double power;

   public enum CarouselState {
       IDLE, SPIN_INCREASE, SPIN_MAX
   }

   private Status carouselSide = Status.RIGHT;

   private CarouselState carouselState = CarouselState.IDLE;

   private ElapsedTime carouselTimer;

   public Carousel(DcMotor Lc, DcMotor Rc) {
       this.leftCarousel = Lc ;
       this.rightCarousel = Rc ;

       leftCarousel.setDirection(DcMotorSimple.Direction.REVERSE);
       rightCarousel.setDirection(DcMotorSimple.Direction.REVERSE);
   }

   //Different spins depending on what side of the field we are on

   public void rightSpin(double rotation) {
       rightCarousel.setPower(-rotation);
   }

   public void leftSpin(double rotation) {
       leftCarousel.setPower(rotation);
   }

   public void stopSpin() {
       spin(0);
   }

   public void spin(double c) {
       leftCarousel.setPower(c);
       rightCarousel.setPower(c);
   }

   public void powerSpin() {
       switch (carouselSide) {
           case LEFT:
               leftCarousel.setPower(power);
               break;
           case RIGHT:
               rightCarousel.setPower(power);
       }
   }

   public void increaseSpin() {
       if (carouselTimer.milliseconds() % Constants.SPIN_RATE_MS > Constants.SPIN_RATE_MS - 20) power *= Constants.SPIN_RATE_MULT;
       if (power > 1) power = 1;
       if (power < -1) power = -1;
   }

   public void resetSpin() {
       switch (carouselSide) {
           case RIGHT:
               power = Constants.SPIN_RATE_START;
               break;
           case LEFT:
               power = -(Constants.SPIN_RATE_START);
       }
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

   public DcMotor getLeftCarousel() {
       return leftCarousel;
   }

   public DcMotor getRightCarousel() {
       return rightCarousel;
   }

   public CarouselState getState() {
       return carouselState;
   }

   public ElapsedTime getTimer() {
       return carouselTimer;
   }

   public double getPower() {
       return leftCarousel.getPower();
   }

   public double getPowerVar() { return power; }

   public Status getSide() {
       return carouselSide;
   }
}
