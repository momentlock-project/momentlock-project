//package momentlockdemo.controller;
//
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.NoHandlerFoundException;
//import org.springframework.web.servlet.resource.NoResourceFoundException;
//
//import com.amazonaws.services.kms.model.NotFoundException;
//
//import jakarta.servlet.http.HttpServletResponse;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    // IllegalArgumentException 처리
//    @ExceptionHandler(IllegalArgumentException.class)
//    public String handleIllegalArgumentException(IllegalArgumentException e, Model model) {
//        model.addAttribute("statusCode", 400);
//        model.addAttribute("errorMessage", e.getMessage()); // "유효하지 않은 초대 링크입니다."
//        return "error/error";
//    }
//
//    // IllegalStateException 처리 (만료된 링크, 이미 사용된 링크 등)
//    @ExceptionHandler(IllegalStateException.class)
//    public String handleIllegalStateException(IllegalStateException e, Model model) {
//        model.addAttribute("statusCode", 400);
//        model.addAttribute("errorMessage", e.getMessage());
//        return "error/error";
//    }
//
//    @ExceptionHandler(Exception.class)
//    public String handleGeneralException(Exception e, Model model, HttpServletResponse response) {
//        int statusCode = response.getStatus();
//
//        System.out.println(statusCode);
//        
//        if (statusCode == 200) {
//            model.addAttribute("statusCode", 404);
//            model.addAttribute("errorMessage", "요청하신 페이지를 찾을 수 없습니다.");
//        } else {
//            model.addAttribute("statusCode", 500);
//            model.addAttribute("errorMessage", "예상치 못한 오류가 발생했습니다.");
//        }
//        return "error/error";
//    }
//   
//}