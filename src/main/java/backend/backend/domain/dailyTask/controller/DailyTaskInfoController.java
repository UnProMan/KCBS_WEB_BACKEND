package backend.backend.domain.dailyTask.controller;

import backend.backend.domain.dailyTask.Repository.DailyTaskInfoQueryRepository;
import backend.backend.domain.dailyTask.Repository.DailyTaskRepository;
import backend.backend.domain.dailyTask.dto.DailyTaskInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 라디오/뉴스 일정 조회 API
 */

@RestController
@RequestMapping("/api/dailyTasks")
@RequiredArgsConstructor
public class DailyTaskInfoController {

    private final DailyTaskRepository dailyTaskRepository;
    private final DailyTaskInfoQueryRepository dailyTaskQueryRepository;

    @GetMapping
    public ResponseEntity<List<DailyTaskInfoDto.DailyTaskInfoResponse>> getDailyTasks(@RequestParam LocalDate date) {
        return ResponseEntity.ok(dailyTaskQueryRepository.findAllDailyTasks(date).stream().map(DailyTaskInfoDto.DailyTaskInfoResponse::from).collect(Collectors.toList()));
    }

}
