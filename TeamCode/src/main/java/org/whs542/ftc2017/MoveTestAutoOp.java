package org.whs542.ftc2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Jiangda on 11/4/2016.
 */

@Autonomous( name = "MoveTestAutoOp", group = "test" )
public class MoveTestAutoOp extends WHSParentAutoOp{

    int caseNumber;
    @Override
    public void init()
    {
        super.init();
        caseNumber = 0;
    }

    @Override
    public void loop(){
        while (caseNumber == 0) {
            whsRobot.drivetrain.moveDistanceMilli(1000, imu);
            caseNumber = 1;
        }
    }

}
