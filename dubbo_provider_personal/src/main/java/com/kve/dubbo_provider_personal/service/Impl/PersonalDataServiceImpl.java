package com.kve.dubbo_provider_personal.service.Impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kve.dubbo_interface.config.exception.CustomException;
import com.kve.dubbo_interface.config.exception.CustomExceptionType;
import com.kve.dubbo_interface.dao.MajorRepository;
import com.kve.dubbo_interface.dao.StudentRepository;
import com.kve.dubbo_interface.dao.TeacherRepository;
import com.kve.dubbo_interface.model.Student;
import com.kve.dubbo_interface.model.Teacher;
import com.kve.dubbo_interface.service.PersonalDataService;
import com.kve.dubbo_interface.service.SendMailService;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.NonUniqueResultException;
import java.util.Map;

/**
 * provider_personal
 */

@Slf4j
@Service
@Component
public class PersonalDataServiceImpl implements PersonalDataService {
    @Resource
    TeacherRepository teacherRepository;
    @Resource
    StudentRepository studentRepository;
    @Reference
    SendMailService sendMailService;
    @Autowired
    MajorRepository majorRepository;

    @Override
    public Teacher getTeacherData(String ID) {
        Teacher teacher = teacherRepository.findTeacherByTea_id(ID);
        teacher.setPassword(null);
        teacher.setCode(null);
        return teacher;
    }

    @Override
    public Student getStudentData(String ID) {
        Student student = studentRepository.findStudentByStu_id(ID);
        student.setPassword(null);
        student.setCode(null);
        String majorId = student.getMajor_id();
        student.setMajor_id(majorRepository.getNameByMajor_id(majorId));
        return student;
    }

