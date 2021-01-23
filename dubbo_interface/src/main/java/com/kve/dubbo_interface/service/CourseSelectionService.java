package com.kve.dubbo_interface.service;

import com.kve.dubbo_interface.model.Course;
import com.kve.dubbo_interface.model.CourseVO;
import com.kve.dubbo_interface.model.StuCo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * common
 */
public interface CourseSelectionService {
    String getClass_id(String stu_id);

    String getMajor_id(String stu_id);

    ArrayList<String> getAllCourse_id(String major_id);

    ArrayList<Map> getChosenCoId_TeaId(List<StuCo> stuCos);
    ArrayList<Map> getUnChooesCoId_TeaId(List<StuCo> stuCos, String major_id);

    String getMajorName(String major_id);

    Course add(CourseVO courseVO);

    void saveToStuCo(String str, String stu_id);

    void quitCourse(String co_id, String tea_id, String stu_id);

    ArrayList getDoneExam(String tea_id);

    ArrayList getStuExam(Long exam_id);
    List<Object> getCourseNotTea(String tea_id);
}
