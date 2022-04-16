package com.projet.geopulserex.customclass.positioncontollerresponseobjects;

import com.projet.geopulserex.customclass.SimpleCoord;
import com.projet.geopulserex.entity.Position;

public class PositionControllerEnginResponse {
    
    private Position estimatedPosition;
    private SimpleCoord circleCenter;
    private SimpleCoord endRadius;

    public Position getEstimatedPosition() {
        return this.estimatedPosition;
    }

    public void setEstimatedPosition(Position estimatedPosition) {
        this.estimatedPosition = estimatedPosition;
    }

    public SimpleCoord getCircleCenter() {
        return this.circleCenter;
    }

    public void setCircleCenter(SimpleCoord circleCenter) {
        this.circleCenter = circleCenter;
    }

    public SimpleCoord getEndRadius() {
        return this.endRadius;
    }

    public void setEndRadius(SimpleCoord endRadius) {
        this.endRadius = endRadius;
    }

    public PositionControllerEnginResponse(Position estimatedPosition, SimpleCoord circleCenter, SimpleCoord endRadius){
        this.estimatedPosition = estimatedPosition;
        this.circleCenter = circleCenter;
        this.endRadius = endRadius;
    }

}
