package com.example.Namanba.evaluation.posture.service.processor;

import com.example.Namanba.common.annotation.Processor;
import com.example.Namanba.evaluation.posture.dto.request.PostureDataDto;
import com.example.Namanba.evaluation.posture.dto.response.PostureResultDto;
import lombok.RequiredArgsConstructor;

@Processor
@RequiredArgsConstructor
public class PostureEvaluationProcessor {


    public PostureResultDto evaluatePosture(PostureDataDto postureData){
        String headTouchScore = evaluateHeadTouch(postureData.getHeadTouch());
        String faceTouchScore = evaluateFaceTouch(postureData.getFaceTouch());
        String excessiveArmMovementScore = evaluateExcessiveArmMovement(postureData.getExcessiveArmMovement());
        String headMovementScore = evaluateHeadMovement(postureData.getHeadMovement());
        String excessiveBodyMovementScore = evaluateExcessiveBodyMovement(postureData.getExcessiveBodyMovement());
        String postureMessage = String.join(", ", evaluationMessages);

        double posture = calculateTotalScore(headTouchScore, faceTouchScore, excessiveArmMovementScore, headMovementScore, excessiveBodyMovementScore);
        return PostureResultDto.of(posture, postureMessage);
    }

    private double calculateTotalScore(String headTouch, String faceTouch, String excessiveArm, String headMovement, String excessiveBody) {
        double Totalscore = 0;
        score +=
    }
}
