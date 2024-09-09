package xyz.catuns.audiototext.transcribe.repository;

import com.amazonaws.services.transcribe.model.TranscriptionJobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.catuns.audiototext.transcribe.model.TranscriptionJob;

import java.util.List;
import java.util.Optional;

public interface TranscriptionJobRepository extends JpaRepository<TranscriptionJob, Long> {
    List<TranscriptionJob> findByStatus(TranscriptionJobStatus jobStatus);

    Optional<TranscriptionJob> findByJobName(String jobName);
}
