package co.katoonyaka.web.client.domain;

import co.katoonyaka.domain.Handiwork;
import lombok.Getter;

public class HandiworkResponseModel extends ClientResponseModel {

    @Getter
    private EnrichedHandiwork handiwork;

    @Getter
    private EnrichedHandiwork previousHandiwork;

    @Getter
    private EnrichedHandiwork nextHandiwork;

    public HandiworkResponseModel(Handiwork handiwork) {
        super(handiwork.getName());
        this.handiwork = new EnrichedHandiwork(handiwork);
    }

    public void setPreviousHandiwork(Handiwork previousHandiwork) {
        this.previousHandiwork = new EnrichedHandiwork(previousHandiwork);
    }

    public void setNextHandiwork(Handiwork nextHandiwork) {
        this.nextHandiwork = new EnrichedHandiwork(nextHandiwork);
    }

}