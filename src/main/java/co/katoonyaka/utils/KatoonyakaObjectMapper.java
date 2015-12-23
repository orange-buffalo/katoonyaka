package co.katoonyaka.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;

@Component
public class KatoonyakaObjectMapper extends ObjectMapper {

    public KatoonyakaObjectMapper() {
        enable(SerializationFeature.INDENT_OUTPUT);
    }

}
