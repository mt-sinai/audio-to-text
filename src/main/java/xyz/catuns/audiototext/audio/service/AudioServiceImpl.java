package xyz.catuns.audiototext.audio.service;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import xyz.catuns.audiototext.audio.dto.AudioFileDetails;
import xyz.catuns.audiototext.audio.dto.AudioFileList;
import xyz.catuns.audiototext.audio.mapper.AudioFileMapper;
import xyz.catuns.audiototext.audio.model.AudioFile;
import xyz.catuns.audiototext.audio.model.AudioFileStatus;
import xyz.catuns.audiototext.audio.repository.AudioFileRepository;
import xyz.catuns.audiototext.aws.service.S3Service;
import xyz.catuns.audiototext.exception.ResourceNotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AudioServiceImpl implements AudioService {

    private final S3Service s3Service;
    private final AudioFileRepository audioFileRepository;
    private final AudioFileMapper audioFileMapper;

    /**
     * @param file multipart file
     * @return <code>AudioFileDetails</code>
     */
    @Override
    public AudioFileDetails uploadAudio(MultipartFile file) throws IOException {
        UUID uuid = UUID.randomUUID();
        String fileName = generateUniqueFileName(uuid, file.getOriginalFilename());
        String s3Key = "audio/" + fileName;

        String fileUrl = s3Service.uploadFile(file, s3Key);
        log.debug("📪Uploaded audio file url: {}", fileUrl);

        AudioFile audioFile = new AudioFile();
        audioFile.setFileId(uuid);
        audioFile.setFileName(file.getOriginalFilename());
        audioFile.setContentType(file.getContentType());
        audioFile.setFileSize(file.getSize());
        audioFile.setFormat(determineFormat(file));
        audioFile.setFilePath(s3Key);
        audioFile.setStatus(AudioFileStatus.UPLOADED);

        audioFile = audioFileRepository.save(audioFile);
        return audioFileMapper.mapToDetails(audioFile);
    }

    private String determineFormat(MultipartFile file) {
        return StringUtils.getFilenameExtension(file.getOriginalFilename());
    }

    private String generateUniqueFileName(UUID uuid, String originalFilename) {
        return (uuid + "-" + originalFilename)
                .replaceAll("[^a-zA-Z0-9.\\-]", "-")
                .toLowerCase();
    }

    /**
     * @return <code>AudioFileDetails</code> List
     */
    @Override
    public AudioFileList listAudioFiles(Pageable pageable) {
        Page<AudioFile> page = audioFileRepository.findAll(pageable);
        return audioFileMapper.mapToList(page);
    }

    /**
     * @param fileId The <code>AudioFile</code> id
     * @return <code>AudioFileDetails</code>
     */
    @Override
    public AudioFileDetails getAudioFileDetails(UUID fileId) {
        AudioFile audioFile = findById(fileId);
        return audioFileMapper.mapToDetails(audioFile);
    }

    private AudioFile findById(UUID fileId) {
        return audioFileRepository.findByFileId(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("Audio file not found"));
    }

    /**
     * @param fileId The <code>AudioFile</code> id
     * @return file contents as byte array
     */
    @Override
    public byte[] downloadAudioFile(UUID fileId) throws IOException {
        AudioFile audioFile = findById(fileId);
        S3Object s3Object = s3Service.getFile(audioFile.getFilePath());
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        return IOUtils.toByteArray(inputStream);

    }

    /**
     * @param fileId The <code>AudioFile</code> id
     */
    @Override
    public void deleteAudioFile(UUID fileId) throws FileNotFoundException {
        AudioFile audioFile = findById(fileId);
        s3Service.deleteFile(audioFile.getFilePath());
        audioFileRepository.delete(audioFile);

    }
}
