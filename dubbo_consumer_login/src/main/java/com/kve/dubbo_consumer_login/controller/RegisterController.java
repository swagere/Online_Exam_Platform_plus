package com.kve.dubbo_consumer_login.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kve.dubbo_interface.config.exception.AjaxResponse;
import com.kve.dubbo_interface.model.Student;
import com.kve.dubbo_interface.model.Teacher;
import com.kve.dubbo_interface.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Map;

/**
 * login_api
 */

@Slf4j
@Controller
@RequestMapping("/login_api/register")
public class RegisterController {
    @Reference
    RegisterService registerService;

    @PostMapping("/student")
    public @ResponseBody
    AjaxResponse saveStudent(@Valid @RequestBody Student student) throws Exception {
        registerService.saveStudent(student);
        log.info("学生注册成功");
        return AjaxResponse.success(student.getStu_id());
    }

    @PostMapping("/teacher")
    public @ResponseBody
    AjaxResponse saveTeacher(@Valid @RequestBody Teacher teacher) throws Exception {
        registerService.saveTeacher(teacher);
        log.info("老师注册成功");
        return AjaxResponse.success(teacher.getTea_id());
    }

    @PostMapping("/email/teacher")
    public @ResponseBody
    AjaxResponse sendTeacherEmail(@RequestBody Map map) throws Exception {
        //从map中获取邮箱号
        String receiver = map.values().toString().substring(1,map.values().toString().length()-1);
        registerService.checkTeacherRepeat(receiver);
        registerService.sendTeacherEmail(receiver);
        log.info("发送邮件成功");
        return AjaxResponse.success();
    }

    @PostMapping("/email/student")
    public @ResponseBody
    AjaxResponse sendStudentEmail(@RequestBody Map map) throws Exception {
        //从map中获取邮箱号
        String receiver = map.values().toString().substring(1,map.values().toString().length()-1);
        registerService.checkStudentRepeat(receiver);
        registerService.sendStudentEmail(receiver);
        log.info("发送邮件成功");
        return AjaxResponse.success();

    }
}
