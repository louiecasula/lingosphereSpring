package com.passion.lingosphere.services;

import com.passion.lingosphere.models.Language;
import com.passion.lingosphere.dtos.WordDto;
import com.passion.lingosphere.models.Word;
import com.passion.lingosphere.repositories.WordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class WordServiceTest {

    @Mock
    private WordRepository wordRepository;
    @Mock
    private Word word;
    @InjectMocks
    private WordService wordService;
    private WordDto wordDto;

    @BeforeEach
    public void setup() {
        String text = "testword";
        String partOfSpeech = "test";
        String definition = "this is a test word";
        Language language = new Language("testlang", "tl");
        wordDto = new WordDto(text, partOfSpeech, definition, 1, language);
    }

    @Test
    public void addWordSuccessfulTest() throws Exception {
        given(wordRepository.existsByText(wordDto.getText())).willReturn(false);
        given(wordRepository.save(any(Word.class))).willReturn(new Word(wordDto.getText(), wordDto.getPartOfSpeech(), wordDto.getDefinition(), 1, wordDto.getLanguage()));

        Word added = wordService.addWord(wordDto);

        assertNotNull(added);
        assertEquals(wordDto.getText(), added.getText());
    }

    @Test
    public void addWordTextTakenTest() {
        given(wordRepository.existsByText(word.getText())).willReturn(true);

        assertThrows(Exception.class, () -> wordService.addWord(wordDto));
    }

    @Test
    public void getAllWordsTest() {
        Word word1 = new Word();
        Word word2 = new Word();
        List<Word> expected = Arrays.asList(word1, word2);
        given(wordRepository.findAll()).willReturn(expected);

        List<Word> actual = wordService.getAllWords();

        assertEquals(expected, actual);
    }

    @Test
    public void getWordByIdSuccessfulTest() throws Exception {
        given(wordRepository.findById(0L)).willReturn(Optional.of(word));

        Word word1 = wordService.getWordById(0L);

        assertNotNull(word1);
    }

    @Test
    public void getWordByIdFailureTest() {
        given(wordRepository.findById(0L)).willReturn(Optional.empty());

        assertThrows(Exception.class, () -> wordService.getWordById(0L));
    }

    // TODO: Write test cases for getWordsOfTheDay.
}
