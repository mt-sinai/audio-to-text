package xyz.catuns.audiototext.transcribe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.catuns.audiototext.transcribe.model.TranscriptionJob;

public interface TranscriptionJobRepository extends JpaRepository<TranscriptionJob, Long> {
}
