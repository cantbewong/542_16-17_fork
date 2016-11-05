package org.whs542.ftc2017.subsys;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.*;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.whs542.lib.Coordinate;
import org.whs542.lib.Vuforia;
import org.whs542.lib.IMU;

/**
 * Created by Amar2 on 10/22/2016.
 */
public class Drivetrain {

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    //All measurements in millimeters because that is the unit Vuforia uses
    private final double RADIUS_OF_WHEEL = 96;
    private final double CIRC_OF_WHEEL = RADIUS_OF_WHEEL * 2 * Math.PI;
    private final double ENCODER_TICKS_PER_MM = 1120 / CIRC_OF_WHEEL;

    private final double MIN_POWER_VALUE = 0.15;


    public Drivetrain (HardwareMap driveMap){

        frontRight = driveMap.dcMotor.get("frontRight");
        frontLeft = driveMap.dcMotor.get("frontLeft");
        backRight = driveMap.dcMotor.get("backRight");
        backLeft = driveMap.dcMotor.get("backLeft");

        frontRight.setZeroPowerBehavior( ZeroPowerBehavior.BRAKE );
        frontLeft.setZeroPowerBehavior( ZeroPowerBehavior.BRAKE );
        backRight.setZeroPowerBehavior(ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(ZeroPowerBehavior.BRAKE);

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void setRunMode( RunMode theMode ){
        frontLeft.setMode(theMode);
        frontRight.setMode(theMode);
        backLeft.setMode(theMode);
        backRight.setMode(theMode);
    }

    public void setMaxSpeed(int maxSpeed){
        frontLeft.setMaxSpeed( maxSpeed );
        frontRight.setMaxSpeed( maxSpeed );
        backLeft.setMaxSpeed(maxSpeed);
        backRight.setMaxSpeed(maxSpeed);
    }

    public void setRightPower(double power){
        frontRight.setPower(power);
        backRight.setPower(power);
    }

    public void setLeftPower(double power){
        frontLeft.setPower(power);
        backLeft.setPower(power);
    }

    public void setRLPower(double leftPower, double rightPower){
        frontLeft.setPower(leftPower);
        backLeft.setPower(leftPower);
        frontRight.setPower(rightPower);
        backRight.setPower(rightPower);
    }

    //A set power method using the cubic function that Moses made
    //params left and right can just be joystick values
    public void setRLScaledPower(double leftPower, double rightPower){

        double rightScaledPower = -Math.pow(rightPower, 3);
        double leftScaledPower = -Math.pow(leftPower, 3);

        setRightPower(rightScaledPower);
        setLeftPower(leftScaledPower);
    }

    //Runs all four motors to a certain encoder position; holds all motors actively thereat
    public void setTargetPosition( int position ){
        frontLeft.setTargetPosition( position );
        frontRight.setTargetPosition( position );
        backLeft.setTargetPosition( position );
        backRight.setTargetPosition( position );
    }

    //Input: destination coordinate object(values are in millimeters)
    //Output: robot automatically moves to that point on the field.
    public void move( Coordinate destination, Vuforia vuforia, IMU imu){
        Coordinate current = vuforia.getHeadingAndLocation();

        double distanceSquared =
                Math.pow( destination.returnCoordSingleValue("x") - current.returnCoordSingleValue("x"), 2 ) +
                        Math.pow( destination.returnCoordSingleValue("y") - current.returnCoordSingleValue("y"), 2);

        double distance = Math.sqrt(distanceSquared);

        /**Problem: turn in the direction of the shorter angle. E.g. if degreesToTurn is 270, which
         * is a 270 degree turn counterclockwise, we want degreesToTurn to be -90, which is a
         * 90 degree turn clockwise.
         */
        /*double degreesToTurnFinal = destination.returnCoordSingleValue("orientation") - current.returnCoordSingleValue("orientation");

        if(degreesToTurnFinal >= 180){
            degreesToTurnFinal = 360 - degreesToTurnFinal;
        }
        else if( degreesToTurnFinal <= -180 ){
            degreesToTurnFinal = degreesToTurnFinal + 360;
        }*/

        //Distance to go in x and y units (mm) which can be positive or negative, from current to destination position
        double xPosToGo = destination.returnCoordSingleValue("x") - current.returnCoordSingleValue("x");
        double yPosToGo = destination.returnCoordSingleValue("y") - current.returnCoordSingleValue("y");
        //InitialOrientation is the initial heading that the robot aligns to before moving forward
        double initialOrientation;

        initialOrientation = 180 * Math.atan2( yPosToGo, xPosToGo ) / Math.PI;

        if( initialOrientation < 0 ){
            initialOrientation = 360 + initialOrientation;
        }
        //Turn robot to the desired orientation
        this.turn(initialOrientation, current.returnCoordSingleValue("heading"), imu);

        //Move robot forward the calculated distance, using IMU as check
        this.moveDistanceMilli(distance, imu);

        //Orient robot to destination orientation
        this.turn(destination.returnCoordSingleValue("heading"), initialOrientation, imu);

    }

    //Moves a certain distance forwards or backwards using encoders. Includes IMU as check. Negative = backwards.
    public void moveDistanceMilli(double distanceMM, IMU imu){
        int encoderPosition = (int) (distanceMM * ENCODER_TICKS_PER_MM);

        this.setRunMode( RunMode.STOP_AND_RESET_ENCODER );
        this.setRunMode(RunMode.RUN_TO_POSITION);
        this.setMaxSpeed(4000);
        this.setTargetPosition(encoderPosition);
        while( Math.abs( 0.5 * ( frontRight.getCurrentPosition() + backLeft.getCurrentPosition() ) )
            < Math.abs( encoderPosition )) {

            this.setRLPower(0.5, 0.5);
            //If the acceleration measured by the accelerometer exceeds a certain threshold, indicating
            //that the robot slammed into something, stop the robot.
            if( imu.getAccelerationMag() > 10.0 ){
                this.setRLPower(0, 0);
                System.exit(0);
            }
        }
    }

    //Tells the robot how much left (positive value) or right (negative) to turn based on the initial heading, from 0
    //to 359.9, and the final heading, also from 0 to 360. Accounts for the jump from 359.9 to 0.
    public void turn( double destinationDegrees, double currentDegrees, IMU imu){
        this.setRunMode( RunMode.RUN_WITHOUT_ENCODER );
        double difference = turnValue(destinationDegrees, currentDegrees);
        double differenceAbs = Math.abs( difference );
        double dir = difference / differenceAbs;

        boolean turning = true;
        double heading = imu.getHeading();
        double amtToTurn = this.turnValue(destinationDegrees, heading);
        //Stops turning when the robot is within 1.5 degrees of target heading
        if( Math.abs( amtToTurn ) < 1.5 ){
            turning = false;
        }
        //Turn robot loop
        while( turning ){
            //Don't worry about this line. It basically scales down the motor power so when the robot finishes
            //Turning it should be barely moving and not stop abruptly.
            this.setRLPower( -( 1 - MIN_POWER_VALUE ) * amtToTurn / differenceAbs - dir * MIN_POWER_VALUE,
            ( 1 - MIN_POWER_VALUE ) * amtToTurn / differenceAbs + dir * MIN_POWER_VALUE);

            heading = imu.getHeading();
            amtToTurn = this.turnValue(destinationDegrees, heading);
            //Stops turning when the robot is within 1.5 degrees of target heading
            if( Math.abs( amtToTurn ) < 1.5 ){
                turning = false;
            }
        }
    }

    public double turnValue( double destinationDegrees, double currentDegrees){
        double difference = destinationDegrees - currentDegrees;
        if( Math.abs( difference ) > 180){
            if( difference < 0){
                difference += 360;
            }
            else{
                difference -= 360;
            }
        }
        return difference;
    }

}

