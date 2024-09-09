package xyz.catuns.audiototext.audio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import xyz.catuns.audiototext.audio.dto.AudioFileDetails;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface AudioService {
    AudioFileDetails uploadAudio(MultipartFile file) throws IOException;

    Page<AudioFileDetails> listAudioFiles(Pageable pageable);

    AudioFileDetails getAudioFileDetails(long fileId) throws IOException;

    byte[] downloadAudioFile(long fileId) throws IOException;

    void deleteAudioFile(long fileId) throws FileNotFoundException;
}
