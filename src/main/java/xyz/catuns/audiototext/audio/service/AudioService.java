package xyz.catuns.audiototext.audio.service;

import org.springframework.web.multipart.MultipartFile;
import xyz.catuns.audiototext.audio.dto.AudioFileDetails;

import java.util.List;

public interface AudioService {
    AudioFileDetails uploadAudio(MultipartFile file);

    List<AudioFileDetails> listAudioFiles();

    AudioFileDetails getAudioFileDetails(long fileId);

    byte[] downloadAudioFile(long fileId);

    void deleteAudioFile(long fileId);
}
