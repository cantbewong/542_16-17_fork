package org.whs542.ftc2017;

/**
 * Created by Jiangda on 10/31/2016.
 */
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.whs542.ftc2017.subsys.WHSRobot;
import org.whs542.lib.IMU;
import org.whs542.lib.Vuforia;
import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class WHSParentAutoOp extends OpMode {
    Vuforia vuforia;
    IMU imu;
    WHSRobot whsRobot;

    @Override
    public void init(){
        vuforia = new Vuforia();
        imu = new IMU();
        whsRobot = new WHSRobot(hardwareMap);
    }

    public abstract void loop();

}
