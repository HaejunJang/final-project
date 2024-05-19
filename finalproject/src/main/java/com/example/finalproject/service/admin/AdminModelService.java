package com.example.finalproject.service.admin;

import com.example.finalproject.domain.AiModel;
import com.example.finalproject.dto.AdminModelEvaluationDto;
import com.example.finalproject.dto.AdminModelRequestDto;
import com.example.finalproject.dto.AdminModelTuningRequestDto;
import com.example.finalproject.repository.AdminModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminModelService {

    @Autowired
    private AdminModelRepository adminModelRepository;

    //학습된 모델리스트 가져오기
    public List<AiModel> getAllModels() {
        return adminModelRepository.findAll();
    }

    //모델 튜닝 요청하기
    public void tuningModel(AdminModelTuningRequestDto requestDto) {
        Optional<AiModel>optionalModel = adminModelRepository.findById(requestDto.getModelNum());
        if(optionalModel.isPresent()) {
            AiModel model = optionalModel.get();
            if(requestDto.getBatchSize() != null) {
                model.setBatchSize(requestDto.getBatchSize());
            }   //여기에 else문으로 만약 null이면 기본값 넣는로직 추가
            if(requestDto.getEpoch() != null) {
                model.setEpoch(requestDto.getEpoch());
            }
            adminModelRepository.save(model);
        }else {
            throw new IllegalArgumentException("모델 수정에 실패했습니다");
        }
    }

    //모델 삭제하기
    public boolean deleteModel(int modelNum) {
        Optional<AiModel> optionalAiModel = adminModelRepository.findById(modelNum);
        if(optionalAiModel.isPresent()) {
            AiModel aiModel = optionalAiModel.get();

            //현재 모델이 활성화 상태인 경우 삭제 실패
            if(aiModel.isActive()){
                return false;
            }
            adminModelRepository.delete(optionalAiModel.get());
            return true;    //삭제 성공
        } else {
            return false;   //삭제 실패
        }
    }

    //모델 가져오기
    public Optional<AiModel> getModel(int modelNum) {
        return adminModelRepository.findById(modelNum);
    }

    //관리자 모델 새로운 모델 재학습 요청
    public boolean saveModel(AdminModelRequestDto adminModelRequestDto) {
        //요청 시간 기록하기 - 현재시간 기록
        LocalDateTime now = LocalDateTime.now();

        //시간 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm");
        String formattedDateTime = now.format(formatter);

        //db에 새로운 모델 정보 저장
        AiModel aiModel = new AiModel();
        aiModel.setModelName(formattedDateTime);
        aiModel.setBatchSize(adminModelRequestDto.getBatchSize());
        aiModel.setEpoch(adminModelRequestDto.getEpoch());
        aiModel.setActive(false);
        // DB에 저장
        adminModelRepository.save(aiModel);

        //파이썬에 재학습 요청
        try {
            ProcessBuilder pb = new ProcessBuilder("python", "/home/t24104/v0.9src/ai/retrain.py", String.valueOf(adminModelRequestDto.getEpoch()), String.valueOf(adminModelRequestDto.getBatchSize()));
            pb.redirectErrorStream(true);

            Process process = pb.start();
            // 프로세스 실행 및 대기 코드

            // 프로세스 실행 후 반환 코드
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                // 재학습 요청에 성공한 경우
                System.out.println("재학습 요청에 성공했습니다.");
                // 성공을 반환
                return true;
            } else {
                // 재학습 요청에 실패한 경우
                System.err.println("재학습 요청에 실패했습니다. 프로세스 종료 코드: " + exitCode);
                // 실패를 반환
                return false;
            }
        } catch (IOException | InterruptedException e) {
            // 예외 처리
            e.printStackTrace();
            // 오류를 반환
            return false;
        }



    }

    public boolean selectModel(int modelNum) {
        //해당 모델을 찾아서 활성화 상태로 변경
        AiModel selectedModel = adminModelRepository.findById(modelNum).orElse(null);
        if(selectedModel != null) {
            selectedModel.setActive(true);
            adminModelRepository.save(selectedModel);

            //다른 모델들의 active값 비활성화로 변경
            List<AiModel> otherModels = adminModelRepository.findByModelNumNot(modelNum);
            for(AiModel model : otherModels) {
                model.setActive(false);
                adminModelRepository.save(model);
            }
            return true;
        }
        return false;
    }

    //관리자 성능평가 페이지
    public List<AdminModelEvaluationDto> getModelEvaluations() {
        List<AiModel> aiModels = adminModelRepository.findAll();
        List<AdminModelEvaluationDto> evaluations = new ArrayList<>();

        for(AiModel aiModel : aiModels) {
            //이미지 경로 생성
            String imagePath1 = "/ai/models_link/validation_image" + aiModel.getModelNum() + "/MSE_graph.png";
            String imagePath2 = "/ai/models_link/validation_image" + aiModel.getModelNum() + "/R-squared_graph.png";
            String imagePath3 = "/ai/models_link/validation_image" + aiModel.getModelNum() + "/RMSE_graph.png";

            //모델 정보와 이미지 경로를 dto에 담아 리스트에 추가
            AdminModelEvaluationDto evaluation = new AdminModelEvaluationDto();
            evaluation.setModelNum(aiModel.getModelNum());
            evaluation.setModelName(aiModel.getModelName());
            evaluation.setImagePath_MSE(imagePath1);
            evaluation.setImagePath_R(imagePath2);
            evaluation.setImagePath_RMSE(imagePath3);
            evaluations.add(evaluation);
        }
        return evaluations;
    }
}