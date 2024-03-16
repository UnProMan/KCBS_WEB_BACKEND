package backend.backend.domain.dailyTask.controller;

import backend.backend.domain.dailyTask.Repository.DailyTaskQueryRepository;
import backend.backend.domain.dailyTask.Repository.DailyTaskRepository;
import backend.backend.domain.dailyTask.dto.DailyTaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dailyTasks")
@RequiredArgsConstructor
public class DailyTaskController {

    private final DailyTaskRepository dailyTaskRepository;
    private final DailyTaskQueryRepository dailyTaskQueryRepository;
    @GetMapping
    public ResponseEntity<List<DailyTaskDto.DailyTasksResponse>> getDailyTasks(@RequestParam LocalDate date) {
        return ResponseEntity.ok(dailyTaskQueryRepository.findAllDailyTasks(date).stream().map(DailyTaskDto.DailyTasksResponse::from).collect(Collectors.toList()));
    }

}
