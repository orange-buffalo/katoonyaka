package co.katoonyaka.services.impl.storage;

import co.katoonyaka.domain.Cover;
import co.katoonyaka.domain.Handiwork;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class JsonKatoonyakaData {

    @Getter @Setter
    private List<Handiwork> handiworks = new ArrayList<>();

    @Getter @Setter
    private List<Cover> covers = new ArrayList<>();

}
