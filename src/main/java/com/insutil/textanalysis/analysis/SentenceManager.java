package com.insutil.textanalysis.analysis;

import com.insutil.textanalysis.common.analysis.PosTagging;
import com.insutil.textanalysis.repository.SttContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SentenceManager {

    private final SttContentsRepository sttContentsRepository;
    private final PosTagging posTagging;

    public List<String> extractAgentSentence(final String contents) throws InvalidParameterException {
        if (!StringUtils.hasLength(contents))
            throw new InvalidParameterException();

        return Arrays.asList(StringUtils.tokenizeToStringArray(contents, "\n"))
                .stream()
                .filter(s -> s.startsWith("A"))
                .map(s -> s.replaceFirst("^A:\\[‡\\d+‡\\]", ""))
                .filter(s -> s.length() > 10)
                .collect(Collectors.toList());

    }

    public String extractNoun(final String sentence) throws InvalidParameterException {
        if (!StringUtils.hasLength(sentence))
            throw new InvalidParameterException();
        return "[" + posTagging.extractNounTag(sentence)
                .stream()
                .collect(Collectors.joining(",")) + "]";
    }



}
