package com.kve.dubbo_provider_login.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kve.dubbo_interface.config.exception.CustomException;
import com.kve.dubbo_interface.config.exception.CustomExceptionType;
import com.kve.dubbo_interface.dao.StudentRepository;
import com.kve.dubbo_interface.dao.TeacherRepository;
import com.kve.dubbo_interface.model.Login;
import com.kve.dubbo_interface.model.Student;
import com.kve.dubbo_interface.model.Teacher;
import com.kve.dubbo_interface.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * login_api
 */

@Slf4j
@Service
@Component
public class LoginServiceImpl implements LoginService {
//    @Autowired
    StudentRepository studentRepository;
//    @Autowired
    TeacherRepository teacherRepository;

    //学号或者工号加密码
    @Override
    public Login LoginId(Login login) {

        Teacher tea = teacherRepository.findTeacherByTea_id(login.getKeyword());
        Student stu = studentRepository.findStudentByStu_id(login.getKeyword());
        if (tea != null || stu != null) {
            if (stu != null) {  //如果为学号
                if (BCrypt.checkpw(login.getPassword(),stu.getPassword())) { //如果密码正确
                    log.info("学生登录验证成功");
                    login.setId(stu.getStu_id());
                    login.setAuthority(stu.getAuthority());
                    return login;
                }
                else {
                    log.info("用户名/密码错误");
                    throw new CustomException(CustomExceptionType.USER_INPUT_ERROR, "用户名/密码错误");     //密码错误
                }
            }
            else { //如果为工号
                if (BCrypt.checkpw(login.getPassword(),tea.getPassword())) {
                    log.info("教师登录验证成功");
                    login.setId(tea.getTea_id());
                    login.setAuthority(tea.getAuthority());
                    return login;
                }
                else {
                    log.info("用户名/密码错误");
                    throw new CustomException(CustomExceptionType.USER_INPUT_ERROR, "用户名/密码错误");     //密码错误
                }
            }
        }
        log.info("用户不存在");
        throw new CustomException(CustomExceptionType.USER_INPUT_ERROR, "用户不存在/密码错误");
    }

    //手机号加密码
    @Override
    public Login loginPhone(Login login) {
        Teacher tea = teacherRepository.findTeacherByTelephone(login.getKeyword());
        Student stu = studentRepository.findStudentByTelephone(login.getKeyword());
        if (tea != null || stu != null) {      //如果数据库里面已存在
            if(stu != null) {
                if (BCrypt.checkpw(login.getPassword(), stu.getPassword())) {

                    log.info("学生登录验证成功");
                    login.setId(stu.getStu_id());
                    login.setAuthority(stu.getAuthority());
                    return login;

                } else {
                    log.info("用户名/密码错误");
                    throw new CustomException(CustomExceptionType.USER_INPUT_ERROR, "用户名/密码错误");     //密码错误
                }
            }
            else {
                if (BCrypt.checkpw(login.getPassword(), tea.getPassword())) {
                    log.info("教师登录验证成功");
                    login.setId(tea.getTea_id());
                    login.setAuthority(tea.getAuthority());
                    return login;
                } else {
                    log.info("用户名/密码错误");
                    throw new CustomException(CustomExceptionType.USER_INPUT_ERROR, "用户名/密码错误");     //密码错误
                }
            }
        }
        log.info("用户不存在");
        throw new CustomException(CustomExceptionType.USER_INPUT_ERROR, "用户不存在/密码错误");      //用户不存在
    }

}
