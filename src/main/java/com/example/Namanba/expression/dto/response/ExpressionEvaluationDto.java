package com.example.Namanba.expression.dto.response;

import com.example.Namanba.gesture.dto.response.GestureEvaluationDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ExpressionEvaluationDto {
    @NotNull
    private double expression;
    @NotNull
    private String expressionMessage;

    public static ExpressionEvaluationDto of(double expression, String expressionMessage) {
        return ExpressionEvaluationDto.builder()
                .expression(expression)
                .expressionMessage(expressionMessage)
                .build();
    }
}
