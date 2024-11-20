package com.example.Namanba.gaze.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.Namanba.common.constant.StaticValue.*;

// 시선 위치
@Getter
public class DirectionCounts {
    @JsonProperty(required = true)
    private double up;
    @JsonProperty(required = true)
    private double down;
    @JsonProperty(required = true)
    private double left;
    @JsonProperty(required = true)
    private double right;
    @JsonProperty(required = true)
    private double centerX;
    @JsonProperty(required = true)
    private double centerY;

    public double computeCenterP() {
        double directionX = right + left + centerX;
        double directionY = up + down + centerY;
        return 0.5 * (centerX / directionX + centerY / directionY);
    }

    @JsonIgnore
    public String getTopDirection(){
        List<String> sortedDirections  = getSortedDirections();
        return !sortedDirections .isEmpty() ? sortedDirections .get(0) : "";
    }

    @JsonIgnore
    public String getTopTwoDirections(){
        return getTopDirection() + ", " + getSecondDirections();
    }

    // TODO: 값이 모두 0일 때를 고려해야 하는 문제
    private String getSecondDirections(){
        List<String> sortedDirections  = getSortedDirections();
        String primaryDirection = sortedDirections.get(0);
        double primaryValue = getDirectionValue(primaryDirection); // 가장 치우친 방향의 점수

        String secondDirection = sortedDirections.get(1);
        double secondValue = getDirectionValue((secondDirection));

        String thirdDirection = sortedDirections.get(2);
        double thirdValue = getDirectionValue(thirdDirection);

        if (secondValue == thirdValue) {
            return secondDirection + ", " + thirdDirection;
        } else {
            return secondDirection;
        }
    }

    // 방향을 값에 따라 정렬하고, 값이 동점이면 알파벳 순서대로 정렬
    private List<String> getSortedDirections(){
        Map<String, Double> directionMap = createDirectionMap();
        return directionMap.entrySet().stream()
                .sorted(Map.Entry.<String,Double>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey())) // 값이 동일하면 알파벳 순서대로 정렬
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
    private Map<String, Double> createDirectionMap() {
        Map<String, Double> directionMap = new HashMap<>();
        directionMap.put(UP, up);
        directionMap.put(DOWN, down);
        directionMap.put(LEFT, left);
        directionMap.put(RIGHT, right);
        return directionMap;
    }

    private double getDirectionValue(String direction){
        return createDirectionMap().get(direction);
    }
}
