package xyz.catuns.audiototext.audio.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import xyz.catuns.audiototext.audio.model.AudioFile;

public interface AudioFileRepository extends JpaRepository<AudioFile, Long> {
}
