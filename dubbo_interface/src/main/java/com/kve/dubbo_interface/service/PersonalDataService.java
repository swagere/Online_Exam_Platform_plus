package com.kve.dubbo_interface.service;


import com.kve.dubbo_interface.model.Student;
import com.kve.dubbo_interface.model.Teacher;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * common
 */
public interface PersonalDataService {
     Teacher getTeacherData(String ID);
     Student getStudentData(String ID);
     Teacher updateTeacherPassword(String ID, Map<String, Object> params);
     Student updateStudentPassword(String ID, Map<String, Object> params);
     Teacher editTeacherBaseData(String ID, Teacher newTeacherData);
     Student editStudentBaseData(String ID, Student newStudentData);
     Teacher updateTeacherEmail(String ID, Map<String, Object> params);
     Student updateStudentEmail(String ID, Map<String, Object> params);
     //此处发的时候只会验证格式正确与否 而不会检测数据库存在与否
     void checkTeacherEmail(String email);
     void checkStudentEmail(String email);

     void checkCode(Map<String, Object> params);

}
