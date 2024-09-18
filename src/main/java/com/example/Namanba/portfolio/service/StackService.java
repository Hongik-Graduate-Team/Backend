package com.example.Namanba.portfolio.service;

import com.example.Namanba.portfolio.dto.StackDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.subitems.Stack;
import com.example.Namanba.portfolio.repository.StackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StackService {
    private final StackRepository stackRepository;

    public List<StackDto> getStacks(Portfolio portfolio) {
        return stackRepository.findByPortfolio(portfolio).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private StackDto toDto(Stack stack) {
        return StackDto.builder()
                .stackId(stack.getStackId())
                .stackLanguage(stack.getStackLanguage())
                .stackLevel(stack.getStackLevel())
                .build();
    }

    public void createStacks(Portfolio portfolio, List<StackDto> stackDtos) {
        List<Stack> stacks = stackDtos.stream()
                .map(dto -> Stack.builder()
                        .stackLanguage(dto.getStackLanguage())
                        .stackLevel(dto.getStackLevel())
                        .portfolio(portfolio)
                        .build())
                .collect(Collectors.toList());

        stackRepository.saveAll(stacks);
    }

    public void updateStacks(Portfolio portfolio, List<StackDto> stackDtos) {
        stackDtos.forEach(dto -> {
            Stack stack = stackRepository.findById(dto.getStackId())
                    .orElseThrow(RuntimeException::new); // -> new StackNotFoundException("Stack not found with id: " + dto.getStackId()));
            stack.updateStackLanguage(dto.getStackLanguage());
            stack.updateStackLevel(dto.getStackLevel());
            stackRepository.save(stack);
        });
    }

    public void deleteStacks(Portfolio portfolio, List<Long> stackIds) {
        List<Stack> stacksToDelete = stackRepository.findAllById(stackIds);
        if (stacksToDelete.size() != stackIds.size()) {
            throw new RuntimeException(); //StackNotFoundException("Some stacks not found");
        }
        stackRepository.deleteAll(stacksToDelete);
    }
}