    @Override
    public Teacher updateTeacherPassword(String ID, Map<String, Object> params) {
        Teacher teacher = teacherRepository.findById(ID).get();
        String newPassword = (String) params.get("newPassword");
        //邮箱验证：验证码和邮箱是否一致；
        if (newPassword.length() < 8) {
            log.info("密码位数过少");
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,"密码位数过少");
        }
        if (BCrypt.checkpw(newPassword, teacher.getPassword())) {
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR, "和原始密码重复");
        }

        try {
            teacher.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            log.info("密码加密成功");
        }catch (Exception e) {
            log.info("密码加密失败");
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR,"密码加密失败");
        }

        Teacher newTeacherData = teacherRepository.save(teacher);
        newTeacherData.setPassword(null);
        newTeacherData.setCode(null);
        return newTeacherData;
    }
    //修改学生密码
    @Override
    public Student updateStudentPassword(String ID, Map<String, Object> params) {
        Student student = studentRepository.findById(ID).get();
        String newPassword = (String) params.get("newPassword");

        if (newPassword.length() < 8) {
            log.info("密码位数过少");
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,"密码位数过少");
        }
        if (BCrypt.checkpw(newPassword, student.getPassword())) {
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR, "和原始密码重复");
        }

        try {
            student.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            log.info("密码加密成功");
        }catch (Exception e) {
            log.info("密码加密失败");
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR,"密码加密失败");
        }

        Student newStudentData = studentRepository.save(student);

        newStudentData.setPassword(null);
        newStudentData.setCode(null);
        return newStudentData;
    }
    @Override
    public Teacher editTeacherBaseData(String ID, Teacher newTeacherData) {
        Teacher teacher = teacherRepository.findById(ID).get();
        //检测电话是否重复
        Teacher teacherPhoneList = teacherRepository.findTeacherByTelephone(newTeacherData.getTelephone());
        if ( teacherPhoneList != null && !teacherPhoneList.getTea_id().equals(ID)) {
            log.info("该电话已被注册");
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,"该电话已被注册");
        }

        teacher.setName(newTeacherData.getName());
        teacher.setQq(newTeacherData.getQq());
        teacher.setWeixin(newTeacherData.getWeixin());
        teacher.setSex(newTeacherData.getSex());

        Teacher teacher1 = teacherRepository.save(teacher);
        teacher1.setPassword(null);
        teacher1.setCode(null);
        return teacher1;
    }

    @Override
    public Student editStudentBaseData(String ID, Student newStudentData) {
        Student student = studentRepository.findById(ID).get();

        Student studentPhoneList = studentRepository.findStudentByTelephone(newStudentData.getTelephone());
        if ( studentPhoneList != null && !studentPhoneList.getStu_id().equals(ID)) {
                log.info("该电话已被注册");
                throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,"该电话已被注册");
        }

        student.setName(newStudentData.getName());
        student.setQq(newStudentData.getQq());
        student.setSex(newStudentData.getSex());
        student.setWeixin(newStudentData.getWeixin());

        Student student1 = studentRepository.save(student);
        student1.setPassword(null);
        student1.setCode(null);
        return student1;
    }
    //更新教师邮箱
    @Override
    public Teacher updateTeacherEmail(String ID, Map<String, Object> params) {
        //邮箱验证：验证码和邮箱是否一致；
        String newEmail = (String) params.get("newEmail");
        String code = (String) params.get("code");
        if (!sendMailService.verification(newEmail, code)) {
            log.info("邮箱验证不一致");
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,"新邮箱验证失败");
        }
        //邮箱验证：是否唯一
        Teacher emailList = teacherRepository.findTeacherByEmail(newEmail);
        if (emailList != null && !emailList.getTea_id().equals(ID)) {
            log.info("该邮箱已被注册");
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,"该邮箱已被注册");
        }
        Teacher teacher = teacherRepository.findById(ID).get();
        teacher.setEmail(newEmail);
        Teacher newTeacher = teacherRepository.save(teacher);
        newTeacher.setPassword(null);
        newTeacher.setCode(null);
        return newTeacher;

    }
    //更新学生邮箱
    @Override
    public Student updateStudentEmail(String ID, Map<String, Object> params) {
        //邮箱验证：验证码和邮箱是否一致；
        String newEmail = (String) params.get("newEmail");
        String code = (String) params.get("code");
        if (!sendMailService.verification(newEmail, code)) {
            log.info("邮箱验证不一致");
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,"邮箱验证失败");
        }
        try {
            //邮箱验证：是否唯一
            Student emailList = studentRepository.findStudentByEmail(newEmail);
            if (emailList != null && !emailList.getStu_id().equals(ID)) {
                log.info("该邮箱已被注册");
                throw new CustomException(CustomExceptionType.USER_INPUT_ERROR, "该邮箱已被注册");
            }
            Student student = studentRepository.findById(ID).get();
            student.setEmail(newEmail);
            Student newStudent = studentRepository.save(student);
            newStudent.setPassword(null);
            newStudent.setCode(null);
            return newStudent;
        }catch (NonUniqueResultException e) {
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,"该邮箱已存在");
        } catch (Exception e) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR,"系统未知异常");
        }
    }
    //确保本人操作 或者 验证新的邮箱是否使用
    @Override
    public void checkStudentEmail(String email) {
        if (!(email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+"))) {
            log.info("邮箱格式不正确");
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,"邮箱格式不正确");
        }
        try {
            sendMailService.sendEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //确保本人操作 或者 验证新的邮箱是否使用
    @Override
    public void checkTeacherEmail(String email) {
        //如果邮箱格式不正确（正则表达式验证）
        String result = email.substring(email.length()-6,email.length());
        if (!(email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+") && result.equals("edu.cn"))) {
//            if (!(email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+"))) {
            log.info("邮箱格式不正确");
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,"邮箱格式不正确");
        }
        try {
            sendMailService.sendEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkCode(Map<String, Object> params) {
        String code = (String) params.get("code");
        String email = (String) params.get("email");
        if (!sendMailService.verification(email, code)) {
            log.info("邮箱验证不一致");
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,"邮箱验证失败");
        }
    }
}
