package org.whs542.lib;

/**
 * Created by Jiangda on 10/16/2016.
 */

import com.qualcomm.ftccommon.DbgLog;
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

import org.whs542.lib.Coordinate;

import java.util.ArrayList;
import java.util.List;
public class Vuforia {

    public static final String TAG = "Vuforia AutoOp";

    VuforiaLocalizer vuforia;

    List<VuforiaTrackable> allTrackables;
    VuforiaTrackables ftcTargets;

    float mmPerInch        = 25.4f;
    float mmBotWidth       = 18 * mmPerInch;            // ... or whatever is right for your robot
    //float mmFTCFieldWidth  = (12*12 - 2) * mmPerInch;   // the FTC field is ~11'10" center-to-center of the glass panels
    float tileWidthMM = 600;
    float mmFTCFieldWidth = 6 * tileWidthMM;
    float imageHtMM = 150;
    /**
     * Initializes Vuforia, using the phone's front camera and with the four vision targets used in Velocity Vortex.
     */
    public Vuforia(){

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(com.qualcomm.ftcrobotcontroller.R.id.cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AcHvLjn/////AAAAGXiaYd8sQUWoodQdUe6EkVh5In4npcgPENX3TMz43hlk9g7Xe4JzvNU8g9W4esItJjBtwkoCJVn1vT28VzK1SEd96YjzpbBgL3zubmG9pCqnxMawGUdiIP19mwl4cWACtqAPH5lV2cccLUmFou4RsBDdhwajo1imLuLphy4auD0IwyV+Pcp7+gAg0LCnZ2A3UX9nsPjGWKEs8REy0pCw37Nl1K3t670ivSSxkfo/iF71IxhUkE+W+GaJZ/JFw1WL6m8i0qgrWWSJg3zfwx9jSRZRAXYdM9crg+edoin2Wmkaw69PTiD7pJDiWfjjb+1z1rewEZGxf1i8WTLWskvO76xZ0coIFlbVSwl8YMNaiPrh";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        ftcTargets = this.vuforia.loadTrackablesFromAsset("FTC_2016-17");
        VuforiaTrackable wheels = ftcTargets.get(0);
        wheels.setName("Wheels");

        VuforiaTrackable gears = ftcTargets.get(1);
        gears.setName("Gears");

        VuforiaTrackable tools = ftcTargets.get(2);
        tools.setName("Tools");

        VuforiaTrackable legos = ftcTargets.get(3);
        legos.setName("Legos");

        // List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(ftcTargets);

        OpenGLMatrix wheelsTargetLocationOnField = OpenGLMatrix
                .translation(0.5f * tileWidthMM, mmFTCFieldWidth/2, imageHtMM)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 0, 0));
        wheels.setLocation(wheelsTargetLocationOnField);
        RobotLog.ii(TAG, "Wheels Target=%s", format(wheelsTargetLocationOnField));

        OpenGLMatrix legosTargetLocationOnField = OpenGLMatrix
                .translation(-1.5f * tileWidthMM, mmFTCFieldWidth/2, imageHtMM)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 0, 0));
        legos.setLocation(legosTargetLocationOnField);
        RobotLog.ii(TAG, "Legos Target=%s", format(legosTargetLocationOnField));

        OpenGLMatrix gearsTargetLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth/2, -0.5f * tileWidthMM, imageHtMM)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES,90, 0, 90 ));
        gears.setLocation(gearsTargetLocationOnField);
        RobotLog.ii(TAG, "Gears Target=%s", format(gearsTargetLocationOnField));

        OpenGLMatrix toolsTargetLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth/2, 1.5f * tileWidthMM, imageHtMM)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, 90, 0, 90));
        tools.setLocation(toolsTargetLocationOnField);
        RobotLog.ii(TAG, "Tools Target=%s", format(toolsTargetLocationOnField));

        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(mmBotWidth/2,0,0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.YZY,
                        AngleUnit.DEGREES, -90, 0, 0));
        RobotLog.ii(TAG, "phone=%s", format(phoneLocationOnRobot));

        ((VuforiaTrackableDefaultListener)wheels.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)gears.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)tools.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)legos.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
    }

    /**
     * Returns a Coordinate class containing the positional information of the robot in relation to the field. Coordinate x, y and z values of 0, 0, 0
     * corresponds to the center of the field. See FTCFieldCoordinateSystemDefinition in FTC 16-17 repo doc/tutorial folder for more info.
     * If Vuforia cannot determine the heading and of the robot, the x, y and z values of Coordinate are all 10000.
     * The first, second, and third values in the xyzCoords [] correspond to x, y, and z coordinates, respectively.
     */
    public Coordinate getHeadingAndLocation(){
        ftcTargets.activate();

        float[] xyzCoords = {10000, 10000, 10000};

        double heading = 10000;
        Orientation robotOrientation;

        for(VuforiaTrackable trackable : allTrackables){

            OpenGLMatrix robotLocationTransform = null;
            while( robotLocationTransform == null ){
                robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
            }
            if(robotLocationTransform != null) {
                xyzCoords = robotLocationTransform.getTranslation().getData();

                robotOrientation = Orientation.getOrientation(robotLocationTransform, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, Orientation.AngleSet.THEONE);
                heading = robotOrientation.thirdAngle;
                DbgLog.msg("%f, %f, %f, %f", xyzCoords[0], xyzCoords[1], xyzCoords[2], heading);
            }
            else {}
        }
        ftcTargets.deactivate();
        return new Coordinate(xyzCoords[0], xyzCoords[1], xyzCoords[2], vuforiaAngleConverter(heading));
    }

    //Converts vuforia angle to absolute angle value. Vuforia gives values from -180 t0 180; this method
    //Converts it to 0 to 360. 0 = +x
    public static double vuforiaAngleConverter( double degrees ){

        if(degrees < 0 && degrees >= -180){
            degrees = degrees + 360;
        }
        return degrees;
    }
    /**
     * Returns the heading of the robot, in degrees. 


    public float getHeading(){
        float heading = 10000;

        for(VuforiaTrackable trackable : allTrackables){
            OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
            if(robotLocationTransform != null) {

            }
        }
        return heading;
    }
     */

    String format(OpenGLMatrix transformationMatrix) {
        return transformationMatrix.formatAsTransform();
    }

}
