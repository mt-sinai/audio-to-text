package xyz.catuns.audiototext.transcription.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.catuns.audiototext.transcription.dto.TranscriptionDetails;
import xyz.catuns.audiototext.transcription.dto.TranscriptionList;
import xyz.catuns.audiototext.transcription.service.TranscriptionService;

@RestController
@RequestMapping("/api/transcriptions")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TranscriptionController {

    private final TranscriptionService transcriptionService;

    @GetMapping("/{transcriptId}")
    public ResponseEntity<TranscriptionDetails> getTranscription(
            @PathVariable Long transcriptId
    ){
        TranscriptionDetails transcription = transcriptionService.getTranscription(transcriptId);
        return ResponseEntity.status(HttpStatus.OK).body(transcription);
    }

    @GetMapping("/list")
    public ResponseEntity<TranscriptionList> getAllTranscriptions(
            @PageableDefault(size = 20) Pageable pageable
    ){
        TranscriptionList transcriptions = transcriptionService.getAllTranscriptions(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(transcriptions);
    }


}
