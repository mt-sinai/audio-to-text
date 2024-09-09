package xyz.catuns.audiototext.audio.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import xyz.catuns.audiototext.audio.model.AudioFile;

import java.util.Optional;
import java.util.UUID;

public interface AudioFileRepository extends JpaRepository<AudioFile, Long> {

    Optional<AudioFile> findByFileId(UUID fileId);
}
