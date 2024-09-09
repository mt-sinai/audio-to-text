package xyz.catuns.audiototext.transcription.service;

import org.springframework.data.domain.Pageable;
import xyz.catuns.audiototext.transcription.dto.TranscriptionDetails;
import xyz.catuns.audiototext.transcription.dto.TranscriptionList;

public interface TranscriptionService {
    TranscriptionDetails getTranscription(Long transcriptionId);

    TranscriptionList getAllTranscriptions(Pageable pageable);
}
