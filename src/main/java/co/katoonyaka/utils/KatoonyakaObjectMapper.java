package co.katoonyaka.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class KatoonyakaObjectMapper extends ObjectMapper {

    public KatoonyakaObjectMapper() {
        enable(SerializationFeature.INDENT_OUTPUT);
    }

}
