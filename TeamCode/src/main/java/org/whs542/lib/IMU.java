package org.whs542.lib;

/**
 * Created by Jiangda on 10/29/2016.
 */
import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.hardware.adafruit.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.internal.AppUtil;

import java.io.File;
public class IMU {

    BNO055IMU imu;

    public IMU(HardwareMap theMap){
        imu = theMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        File file = AppUtil.getInstance().getSettingsFile("AdafruitIMUCalibration.json");
        parameters.calibrationDataFile = ReadWriteFile.readFile(file);
        //parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu.initialize(parameters);
    }
    //Returns the heading, a value from 0 to 360 degrees.
    public double getHeading(){
        double heading = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.XYZ).thirdAngle;
        return heading;
    }

    //Returns the magnitude of the acceleration, not the direction.
    public double getAccelerationMag(){
        double xAccel = imu.getLinearAcceleration().xAccel;
        double yAccel = imu.getLinearAcceleration().yAccel;
        double zAccel = imu.getLinearAcceleration().zAccel;

        double accelMag =
        Math.sqrt(
            Math.pow( xAccel, 2 ) + Math.pow( yAccel, 2 ) + Math.pow( zAccel, 2 )
        );
        return accelMag;
    }
}

