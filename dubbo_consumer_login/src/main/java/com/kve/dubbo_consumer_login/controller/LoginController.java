package com.kve.dubbo_consumer_login.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kve.dubbo_interface.config.exception.AjaxResponse;
import com.kve.dubbo_interface.config.exception.CustomException;
import com.kve.dubbo_interface.config.exception.CustomExceptionType;
import com.kve.dubbo_interface.model.Login;
import com.kve.dubbo_interface.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * consumer_login
 */

@Slf4j
@Controller
@RequestMapping("/login_api/login")
public class LoginController {
    @Reference
    LoginService loginService;

    //学号或者工号加密码
    @PostMapping("/id")
    public @ResponseBody
    AjaxResponse loginId(@Valid @RequestBody Login login, HttpServletRequest request) {

        Login login1 = loginService.LoginId(login);

//        //添加数据到session
//        request.getSession().setAttribute("session", request.getSession().getId());

        //添加sessionID到map
        Map<String, Object> map = new HashMap<>();
        map.put("id", login1.getId());
        map.put("session", request.getSession().getId());
        map.put("authority", login1.getAuthority());

        //添加数据到session
        request.getSession().setAttribute("userInfo", map);
        return AjaxResponse.success(map);
    }
//
//    //手机号加密码
//    @PostMapping("/phone")
//    public @ResponseBody AjaxResponse loginPhone(@Valid @RequestBody Login login, HttpServletRequest request) {
//        Login login1 = loginService.loginPhone(login);
//
//
//        //添加sessionID到map（传给前端）
//        Map<String, Object> map = new HashMap<>();
//        map.put("id", login1.getId());
//        map.put("session", request.getSession().getId());
//        map.put("authority", login1.getAuthority());
//
//        //添加数据到session(保存到session和redis)
//        request.getSession().setAttribute("userInfo", map);
//
//        return AjaxResponse.success(map);
//    }

    @PostMapping("/get/userInfo")
    public @ResponseBody
    AjaxResponse get(HttpServletRequest request) {
        //获取session数据(sessionId)
        Object userInfo = request.getSession().getAttribute("userInfo");

        return AjaxResponse.success(userInfo);
    }

    @PostMapping("/logout")
    public @ResponseBody
    AjaxResponse logout(HttpServletRequest request) {
        //登出前先检查用户是否已登录
        Object userInfo = request.getSession().getAttribute("userInfo");
        if (userInfo == null) {
            log.info("用户未登录");
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR, "用户未登录");
        }
        Object userInfo1 = userInfo;

        //销毁session
        request.getSession().removeAttribute("userInfo");
        userInfo = request.getSession().getAttribute("userInfo");
        if (userInfo != null) {
            log.info("登出失败");
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR, "登出失败");
        }
        log.info("登出成功");
        return AjaxResponse.success(userInfo1);
    }

}
