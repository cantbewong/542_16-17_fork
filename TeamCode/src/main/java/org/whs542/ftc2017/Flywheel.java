package org.whs542.ftc2017;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.whs542.lib.Toggler;

/**
 * Created by Jiangda on 10/1/2016.
 */
public class Flywheel {

    private DcMotor rightFly;
    private DcMotor leftFly;
    private boolean status;
    double power = 1;                   //Defualt power level. Can be changed via the alternate contructor, or using the method setPower

    private Toggler flyToggler = new Toggler(2);

   //HardwareMap: basically tells the program where each device is located on the robot. The name in argument(e.g. "rightFly") should correspond exactly to the name on the phone.
    public Flywheel(HardwareMap aMap){
        rightFly = aMap.dcMotor.get("rightFly");
        leftFly = aMap.dcMotor.get("leftFly");
        status = false;
    }
    public Flywheel(HardwareMap aMap, double powerIn){
        rightFly = aMap.dcMotor.get("rightFly");
        leftFly = aMap.dcMotor.get("leftFly");
        status = false;
    }

    //The core of the flywheel program. Spins flywheels if right bumper is pressed, stops spinning if pressed again.
    public void run(boolean b1){
        flyToggler.changeState(b1);
        if(flyToggler.currentState() == 1){
            status = true;
            rightFly.setPower(-power);
            leftFly.setPower(power);
        }
        else{
            status = false;
            rightFly.setPower(0);
            leftFly.setPower(0);
        }
    }
    public String getStatus(){
        if(status)
            return "spinning";
        else
            return "not spinning";
    }

    public void setPower(double power){
        this.power = power;
    }

}
