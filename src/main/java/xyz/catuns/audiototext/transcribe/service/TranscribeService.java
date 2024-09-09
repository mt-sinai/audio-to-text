package xyz.catuns.audiototext.transcribe.service;

import org.springframework.data.domain.Pageable;
import xyz.catuns.audiototext.transcribe.dto.TranscriptionJobDetails;
import xyz.catuns.audiototext.transcribe.dto.TranscriptionJobList;

import java.util.UUID;

public interface TranscribeService {
    TranscriptionJobDetails startTranscription(UUID audioFileId, String languageCode);

    TranscriptionJobDetails getJobDetails(String jobName);

    TranscriptionJobList listTranscriptionJobs(Pageable pageable);

    void updateJobStatus();
}
