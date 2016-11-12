package org.whs542.lib;

/**
 * Created by Lucy Wang on 11/11/2016.
 */

public class Functions
{
    public static double calculateDistance(Coordinate current, Coordinate target)
    {
        double distance;
        distance = Math.sqrt(Math.pow(target.returnCoordSingleValue("x") - current.returnCoordSingleValue("x"), 2) +
                    Math.pow(target.returnCoordSingleValue("y") - current.returnCoordSingleValue("y"), 2));
        return distance;
    }

    public static double calculateTurningAngle(Coordinate current, Coordinate target)
    {
        double difference = target.returnCoordSingleValue("orientation") - current.returnCoordSingleValue("orientation");
        if(Math.abs( difference ) > 180)
        {
            if(difference < 0)
            {
                difference += 360;
            }
            else
            {
                difference -= 360;
            }
        }
        return difference;
    }


}
