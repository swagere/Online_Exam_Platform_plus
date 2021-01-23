package com.kve.dubbo_interface.service;

import com.alibaba.fastjson.JSONObject;
import com.kve.dubbo_interface.model.GetQuestion;
import com.kve.dubbo_interface.model.JudgeResult;

import java.util.ArrayList;

/**
 * common
 */
public interface JudgeService {

    JSONObject judge(String src, String language, Long testCaseId);

    void addTestCase(GetQuestion getQuestion);

    //Future<String> writeFile(Long question_id, int type, GetQuestion getQuestion) throws InterruptedException;

    void saveProgramQustionFile(Long question_id, int type, GetQuestion getQuestion);
//    void addToDocker(String path, Long question_id, ArrayList<String> fileNames);

    void deleteFile(Long question_id);

    String transformToMd5(String output);

    JudgeResult transformToResult(JSONObject json, String stu_id, String code, String language, Long question_id, Long exam_id);

    ArrayList<String> getFileNames(Long question_id);

    String getJudgeResult(int result);
}
