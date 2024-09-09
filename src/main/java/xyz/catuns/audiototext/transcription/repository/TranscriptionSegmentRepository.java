package xyz.catuns.audiototext.transcription.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.catuns.audiototext.transcription.model.TranscriptionSegment;

public interface TranscriptionSegmentRepository extends JpaRepository<TranscriptionSegment, Long> {
}
