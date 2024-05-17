package com.example.finalproject.controller;

import com.example.finalproject.domain.AiModel;
import com.example.finalproject.dto.AdminModelEvaluationDto;
import com.example.finalproject.dto.AdminModelRequestDto;
import com.example.finalproject.dto.AdminModelTuningRequestDto;
import com.example.finalproject.service.admin.AdminModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AdminModelController {

    @Autowired
    private AdminModelService adminModelService;

    //필요한거 정리->     모델리스트 전달ok, 사용할 모델 변경 여기서 boolean값 변경해야한다 o, 모델 삭제 ok,  새로운 모델 생성 요청 o,

    //관리자 모델 리스트 조회
    @GetMapping("/src/admins/models")
    public ResponseEntity<List<AiModel>> getModelList() {
        List<AiModel> models = adminModelService.getAllModels();
        return ResponseEntity.ok(models);
    }

    //관리자 단일 모델 조회 - 변경버튼을 통한 데이터 가져오기
    @GetMapping("/src/admins/model/{modelNum}")
    public ResponseEntity<AiModel> getModel(@PathVariable("modelNum") int modelNum) {
        Optional<AiModel> model = adminModelService.getModel(modelNum);
        if(model.isPresent()) {
            return ResponseEntity.ok(model.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    //관리자 새로운 모델 요청 여기서 값 변경하고 파이썬에 재학습 요청 보내서 재학습 시켜놔야한다
    @PostMapping("/src/admins/model")
    public ResponseEntity<String> createModel(@RequestBody AdminModelRequestDto adminModelRequestDto) {
        try {
            if (adminModelService.saveModel(adminModelRequestDto)) {
                return ResponseEntity.ok("{\"message\": \"모델 재학습을 요청했습니다.\"}");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"파이썬 코드 실행 중 오류 발생했습니다.\"}");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"서버에서 예기치 않은 오류가 발생했습니다.\"}");
        }
    }

    //관리자가 사용할 모델 지정
    @PutMapping("/src/admin/select/{modelNum}")
    public ResponseEntity<String> selectModel(@PathVariable("modelNum") int modelNum) {
        //해당 모델을 활성화하고, 나머지 모든 모델 활성 상태를 비활성화로 설정
        boolean updated = adminModelService.selectModel(modelNum);

        if (updated) {
            String message = "{\"message\": \"모델 선택을 완료했습니다.\"}";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            String message = "{\"message\": \"해당하는 모델이 없습니다.\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    //관리자 모델 값 수정-> 필요없음
    @PutMapping("/src/admins/model/tuning")
    public ResponseEntity<String> modelTuning(@RequestBody AdminModelTuningRequestDto tuningRequestDto) {
        adminModelService.tuningModel(tuningRequestDto);
        return ResponseEntity.ok().body("{\"message\": \"모델 파라미터를 수정했습니다.\"}");
    }

    //관리자 모델 삭제
    @DeleteMapping("/src/admins/model/{modelNum}")
    public ResponseEntity<String> deleteModel(@PathVariable("modelNum") int modelNum) {
        boolean deleted = adminModelService.deleteModel(modelNum);
        if (deleted) {
            String message = "{\"message\": \"모델 삭제를 성공했습니다.\"}";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            String message = "{\"message\": \"사용중인 모델은 삭제할수 없습니다.\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    //관리자 성능평가 페이지
    @GetMapping("/src/admins/models/evaluation")
    public ResponseEntity<List<AdminModelEvaluationDto>> getModelEvaluations(){
        List<AdminModelEvaluationDto> evaluations = adminModelService.getModelEvaluations();
        return ResponseEntity.ok(evaluations);
    }

}
