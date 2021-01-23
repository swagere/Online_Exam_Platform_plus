package com.kve.dubbo_interface.service;


import com.kve.dubbo_interface.model.Student;
import com.kve.dubbo_interface.model.Teacher;

/**
 * login_api
 */

public interface RegisterService {
    void saveStudent(Student student) throws Exception;

    void saveTeacher(Teacher teacher) throws Exception;

    void checkTeacherRepeat(String email);

    void sendTeacherEmail(String receiver) throws Exception;

    void checkStudentRepeat(String email);

    void sendStudentEmail(String receiver) throws Exception;
}
