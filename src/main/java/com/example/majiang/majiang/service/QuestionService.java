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
        Integer totalPage;
        Integer totalCount = questionMapper.count();

        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size +1 ;
        }

        if(page<1){
            page=1;
        }
        if(page > totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);



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

    public PaginationDTO list(Integer userId, Integer page,Integer size){

        PaginationDTO paginationDTO=new PaginationDTO();
        Integer totalPage;
        Integer totalCount = questionMapper.countByUserId(userId);

        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size +1 ;
        }

        if(page<1){
            page=1;
        }
        if(page > totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);



        Integer offset = size*(page-1);
        List<Question> questions=questionMapper.listByUserId(userId,offset,size);
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

    public QuestionDTO getByid(Integer id){
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.finById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;

    }

    public void createupdate(Question question) {
        if(question.getId() == null){
//            创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else {
//            更新
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        }
    }
}
