package com.passion.lingosphere.services;

import com.passion.lingosphere.dtos.WordDto;
import com.passion.lingosphere.models.Word;
import com.passion.lingosphere.repositories.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordService {

    private final WordRepository wordRepository;

     @Autowired
    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public Word addWord(WordDto wordDto) throws Exception {
         // Check if word already exists
        if (wordRepository.existsByText(wordDto.getText())) {
            throw new Exception("Word already exists");
        }

        // Create new Word entity from the DTO
        Word newWord = new Word();
        newWord.setText(wordDto.getText());
        newWord.setPartOfSpeech(wordDto.getPartOfSpeech());
        newWord.setDefinition(wordDto.getDefinition());
        newWord.setLanguage(wordDto.getLanguage());

        // Save the new word entity
        return wordRepository.save(newWord);
    }

    public List<Word> getAllWords() {
        return wordRepository.findAll();
    }

    public Word getWordById(Long wordId) throws Exception {
         return wordRepository.findById(wordId)
                 .orElseThrow(() -> new Exception("Word doesn't exist"));
    }
}
