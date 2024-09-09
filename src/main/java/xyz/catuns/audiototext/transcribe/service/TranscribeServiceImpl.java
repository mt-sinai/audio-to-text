package xyz.catuns.audiototext.transcribe.service;

import com.amazonaws.services.transcribe.AmazonTranscribe;
import com.amazonaws.services.transcribe.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.catuns.audiototext.audio.model.AudioFile;
import xyz.catuns.audiototext.audio.model.AudioFileStatus;
import xyz.catuns.audiototext.audio.repository.AudioFileRepository;
import xyz.catuns.audiototext.exception.ResourceNotFoundException;
import xyz.catuns.audiototext.transcribe.dto.TranscriptionJobDetails;
import xyz.catuns.audiototext.transcribe.dto.TranscriptionJobList;
import xyz.catuns.audiototext.transcribe.mapper.TranscriptionJobMapper;
import xyz.catuns.audiototext.transcribe.model.TranscriptionJob;
import xyz.catuns.audiototext.transcribe.repository.TranscriptionJobRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TranscribeServiceImpl implements TranscribeService {

    private final AmazonTranscribe transcribeClient;
    private final TranscriptionJobRepository jobRepository;
    private final AudioFileRepository audioFileRepository;
    private final TranscriptionJobMapper jobMapper;

    @Value("${aws.s3.bucket.name}")
    private String s3BucketName;

    /**
     * Start a new transcription job
     * @param audioFileId  The <code>AudioFile</code> fileId
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
        log.debug("🎤Starting transcription job: {}", jobName);

        audioFile.setStatus(AudioFileStatus.TRANSCRIBING);

        TranscriptionJob job = new TranscriptionJob();
        job.setJobName(jobName);
        job.setAudioFile(audioFile);
        job.setStatus(TranscriptionJobStatus.IN_PROGRESS);
        job.setLanguageCode(languageCode);
        job = jobRepository.save(job);


        return jobMapper.mapToDetails(job);
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
     *
     * @param jobName The <code>TranscriptionJob</code> name
     * @return <code>TranscriptionJobDetails</code>
     */
    @Override
    public TranscriptionJobDetails getJobDetails(String jobName) {
        TranscriptionJob job = jobRepository.findByJobName(jobName)
                .orElseThrow(() -> new ResourceNotFoundException("Transcription job not found"));
        return jobMapper.mapToDetails(job);
    }

    /**
     * List all transcription jobs for a user
     * @param pageable Page parameters
     * @return <code>TranscriptionJobList</code>
     */
    @Override
    public TranscriptionJobList listTranscriptionJobs(Pageable pageable) {
        Page<TranscriptionJob> jobs = jobRepository.findAll(pageable);
        return jobMapper.mapToList(jobs);
    }

    /**
     * Update the status of all in-progress jobs
     */
    @Override
    public void updateJobStatus() {
        List<TranscriptionJob> inProgressJobs = jobRepository.findByStatus(TranscriptionJobStatus.IN_PROGRESS);
        for (TranscriptionJob job : inProgressJobs) {
            GetTranscriptionJobRequest request = new GetTranscriptionJobRequest()
                    .withTranscriptionJobName(job.getJobName());
            GetTranscriptionJobResult result = transcribeClient.getTranscriptionJob(request);

            TranscriptionJobStatus newStatus = TranscriptionJobStatus.valueOf(result.getTranscriptionJob().getTranscriptionJobStatus());
            if (newStatus != job.getStatus()) {
                job.setStatus(newStatus);
                if (newStatus == TranscriptionJobStatus.COMPLETED) {
                    job.setEndTime(LocalDateTime.now());
                    job.getAudioFile().setStatus(AudioFileStatus.COMPLETED);
                    // Here you would also save the transcription result

                }
                jobRepository.save(job);
            }
        }
    }
}
