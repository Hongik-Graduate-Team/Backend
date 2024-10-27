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
    private Double expression;
    @NotNull
    private String expressionMessage;

    public static ExpressionEvaluationDto of(Double expression, String expressionMessage) {
        return ExpressionEvaluationDto.builder()
                .expression(expression)
                .expressionMessage(expressionMessage)
                .build();
    }
}
