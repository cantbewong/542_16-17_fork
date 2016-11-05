package org.whs542.ftc2017.subsys;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.whs542.lib.Coordinate;
import org.whs542.lib.Toggler;

/**
 * Created by Jiangda on 10/1/2016.
 */
public class Flywheel {

    private DcMotor rightFly;
    private DcMotor leftFly;

    private Servo flywheelGate;
    //private Servo particleRelease;

    private boolean status;
    private boolean gateStatus;
    private static double MAX_SPEED = 4000; //ticks per sec

    private Coordinate currentPosition;
    private Coordinate Vortex=new Coordinate(304.8,304.8,304.8,1);

    double power = 1;                   //Defualt power level. Can be changed via the alternate contructor, or using the method setPower

    private Toggler flyToggler = new Toggler(2);
    private Toggler gateToggler = new Toggler(2);

    //HardwareMap: basically tells the program where each device is located on the robot. The name in argument(e.g. "rightFly") should correspond exactly to the name on the phone.
    public Flywheel(HardwareMap aMap){
        rightFly = aMap.dcMotor.get("rightFly");
        leftFly = aMap.dcMotor.get("leftFly");
        flywheelGate = aMap.servo.get("flywheelGate");
        status = false;
        rightFly.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFly.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //particleRelease = aMap.servo.get("particleRelease");
    }



    //The core of the flywheel program. Spins flywheels if right bumper is pressed, stops spinning if pressed again.
    public void run(boolean b1){
        flyToggler.changeState(b1);
        if(flyToggler.currentState() == 1){
            status = true;
            rightFly.setPower(power);
            leftFly.setPower(-power);
        }
        else{
            status = false;
            rightFly.setPower(0);
            leftFly.setPower(0);
        }
    }

    public void run(boolean b1, double powerIn){
        flyToggler.changeState(b1);
        if(flyToggler.currentState() == 1){
            status = true;
            rightFly.setPower(powerIn);
            leftFly.setPower(-powerIn);
        }
        else{
            status = false;
            rightFly.setPower(0);
            leftFly.setPower(0);
        }
    }

    public void operateGate(boolean a){
        gateToggler.changeState(a);

        if (gateToggler.currentState() == 1){
            flywheelGate.setPosition(1.0);
            gateStatus = true;
        }
        else{
            flywheelGate.setPosition(0.0);
            gateStatus = false;
        }
    }

    public String getStatus(){
        if(status)
            return "spinning";
        else
            return "not spinning";
    }

    public String getGateStatus(){
        if(gateStatus)
            return "notdefault";
        else
            return "default";
    }

    public void setPower(double power){
        this.power = power;
    }

    /*
    public void releaseParticle(boolean b2){
        particleRelease.setPosition(120);
        particleRelease.setPosition(0);
    }
    */

    public double findPower(){
        //current position = use vuforia, take picture
        //calculate distance
        rightFly.setMaxSpeed((int) MAX_SPEED);
        double velocity = 1; //distance/ constant if it's linear, ticks per second
        power = velocity/MAX_SPEED;
        return power;
        //https://ftc-tricks.com/dc-motors/
    }


    /*
    public void shoot(boolean b1, boolean b2, double joystick){

        if (b1){
            //thread.sleep
            //target position face vortex
            setPower(findPower());
            run(b1);
            boolean loop = true;
            while (loop) {
                if (rightFly.getPower() == power && b2) {
                    releaseParticle(b2);
                }
                else if (b1){
                    loop = false;
                }
                else if (joystick != 0 ){
                    loop = false;
                }
            }
        }

    }
    */


}
