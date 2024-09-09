package xyz.catuns.audiototext.transcribe.service;

import com.amazonaws.services.transcribe.AmazonTranscribe;
import com.amazonaws.services.transcribe.model.Media;
import com.amazonaws.services.transcribe.model.StartTranscriptionJobRequest;
import com.amazonaws.services.transcribe.model.TranscriptionJobStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.catuns.audiototext.audio.model.AudioFile;
import xyz.catuns.audiototext.audio.repository.AudioFileRepository;
import xyz.catuns.audiototext.exception.ResourceNotFoundException;
import xyz.catuns.audiototext.transcribe.dto.TranscriptionJobDetails;
import xyz.catuns.audiototext.transcribe.dto.TranscriptionJobList;
import xyz.catuns.audiototext.transcribe.mapper.TranscriptionJobMapper;
import xyz.catuns.audiototext.transcribe.model.TranscriptionJob;
import xyz.catuns.audiototext.transcribe.repository.TranscriptionJobRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TranscribeServiceImpl implements TranscribeService {

    private final AmazonTranscribe transcribeClient;
    private final TranscriptionJobRepository jobRepository;
    private final AudioFileRepository audioFileRepository;
    private final TranscriptionJobMapper transcriptionJobMapper;

    @Value("${aws.s3.bucket.name}")
    private String s3BucketName;

    /**
     * Start a new transcription job
     * @param audioFileId  The <code>AudioFile</code> id
     * @param languageCode The language code
     * @return <code>TranscriptionJobDetails</code>
     */
    @Override
    public TranscriptionJobDetails startTranscription(UUID audioFileId, String languageCode) {
        AudioFile audioFile = findAudioFileById(audioFileId);
        String jobName = "Transcription_" + audioFile.getFileId();

        StartTranscriptionJobRequest request = new StartTranscriptionJobRequest()
                .withTranscriptionJobName(jobName)
                .withLanguageCode(languageCode)
                .withMediaFormat(audioFile.getFormat())
                .withMedia(new Media().withMediaFileUri(getS3Uri(audioFile.getFilePath())));

        transcribeClient.startTranscriptionJob(request);

        TranscriptionJob job = new TranscriptionJob();
        job.setJobName(jobName);
        job.setAudioFile(audioFile);
        job.setStatus(TranscriptionJobStatus.IN_PROGRESS);
        job.setLanguageCode(languageCode);
        job = jobRepository.save(job);

        return transcriptionJobMapper.mapToDetails(job);
    }

    private String getS3Uri(String filePath) {
        return "s3://" + s3BucketName + "/" + filePath;
    }

    private AudioFile findAudioFileById(UUID audioFileId) {
        return audioFileRepository.findByFileId(audioFileId)
                .orElseThrow(() -> new ResourceNotFoundException("Audio file not found"));
    }

    /**
     * Retrieve the status of a specific job
     * @param jobId The <code>TranscriptionJob</code> id
     * @return <code>TranscriptionJobDetails</code>
     */
    @Override
    public TranscriptionJobDetails getJobDetails(Long jobId) {
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
