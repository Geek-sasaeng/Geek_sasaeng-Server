package shop.geeksasang.controller.auth;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.dto.email.PostEmailCertificationReq;
import shop.geeksasang.dto.email.PostEmailReq;
import shop.geeksasang.dto.email.PostEmailCertificationRes;
import shop.geeksasang.service.auth.EmailService;
import shop.geeksasang.utils.jwt.NoIntercept;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    // 이메일 인증 번호 보내기
    @ApiOperation(value = "이메일 인증번호 보내기", notes = "사용자의 이메일을 입력받아 인증번호를 보낸다.")
    @ApiResponses({
            @ApiResponse(code = 1802, message = "이메일이 성공적으로 전송 되었습니다.")
            ,@ApiResponse(code = 2803, message = "유효하지 않은 인증번호 입니다.")
            ,@ApiResponse(code = 2804, message = "이메일 인증은 하루 최대 10번입니다.")
            ,@ApiResponse(code = 2805, message = "잠시 후에 다시 시도해주세요.")
    }
    )
    @NoIntercept
    @PostMapping("")
    public BaseResponse<String> sendAuthEmail(@RequestBody @Valid PostEmailReq req, HttpServletRequest servletRequest) {
        emailService.sendEmail(req);
        return new BaseResponse<>(BaseResponseStatus.SEND_MAIL_SUCCESS);
    }

    // 이메일 인증 번호 확인하기
    @ApiOperation(value = "이메일 인증번호 확인하기", notes = "사용자의 이메일과, 수신한 이메일 인증번호를 이용해서 일치하는지 확인한다.")
    @ApiResponses({
            @ApiResponse(code = 1801, message = "이메일 인증이 완료되었습니다.")
            ,@ApiResponse(code = 2800, message = "유효하지 않은 인증번호 입니다.")
    }
    )
    @NoIntercept
    @PostMapping("/check")
    public BaseResponse<PostEmailCertificationRes> checkEmailValid(@RequestBody @Valid PostEmailCertificationReq req) {
        PostEmailCertificationRes postEmailRes = emailService.checkEmailCertification(req);
        return new BaseResponse<PostEmailCertificationRes>(postEmailRes);
    }
}
