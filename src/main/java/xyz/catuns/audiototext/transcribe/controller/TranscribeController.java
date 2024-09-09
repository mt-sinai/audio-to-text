package xyz.catuns.audiototext.transcribe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.catuns.audiototext.transcribe.dto.TranscriptionJobDetails;
import xyz.catuns.audiototext.transcribe.dto.TranscriptionJobList;
import xyz.catuns.audiototext.transcribe.service.TranscribeService;

@RestController
@RequestMapping("/api/transcribe")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TranscribeController {

    private final TranscribeService transcribeService;

    @PostMapping("/start")
    public ResponseEntity<TranscriptionJobDetails> startTranscription(
            @RequestParam String audioFileId,
            @RequestParam String languageCode
    ) {
        TranscriptionJobDetails job = transcribeService.startTranscription(audioFileId, languageCode);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(job);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<TranscriptionJobDetails> getJobDetails(
            @PathVariable String jobId
    ){
        TranscriptionJobDetails job = transcribeService.getJobDetails(jobId);
        return ResponseEntity.status(HttpStatus.OK).body(job);
    }
    
    @GetMapping("/jobs")
    public ResponseEntity<TranscriptionJobList> listTranscriptionJobs(
            @PageableDefault(size = 20) Pageable pageable
    ) {
        TranscriptionJobList jobList = transcribeService.listTranscriptionJobs(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(jobList);
    }

    @PostMapping("/update-status")
    public ResponseEntity<Void> updateJobStatus() {
        transcribeService.updateJobStatus();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
