package com.kve.dubbo_interface.service;


import com.kve.dubbo_interface.model.CourseVO;

import java.util.ArrayList;
import java.util.List;

/**
 * common
 */
public interface HomePageService {
    List<Object> findStuById(String stu_id, int status) ;

    List<CourseVO> findTeaById(String tea_id);

    String List2String(ArrayList<String> list);

    ArrayList<String> String2List(String s);

}
