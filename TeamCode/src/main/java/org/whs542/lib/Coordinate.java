package org.whs542.lib;

/**
 * Created by Jiangda on 10/22/2016.
 */
public class Coordinate {
    private double xPos;
    private double yPos;
    private double zPos;
    private double orientation;

    public Coordinate(double xPosition, double yPosition, double zPosition, double orientationInput){
        xPos = xPosition;
        yPos = yPosition;
        zPos = zPosition;
        orientation = orientationInput;
    }

    public Coordinate returnCoordValue(){
        return this;
    }

    public double returnCoordSingleValue(String typeOfValue){
        if(typeOfValue.equalsIgnoreCase("x")){
            return xPos;
        }
        else if (typeOfValue.equalsIgnoreCase("y")) {

            return yPos;
        }
        else if (typeOfValue.equalsIgnoreCase("z")){
            return zPos;
        }
        else {
            return orientation;
        }
    }
}
