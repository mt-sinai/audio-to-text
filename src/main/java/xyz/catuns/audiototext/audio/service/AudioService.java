package xyz.catuns.audiototext.audio.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import xyz.catuns.audiototext.audio.dto.AudioFileDetails;
import xyz.catuns.audiototext.audio.dto.AudioFileList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

public interface AudioService {
    AudioFileDetails uploadAudio(MultipartFile file) throws IOException;

    AudioFileList listAudioFiles(Pageable pageable);

    AudioFileDetails getAudioFileDetails(UUID fileId) throws IOException;

    byte[] downloadAudioFile(UUID fileId) throws IOException;

    void deleteAudioFile(UUID fileId) throws FileNotFoundException;
}
