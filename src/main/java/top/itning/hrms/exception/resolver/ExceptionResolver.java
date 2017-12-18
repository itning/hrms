package top.itning.hrms.exception.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.itning.hrms.entity.ServerMessage;
import top.itning.hrms.exception.defaults.DefaultException;
import top.itning.hrms.exception.json.JsonException;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理
 *
 * @author Ning
 */
@ControllerAdvice
public class ExceptionResolver {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);
    /**
     * 默认错误页面
     */
    private static final String DEFAULT_ERROR_VIEW = "error";

    /**
     * 默认错误适配
     *
     * @param req HttpServletRequest
     * @param e   Exception
     * @return ModelAndView
     * @throws Exception 异常
     */
    @ExceptionHandler(value = DefaultException.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.warn("defaultErrorHandler->" + e.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.addObject("exceptionMessage", e.getMessage());
        mav.addObject("url", req.getRequestURL().toString());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

    /**
     * 其他异常消息
     *
     * @param req HttpServletRequest
     * @param e   Exception
     * @return ModelAndView
     * @throws Exception 异常
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView otherErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.warn("otherErrorHandler->" + e.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.addObject("exceptionMessage", e.getMessage());
        mav.addObject("url", req.getRequestURL().toString());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

    /**
     * json 格式错误消息
     *
     * @param req HttpServletRequest
     * @param e   Exception
     * @return 异常消息
     * @throws Exception 异常
     */
    @ExceptionHandler(value = JsonException.class)
    @ResponseBody
    public ServerMessage jsonErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.warn("jsonErrorHandler->" + e.getMessage());
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setCode(ServerMessage.NOT_FIND);
        serverMessage.setMsg(e.getMessage());
        serverMessage.setUrl(req.getRequestURL().toString());
        return serverMessage;
    }
}
