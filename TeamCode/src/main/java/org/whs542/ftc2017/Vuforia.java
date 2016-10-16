package org.whs542.ftc2017;

/**
 * Created by Jiangda on 10/16/2016.
 */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;
public class Vuforia {

    public static final String TAG = "Vuforia AutoOp";

    VuforiaLocalizer vuforia;

    List<VuforiaTrackable> allTrackables;

    float mmPerInch        = 25.4f;
    float mmBotWidth       = 18 * mmPerInch;            // ... or whatever is right for your robot
    float mmFTCFieldWidth  = (12*12 - 2) * mmPerInch;   // the FTC field is ~11'10" center-to-center of the glass panels


    public Vuforia(){

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(com.qualcomm.ftcrobotcontroller.R.id.cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AcHvLjn/////AAAAGXiaYd8sQUWoodQdUe6EkVh5In4npcgPENX3TMz43hlk9g7Xe4JzvNU8g9W4esItJjBtwkoCJVn1vT28VzK1SEd96YjzpbBgL3zubmG9pCqnxMawGUdiIP19mwl4cWACtqAPH5lV2cccLUmFou4RsBDdhwajo1imLuLphy4auD0IwyV+Pcp7+gAg0LCnZ2A3UX9nsPjGWKEs8REy0pCw37Nl1K3t670ivSSxkfo/iF71IxhUkE+W+GaJZ/JFw1WL6m8i0qgrWWSJg3zfwx9jSRZRAXYdM9crg+edoin2Wmkaw69PTiD7pJDiWfjjb+1z1rewEZGxf1i8WTLWskvO76xZ0coIFlbVSwl8YMNaiPrh";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables ftcTargets = this.vuforia.loadTrackablesFromAsset("FTC_2016-17");
        VuforiaTrackable wheels = ftcTargets.get(0);
        wheels.setName("Wheels");

        VuforiaTrackable gears = ftcTargets.get(1);
        gears.setName("Gears");

        VuforiaTrackable tools = ftcTargets.get(2);
        tools.setName("Tools");

        VuforiaTrackable legos = ftcTargets.get(3);
        legos.setName("Legos");

        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(ftcTargets);

        OpenGLMatrix wheelsTargetLocationOnField = OpenGLMatrix
                .translation(330.2F, mmFTCFieldWidth/2, 146.05F)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES,90, 0, 0));
        wheels.setLocation(wheelsTargetLocationOnField);
        RobotLog.ii(TAG, "Wheels Target=%s", format(wheelsTargetLocationOnField));

        OpenGLMatrix gearsTargetLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth/2, -330.2F, 146.05F)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES,90, 0, 90 ));
        gears.setLocation(gearsTargetLocationOnField);
        RobotLog.ii(TAG, "Gears Target=%s", format(gearsTargetLocationOnField));

        OpenGLMatrix toolsTargetLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth, 889, 146.05F)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 0, 90));
        tools.setLocation(toolsTargetLocationOnField);
        RobotLog.ii(TAG, "Tools Target=%s", format(toolsTargetLocationOnField));

        OpenGLMatrix legosTargetLocationOnField = OpenGLMatrix
                .translation(-889, mmFTCFieldWidth, 146.05F)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 0, 0));
        legos.setLocation(legosTargetLocationOnField);
        RobotLog.ii(TAG, "Legos Target=%s", format(legosTargetLocationOnField));

        ftcTargets.activate();

    }

    public double[] getLocation(){

        double[] xyzCoords = new double[3];

        for(VuforiaTrackable trackable : allTrackables){
            OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
            if(robotLocationTransform != null) {
                xyzCoords[0] = robotLocationTransform.get(0, 3);
                xyzCoords[1] = robotLocationTransform.get(1, 3);
                xyzCoords[2] = robotLocationTransform.get(2, 3);
            }
        }

        return xyzCoords;
    }

    public double getHeading(){
        double heading = 0;
        for(VuforiaTrackable trackable : allTrackables){
            OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
            if(robotLocationTransform != null) {
                String location = format(robotLocationTransform);
            }
        }
        return heading;
    }

    String format(OpenGLMatrix transformationMatrix) {
        return transformationMatrix.formatAsTransform();
    }

}
