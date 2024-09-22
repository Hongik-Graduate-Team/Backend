package com.example.Namanba.Interview.converter;

import com.example.Namanba.Interview.dto.PromptContent;
import com.example.Namanba.portfolio.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PromptConverter {

    public PromptContent toPromptContent(PortfolioResponseDto portfolio) {
        StringBuilder userMessage = new StringBuilder();

        userMessage.append("**Input Values:** \n");
        userMessage.append("Details from the portfolio:\nThe role of interest: ")
                .append(portfolio.getPositionName())
                .append("\n");
        appendItems(userMessage, "- Major:", portfolio.getMajors(), MajorDto::getMajorName);
        appendItems(userMessage, "- GPA:", portfolio.getGpas(), gpa -> gpa.getScore() + "/" + gpa.getTotal());
        appendItems(userMessage, "- Experience:", portfolio.getCareers(), career -> "[" + career.getCareerType() + "] " + career.getContent());
        appendItems(userMessage, "- Technology Stack:", portfolio.getStacks(), stack -> stack.getStackLanguage() + " (" + stack.getStackLevel() + ")");
        appendItems(userMessage, "- Awards:", portfolio.getAwards(), award -> "[" + award.getAwardType() + "] " + award.getAwardPrize());
        appendItems(userMessage, "- Certifications:", portfolio.getCertifications(), cert -> cert.getCertType() + " (" + cert.getCertDate() + ")");
        appendItems(userMessage, "- Language Certifications:", portfolio.getLanguageCerts(), langCert -> langCert.getLanguageCertType() + " - Level: " + langCert.getLanguageCertLevel());

        if (portfolio.getResumes() != null && !portfolio.getResumes().isEmpty()) {
            userMessage.append("\nContent of the cover letter:\n");
            for (ResumeDto resume : portfolio.getResumes()) {
                userMessage.append("[Question] ");
                userMessage.append(resume.getQuestion()).append("\n");
                userMessage.append("[Answer] ");
                userMessage.append(resume.getAnswer()).append("\n");
            }
        }

        return PromptContent.builder()
                .systemMessage(createSystemMessage(portfolio.getPositionName()))
                .userMessage(userMessage.toString())
                .build();
    }

    private String createSystemMessage(String positionName) {
        StringBuilder systemMessage = new StringBuilder();
        String dynamicMessage = String.format(
                "Role: As an interviewer for a %s role, evaluate the candidate based on their portfolio and cover letter.\n" +
                        "Context: The candidate is applying for a %s position. They have submitted a portfolio and a cover letter. It includes technical skills, certifications, and project experiences.",
                positionName, positionName
        );
        String fixedMessage =
                "\nInstructions:\n" +
                "Ask job-specific questions to assess the candidate’s experience and technical expertise.\n" +
                "Ask portfolio-based questions about the candidate's technical stack and project experiences.\n" +
                "Ask deep-dive questions based on their cover letter, focusing on lessons learned and challenges faced.\n" +
                "Ask non-technical questions exploring teamwork, communication skills, and problem-solving abilities.\n" +
                "Inquire about problem-solving experiences, particularly how they tackled specific technical challenges.\n" +
                "Ask about future growth plans, including long-term goals and how they plan to contribute to the company.\n" +
                "Include follow-up questions to dive deeper into their technical knowledge and understanding of IT concepts.\n" +
                "Constraints: All questions should be open-ended to encourage detailed responses. Tailor the questions to specific information from the portfolio and cover letter.\n";

        systemMessage.append(dynamicMessage).append(fixedMessage);
        return systemMessage.toString();
    }

    private <T> void appendItems(StringBuilder builder, String title, List<T> items, Function<T, String> extractor) {
        if (items != null && !items.isEmpty()) {
            builder.append(title).append(" ");
            String joinedItems = items.stream()
                    .map(extractor)
                    .collect(Collectors.joining(", "));
            builder.append(joinedItems).append(" ");
        }
    }
}
