package com.projet.geopulserex.customclass.positioncontollerresponseobjects;
import com.projet.geopulserex.entity.Position;

public class PositionControllerCourseResponse {

    private Position estimatedPosition;
    private String probabilitySegment;

    public Position getEstimatedPosition() {
        return this.estimatedPosition;
    }

    public void setEstimatedPosition(Position estimatedPosition) {
        this.estimatedPosition = estimatedPosition;
    }
    
    public String getProbabilitySegment() {
        return this.probabilitySegment;
    }

    public void setProbabilitySegment(String probabilitySegment) {
        this.probabilitySegment = probabilitySegment;
    }

    public PositionControllerCourseResponse(Position estimatedPosition, String probabilitySegment){
        this.estimatedPosition = estimatedPosition;
        this.probabilitySegment =  probabilitySegment;
    }

}
