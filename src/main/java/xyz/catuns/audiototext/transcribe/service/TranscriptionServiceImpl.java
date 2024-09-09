package xyz.catuns.audiototext.transcribe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.catuns.audiototext.transcribe.dto.TranscriptionJobDetails;
import xyz.catuns.audiototext.transcribe.dto.TranscriptionJobList;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TranscriptionServiceImpl implements TranscriptionService {


    /**
     * Start a new transcription job
     * @param audioFileId  The <code>AudioFile</code> id
     * @param languageCode The language code
     * @return <code>TranscriptionJobDetails</code>
     */
    @Override
    public TranscriptionJobDetails startTranscription(String audioFileId, String languageCode) {
        return null;
    }

    /**
     * Retrieve the status of a specific job
     * @param jobId The <code>TranscriptionJob</code> id
     * @return <code>TranscriptionJobDetails</code>
     */
    @Override
    public TranscriptionJobDetails getJobDetails(String jobId) {
        return null;
    }

    /**
     * List all transcription jobs for a user
     * @param pageable Page parameters
     * @return <code>TranscriptionJobList</code>
     */
    @Override
    public TranscriptionJobList listTranscriptionJobs(Pageable pageable) {
        return null;
    }

    /**
     * Update the status of all in-progress jobs
     */
    @Override
    public void updateJobStatus() {

    }
}
