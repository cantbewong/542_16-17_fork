package org.whs542.ftc2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Jiangda on 11/4/2016.
 */

@Autonomous( name = "MoveTestAutoOp", group = "test" )
public class MoveTestAutoOp extends WHSParentAutoOp{

    @Override
    public void init(){
        super.init();
    }

    @Override
    public void loop(){
        whsRobot.drivetrain.moveDistanceMilli( 1000, imu );
    }

}
