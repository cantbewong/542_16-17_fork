package org.whs542.ftc2017.subsys;

/**
 * Created by Jiangda on 10/30/2016.
 */
import com.qualcomm.robotcore.eventloop.opmode.*;

import org.whs542.lib.IMU;
import org.whs542.lib.Vuforia;

@Autonomous(name = "AutoOpTest", group = "AutoOp")
public class TestAutoOp extends OpMode{
    WHSRobot robot;
    //IMU imu;
    //Vuforia vuforia;

    public void init() {
        robot = new WHSRobot(hardwareMap);
        //imu = new IMU(hardwareMap);
        //vuforia = new Vuforia();

    }

    public void loop() {

    }
}


