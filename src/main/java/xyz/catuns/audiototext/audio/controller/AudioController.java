package xyz.catuns.audiototext.audio.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.catuns.audiototext.audio.dto.AudioFileDetails;
import xyz.catuns.audiototext.audio.service.AudioService;

import java.util.List;

@RestController
@RequestMapping("/api/audio")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AudioController {

    private final AudioService audioService;

    @PostMapping("/upload")
    public ResponseEntity<AudioFileDetails> uploadAudio(@RequestParam("file") MultipartFile file){
        AudioFileDetails audio = audioService.uploadAudio(file);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(audio);
    }

    @GetMapping("")
    public ResponseEntity<List<AudioFileDetails>> listAudioFiles(){
        List<AudioFileDetails> audioFiles = audioService.listAudioFiles();
        return ResponseEntity.status(HttpStatus.OK).body(audioFiles);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<AudioFileDetails> getAudioFileDetails(@PathVariable long fileId){
        AudioFileDetails audioFile = audioService.getAudioFileDetails(fileId);
        return ResponseEntity.status(HttpStatus.OK).body(audioFile);
    }

    @GetMapping("/{fileId}/download")
    public ResponseEntity<byte[]> downloadAudioFile(@PathVariable long fileId){
        byte[] fileContent = audioService.downloadAudioFile(fileId);
        AudioFileDetails audioFile = audioService.getAudioFileDetails(fileId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(audioFile.contentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + audioFile.fileName() + "\"")
                .body(fileContent);
    }
    
    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteAudioFile(@PathVariable long fileId){
        audioService.deleteAudioFile(fileId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
