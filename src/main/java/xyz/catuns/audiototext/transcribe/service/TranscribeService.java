package xyz.catuns.audiototext.transcribe.service;

import org.springframework.data.domain.Pageable;
import xyz.catuns.audiototext.transcribe.dto.TranscriptionJobDetails;
import xyz.catuns.audiototext.transcribe.dto.TranscriptionJobList;

public interface TranscribeService {
    TranscriptionJobDetails startTranscription(String audioFileId, String languageCode);

    TranscriptionJobDetails getJobDetails(String jobId);

    TranscriptionJobList listTranscriptionJobs(Pageable pageable);

    void updateJobStatus();
}
