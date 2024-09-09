package xyz.catuns.audiototext.transcription.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.catuns.audiototext.transcription.dto.TranscriptionDetails;
import xyz.catuns.audiototext.transcription.dto.TranscriptionList;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TranscriptionServiceImpl implements TranscriptionService {


    /**
     * @param transcriptionId
     * @return
     */
    @Override
    public TranscriptionDetails getTranscription(Long transcriptionId) {
        return null;
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public TranscriptionList getAllTranscriptions(Pageable pageable) {
        return null;
    }
}
