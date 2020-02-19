package com.example.majiang.majiang.service;

import com.example.majiang.majiang.dto.PaginationDTO;
import com.example.majiang.majiang.dto.QuestionDTO;
import com.example.majiang.majiang.mapper.QuestionMapper;
import com.example.majiang.majiang.mapper.UserMapper;
import com.example.majiang.majiang.model.Question;
import com.example.majiang.majiang.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page,Integer size){

        PaginationDTO paginationDTO=new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);

        if(page<1){
            page=1;
        }
        if(page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }

        Integer offset = size*(page-1);
        List<Question> questions=questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOlist=new ArrayList<>();

        for(Question question :questions){
            User user=userMapper.finById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOlist.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOlist);
        return paginationDTO;
    }
}
