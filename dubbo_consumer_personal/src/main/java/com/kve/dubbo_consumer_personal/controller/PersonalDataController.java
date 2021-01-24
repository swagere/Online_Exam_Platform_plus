package com.kve.dubbo_consumer_personal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kve.dubbo_interface.config.exception.AjaxResponse;
import com.kve.dubbo_interface.config.exception.CustomException;
import com.kve.dubbo_interface.config.exception.CustomExceptionType;
import com.kve.dubbo_interface.model.Student;
import com.kve.dubbo_interface.model.Teacher;
import com.kve.dubbo_interface.service.AuthorityCheckService;
import com.kve.dubbo_interface.service.PersonalDataService;
import com.kve.dubbo_interface.service.RegisterService;
import com.kve.dubbo_interface.service.SendMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * consumer_personal
 */
@Slf4j
@RestController
@RequestMapping("/personal_api/PersonalData")
public class PersonalDataController {

    @Reference
    PersonalDataService personalDataService;
    @Reference
    RegisterService registerService;
    @Reference
    SendMailService sendMailService;
    @Reference
    AuthorityCheckService authorityCheckService;

    /**
     * 获取当前登陆老师个人信息
     * @param request
     * @return
     */
    @GetMapping("/getTeacher")
    public AjaxResponse getTeacherData(HttpServletRequest request){
        authorityCheckService.checkTeacherAuthority(request.getSession().getAttribute("userInfo"));
        Map m = (Map) request.getSession().getAttribute("userInfo");
        String ID = (String) m.get("id");

        Teacher teacherData = personalDataService.getTeacherData(ID);
        if(teacherData != null)
            return AjaxResponse.success(teacherData);//返回teacher类Data
        else
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, "查找个人资料错误");//返回500
    }

    /**
     * 获取登陆当前学生信息
     * @param request
     * @return
     */
    @GetMapping("/getStudent")
     public AjaxResponse getStudentData(HttpServletRequest request){
        authorityCheckService.checkStudentAuthority(request.getSession().getAttribute("userInfo"));
        Map m = (Map) request.getSession().getAttribute("userInfo");
        String ID = (String) m.get("id");
        Student studentData = personalDataService.getStudentData(ID);
        if(studentData != null)
            return AjaxResponse.success(studentData);//返回teacher类
        else
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, "查找个人资料错误");//返回500
    }

    //此处的邮箱验证 是否是教师操作
    @PostMapping("/checkTeacherEmail")
    public @ResponseBody
    AjaxResponse sendTeacherEmail(@RequestBody Map<String, Object> params, HttpServletRequest httpServletRequest) throws Exception {
        authorityCheckService.checkTeacherAuthority(httpServletRequest.getSession().getAttribute("userInfo"));
        String email = (String) params.get("email");
        personalDataService.checkTeacherEmail(email);
        log.info("发送邮件成功");
        return AjaxResponse.success();
     }

     //此处的邮箱验证 是否是本人操作
    @PostMapping("/checkStudentEmail")
    public @ResponseBody
    AjaxResponse sendStudentEmail(@RequestBody Map<String, Object> params, HttpServletRequest httpServletRequest) throws Exception {
        authorityCheckService.checkStudentAuthority(httpServletRequest.getSession().getAttribute("userInfo"));
        String email = (String) params.get("email");
        personalDataService.checkStudentEmail(email);
        log.info("发送邮件成功");
        return AjaxResponse.success();

    }

    //更新教师邮箱
    @PostMapping("/updateTeacherNewEmail")
    public @ResponseBody
    AjaxResponse updateTeacherNewEmail(HttpServletRequest request, @RequestBody Map<String, Object> params, HttpServletRequest httpServletRequest) throws Exception {
        authorityCheckService.checkTeacherAuthority(httpServletRequest.getSession().getAttribute("userInfo"));
        Map m = (Map) request.getSession().getAttribute("userInfo");
        String ID = (String) m.get("id");
        personalDataService.updateTeacherEmail(ID,params);
        return AjaxResponse.success();
    }

    //更新学生邮箱
    @PostMapping("/updateStudentNewEmail")
    public @ResponseBody
    AjaxResponse updateStudentNewEmail(HttpServletRequest request, @RequestBody Map<String, Object> params, HttpServletRequest httpServletRequest) throws Exception {
        authorityCheckService.checkStudentAuthority(httpServletRequest.getSession().getAttribute("userInfo"));
        Map m = (Map) request.getSession().getAttribute("userInfo");
        String ID = (String) m.get("id");
        personalDataService.updateStudentEmail(ID,params);
        return AjaxResponse.success();
    }


    //更新教师密码
    @PostMapping("/updateTeacherPassword")
    public AjaxResponse updataTeacherPassword(HttpServletRequest request , @RequestBody Map<String, Object> params){
        authorityCheckService.checkTeacherAuthority(request.getSession().getAttribute("userInfo"));
        Map m = (Map) request.getSession().getAttribute("userInfo");
        String ID = (String) m.get("id");
        Teacher newTeacherData = personalDataService.updateTeacherPassword(ID, params);
        return AjaxResponse.success(newTeacherData);
    }

    //更新学生密码
    @PostMapping("/updateStudentPassword")
    public AjaxResponse updataStudentPassword(HttpServletRequest request , @RequestBody Map<String, Object> params){
        authorityCheckService.checkStudentAuthority(request.getSession().getAttribute("userInfo"));
        Map m = (Map) request.getSession().getAttribute("userInfo");
        String ID = (String) m.get("id");
        Student newStudentData = personalDataService.updateStudentPassword(ID, params);
        return AjaxResponse.success(newStudentData);
    }

    /**
     * 修改老师基本信息
     * @param request
     * @param newTeacherData
     * @return
     */
    @PostMapping("/editTeacherBaseData")
    public AjaxResponse editTeacherBaseData(HttpServletRequest request, @RequestBody Teacher newTeacherData){
        authorityCheckService.checkTeacherAuthority(request.getSession().getAttribute("userInfo"));
        Map m = (Map) request.getSession().getAttribute("userInfo");
        String ID = (String) m.get("id");
        return AjaxResponse.success(personalDataService.editTeacherBaseData(ID,newTeacherData));
    }

    /**
     *修改学生基本信息
     * @param request
     * @param newStudentData
     * @return
     */
    @PostMapping("/editStudentBaseData")
    public AjaxResponse editStudentBaseData(HttpServletRequest request, @RequestBody Student newStudentData){
        authorityCheckService.checkStudentAuthority(request.getSession().getAttribute("userInfo"));
        Map m = (Map) request.getSession().getAttribute("userInfo");
        String ID = (String) m.get("id");
        return AjaxResponse.success(personalDataService.editStudentBaseData(ID,newStudentData));
    }

    /**
     * 检测邮箱是否经过验证
     * @param params
     * @return
     */
    @PostMapping("/checkCode")
    public AjaxResponse checkCode(@RequestBody Map<String, Object> params){
        personalDataService.checkCode(params);
        return AjaxResponse.success();
    }
}

