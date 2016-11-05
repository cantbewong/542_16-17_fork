package org.whs542.ftc2017.subsys;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Amar2 on 10/22/2016.
 */
public class WHSRobot
{
    public Drivetrain drivetrain;
    //public Intake intake;
    //public Flywheel flywheel;

    public WHSRobot(HardwareMap robotMap){
        drivetrain = new Drivetrain(robotMap);
        //intake = new Intake(robotMap);
        //flywheel = new Flywheel(robotMap);
    }

}
