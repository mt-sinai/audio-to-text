package xyz.catuns.audiototext.transcription.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.catuns.audiototext.transcription.model.Transcription;

public interface TranscriptionRepository extends JpaRepository<Transcription, Long> {
}
