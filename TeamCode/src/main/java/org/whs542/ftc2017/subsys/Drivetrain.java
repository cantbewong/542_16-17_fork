package org.whs542.ftc2017.subsys;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Amar2 on 10/22/2016.
 */
public class Drivetrain {

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    public Drivetrain (HardwareMap driveMap){

        frontRight = driveMap.dcMotor.get("frontRight");
        frontLeft = driveMap.dcMotor.get("frontLeft");
        backRight = driveMap.dcMotor.get("backRight");
        backLeft = driveMap.dcMotor.get("backLeft");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

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
    //Moves a certain distance forwards or backwards using encoders. Negative = backwards.
    public void moveDistanceMilli(double distance){
        
    }
    //Turns the robot with the center as the center of rotation. Positive = turn clockwise; negative = turn counterclockwise.
    public void turn(double degrees){

    }







}

