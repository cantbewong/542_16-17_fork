package org.whs542.lib;

/**
 * Created by Jiangda on 10/22/2016.
 */
import org.whs542.lib.Coordinate;

public class Functions {

    public static void moveFunction(Coordinate destination){
        Vuforia vuforia = new Vuforia();
        Coordinate current = vuforia.getHeadingAndLocation();

        double distanceSquared =
        Math.pow( destination.returnCoordSingleValue("x") - current.returnCoordSingleValue("x"), 2 ) +
        Math.pow( destination.returnCoordSingleValue("y") - current.returnCoordSingleValue("y"), 2);

        double distance = Math.sqrt(distanceSquared);

        /**Problem: turn in the direction of the shorter angle. E.g. if degreesToTurn is 270, which
         * is a 270 degree turn counterclockwise, we want degreesToTurn to be -90, which is a
         * 90 degree turn clockwise.
         */
        /*double degreesToTurnFinal =
        destination.returnCoordSingleValue("orientation") - current.returnCoordSingleValue("orientation");

        if( degreesToTurnFinal >= 180 ){
            degreesToTurnFinal = 360 - degreesToTurnFinal;
        }
        else if( degreesToTurnFinal <= -180 ){
            degreesToTurnFinal = degreesToTurnFinal + 360;
        }*/

        //Distance to go in x and y units (mm) which can be positive or negative, from current to destination position
        double xPosToGo = destination.returnCoordSingleValue("x") - current.returnCoordSingleValue("x");
        double yPosToGo = destination.returnCoordSingleValue("y") - current.returnCoordSingleValue("y");

        double degreesToTurnInitial;

        degreesToTurnInitial = 180 * Math.atan2( yPosToGo, xPosToGo ) / Math.PI;

        if( degreesToTurnInitial < 0 ){
            degreesToTurnInitial = 360 + degreesToTurnInitial;
        }
        //Turn robot to the desired orientation
        //Move robot forward the calculated distance, using IMU as check
        //Orient robot to destination orientation






    }
    //Turns to an angle on the field(0 = x positive x axis, 180 = -x axis, 359 = right under +x axis)
    public static void TurnToExtrinsicOriention(double degrees){
        //Determine current heading using Vuforia
        //Coordinate turn using IMU
    }
}
