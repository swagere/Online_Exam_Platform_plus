package com.kve.dubbo_interface.service;


import com.kve.dubbo_interface.model.GetQuestion;

/**
 * common
 */
public interface QuestionService {
    long saveQuestion(GetQuestion getQuestion) throws Exception;
}
