package com.example.Namanba.gesture.usecase.processor;

import com.example.Namanba.common.annotation.Processor;
import com.example.Namanba.evaluation.adaptor.EvaluationContentAdaptor;
import com.example.Namanba.evaluation.entity.Category;
import com.example.Namanba.evaluation.entity.CategoryDetails;
import com.example.Namanba.gesture.dto.request.GestureDataDto;
import com.example.Namanba.gesture.dto.response.GestureEvaluationDto;
import com.example.Namanba.gesture.entity.GestureCriteria;
import com.example.Namanba.gesture.entity.GestureScore;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;

@Processor
@RequiredArgsConstructor
public class GestureEvaluationProcessor {
    private EvaluationContentAdaptor evaluationContentAdaptor;
    public GestureEvaluationDto evaluateGesture(GestureDataDto gestureData) {
        Map<CategoryDetails, GestureScore> evaluationResults = evaluateGestureDetails(gestureData);

        double totalScore = calculateTotalScore(evaluationResults);
        String combinedMessage = generateCombinedMessage(evaluationResults);

        System.out.println("totalScore: " + totalScore);
        System.out.println("combined Message: " + combinedMessage);
        return GestureEvaluationDto.of(totalScore, combinedMessage);
    }

    // 자세 세부 동작 평가
    private Map<CategoryDetails, GestureScore> evaluateGestureDetails(GestureDataDto gestureData) {
        return Map.of(
                CategoryDetails.HEAD_TOUCH, getScoreByCriteria(GestureCriteria.HEAD_TOUCH, gestureData.getHeadTouch()),
                CategoryDetails.FACE_TOUCH, getScoreByCriteria(GestureCriteria.FACE_TOUCH, gestureData.getFaceTouch()),
                CategoryDetails.ARM_TOUCH, getScoreByCriteria(GestureCriteria.ARM_TOUCH, gestureData.getExcessiveArmMovement()),
                CategoryDetails.HEAD_MOVEMENT, getScoreByCriteria(GestureCriteria.HEAD_MOVEMENT, gestureData.getHeadMovement()),
                CategoryDetails.BODY_MOVEMENT, getScoreByCriteria(GestureCriteria.BODY_MOVEMENT, gestureData.getExcessiveBodyMovement())
        );
    }

    // 자세 메시지 반환
    private String generateCombinedMessage(Map<CategoryDetails, GestureScore> evaluationResults) {
        return evaluationResults.entrySet().stream()
                .map(entry -> {
                    CategoryDetails categoryDetails = entry.getKey();
                    GestureScore gestureScore = entry.getValue();
                    return evaluationContentAdaptor.fetchMessageByCriteria(Category.GESTURE,categoryDetails, gestureScore.name());
                })
                .collect(Collectors.joining(" "));
    }

    // 자세 점수 반환
    private double calculateTotalScore(Map<CategoryDetails, GestureScore> evaluationResults) {
        return evaluationResults.values().stream()
                .map(GestureScore::getScore)
                .reduce(0.0, Double::sum);
    }

    // 자세 세부 항목 평가 반환
    private GestureScore getScoreByCriteria(GestureCriteria criteria, int count) {
        return criteria.evaluate(count); // 각 기준에 따라 점수 반환
    }

}
