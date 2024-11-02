package com.example.Namanba.gaze.usecase.processor;

import com.example.Namanba.common.annotation.Processor;
import com.example.Namanba.evaluation.adaptor.EvaluationContentAdaptor;
import com.example.Namanba.evaluation.entity.Category;
import com.example.Namanba.evaluation.entity.CategoryDetails;
import com.example.Namanba.gaze.dto.request.GazeDataDto;
import com.example.Namanba.gaze.dto.response.GazeEvaluationDto;
import com.example.Namanba.gaze.entity.Direction;
import com.example.Namanba.gaze.entity.enums.GazeDirectionCriteria;
import com.example.Namanba.gaze.entity.enums.GazeStabilityCriteria;
import lombok.RequiredArgsConstructor;

@Processor
@RequiredArgsConstructor
public class GazeEvaluationProcessor {

    private final EvaluationContentAdaptor evaluationContentAdaptor;

    public GazeEvaluationDto evaluateGaze(GazeDataDto gazeData){
        GazeDirectionCriteria gazeDirectionCriteria = evaluateGazeDirection(gazeData.getDirection());
        GazeStabilityCriteria gazeStabilityCriteria = GazeStabilityCriteria.evaluate(gazeData.getStabilityScore());

        String combinedMessage = generateCombinedMessage(gazeDirectionCriteria, gazeStabilityCriteria, gazeData.getDirection());
        double totalScore = gazeDirectionCriteria.getScore() + gazeStabilityCriteria.getScore();

        return GazeEvaluationDto.of(totalScore, combinedMessage);
    }

    private String generateCombinedMessage(GazeDirectionCriteria gazeDirection, GazeStabilityCriteria gazeStability, Direction direction){
        String gazeDirectionBasicMessage =   evaluationContentAdaptor.fetchMessageByCriteria(Category.GAZE, CategoryDetails.GAZE_DIRECTION, gazeDirection.name());
        String gazeDirectionMessage = fillGazeDirectionMessage(gazeDirectionBasicMessage, direction);

        String gazeStabilityMessage =   evaluationContentAdaptor.fetchMessageByCriteria(Category.GAZE, CategoryDetails.GAZE_STABILITY, gazeStability.name());
        return gazeDirectionMessage + " " + gazeStabilityMessage;
    }

    private String fillGazeDirectionMessage(String basicMessage, Direction direction){
        if (basicMessage.contains("{top}")){
            return basicMessage = basicMessage.replace("{top}", direction.getTopDirection());
        } else if (basicMessage.contains(("{topTwo}"))){
            return basicMessage = basicMessage.replace("{topTwo}", direction.getTopTwoDirections());
        } else  return basicMessage;

    }

    // userCenterP을 계산하고 GazeDirectionCriteria를 반환
    private GazeDirectionCriteria evaluateGazeDirection(Direction direction){
        double userCenterP = direction.computeCenterP();
        return GazeDirectionCriteria.evaluate(userCenterP);
    }

}
