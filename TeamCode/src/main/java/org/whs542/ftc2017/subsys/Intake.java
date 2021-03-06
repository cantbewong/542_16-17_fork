package org.whs542.ftc2017.subsys;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Amar2 on 10/22/2016.
 */
public class Intake {

    DcMotor intakeMotor;
    double defaultPower = 1.0;

    public Intake(HardwareMap intakeMap){
        intakeMotor = intakeMap.dcMotor.get("intake");
    }


    public void runIntake(boolean bumper, float trigger){
        if(bumper){
            intakeMotor.setPower(defaultPower);
        }
        else if(trigger>0.05){
            intakeMotor.setPower(-defaultPower);
        }
        else {
            intakeMotor.setPower(0.0);
        }
    }

    public void runIntake(){
        intakeMotor.setPower(defaultPower);
    }

    public void runIntake(boolean bumper, float trigger, double power){
        if(bumper){
            intakeMotor.setPower(power);
        }
        else if(trigger>0.05){
            intakeMotor.setPower(-power);
        }
        else {
            intakeMotor.setPower(0.0);
        }
    }

    public void runIntake(double power){
        intakeMotor.setPower(power);
    }

    public void setDefaultPower(double power){
        defaultPower = power;
    }

}
