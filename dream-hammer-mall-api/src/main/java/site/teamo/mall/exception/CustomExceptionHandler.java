package site.teamo.mall.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import site.teamo.mall.common.util.MallJSONResult;

@RestControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public MallJSONResult handlerMaxUploadFile(MaxUploadSizeExceededException e){
        return MallJSONResult.errorException("文件大小超出限制");
    }
}
