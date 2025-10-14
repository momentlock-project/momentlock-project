package momentlockdemo.controller;

import momentlockdemo.repository.InquiryRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // IllegalArgumentException 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e, Model model) {
        model.addAttribute("statusCode", 400);
        model.addAttribute("errorMessage", e.getMessage()); // "유효하지 않은 초대 링크입니다."
        return "error/error";
    }

    // IllegalStateException 처리 (만료된 링크, 이미 사용된 링크 등)
    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException(IllegalStateException e, Model model) {
        model.addAttribute("statusCode", 400);
        model.addAttribute("errorMessage", e.getMessage());
        return "error/error";
    }

    // 나머지 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception e, Model model) {
    	
        model.addAttribute("statusCode", 500);
        model.addAttribute("errorMessage", "예상치 못한 오류가 발생했습니다.");
        // 개발 중에는 실제 에러 메시지를 보고 싶다면:
        // model.addAttribute("errorMessage", e.getMessage());
        return "error/error";
    }
}