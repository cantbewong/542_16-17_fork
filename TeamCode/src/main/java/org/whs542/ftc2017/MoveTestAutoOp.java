package org.whs542.ftc2017;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.whs542.ftc2017.subsys.WHSRobot;
import org.whs542.lib.IMU;
import org.whs542.lib.Vuforia;

/**
 * Created by Jiangda on 11/4/2016.
 */

@Autonomous( name = "MoveTestAutoOp", group = "test" )
public class MoveTestAutoOp extends WHSParentAutoOp{

    Vuforia vuforia;
    IMU imu;
    WHSRobot whsRobot;

    int caseNumber;
    @Override
    public void init()
    {
        //super.init();
        vuforia = new Vuforia();
        DbgLog.msg("Vuforia init");
        telemetry.addData("Vuforia Init", 1);
        imu = new IMU(hardwareMap);
        DbgLog.msg("IMU init");
        telemetry.addData("IMU Init", 1);
        whsRobot = new WHSRobot(hardwareMap);
        DbgLog.msg("Rbt init");
        telemetry.addData("RBT Init", 1);
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
