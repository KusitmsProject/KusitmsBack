package com.example.demo.src.controller;


import com.example.demo.config.BaseResponse;
import com.example.demo.src.dto.request.PostQuestionReq;
import com.example.demo.src.dto.response.BaseException;
import com.example.demo.src.dto.response.GetQuestionRes;
import com.example.demo.src.dto.response.PostQuestionRes;
import com.example.demo.src.entity.Post;
import com.example.demo.src.entity.Question;
import com.example.demo.src.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bring",produces = "application/json;charset=UTF-8")
public class QuestionController {


    @Autowired
    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    // 질문 등록
    @PostMapping(value="/question")
    public BaseResponse<PostQuestionRes> postQuestion(@RequestBody PostQuestionReq postQuestionReq)  {


        String imageUrl="";
        String videoIdx="";

        if(imageUrl.equals(null)){
            imageUrl="";
        }else{
            imageUrl=postQuestionReq.getImageUrl();
        }
        if(videoIdx.equals(null)){
            videoIdx="";
        }else{
            videoIdx=postQuestionReq.getVideoIdx();
        }


        Question question=Question.builder()
                        .questionText(postQuestionReq.getQuestionText())
                        .imageUrl(imageUrl)
                        .videoIdx(videoIdx).build();

        Question saveQuestion=questionRepository.save(question);

        PostQuestionRes postQuestionRes=new PostQuestionRes(saveQuestion.getQuestionIdx());

        return new BaseResponse<>(postQuestionRes);


    }


    //질문 조회

    @GetMapping("/question")
    public BaseResponse<GetQuestionRes> getQuestion(@RequestParam(value="year")String year,@RequestParam(value="month")String month,@RequestParam(value="day")String day){

            try{
                Question questionRes=questionRepository.getQuestionByDate(year,month,day);


                GetQuestionRes getQuestionRes=GetQuestionRes.builder()
                        .questionIdx(questionRes.getQuestionIdx())
                        .questionText(questionRes.getQuestionText())
                        .imageUrl(questionRes.getImageUrl())
                        .videoIdx(questionRes.getVideoIdx())
                        .build();

                return new BaseResponse<>(getQuestionRes);
            }catch (NullPointerException e){
                throw  new NullPointerException(e.getMessage());
            }
    }


}
