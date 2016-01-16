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
        super(handiwork.getName(),
                handiwork.getSummary(),
                "photos/" + handiwork.getUrl() + "." + handiwork.getCover().getId() + ".jpeg",
                "portfolio/" + handiwork.getUrl());
        this.handiwork = new EnrichedHandiwork(handiwork);
    }

    public void setPreviousHandiwork(Handiwork previousHandiwork) {
        this.previousHandiwork = new EnrichedHandiwork(previousHandiwork);
    }

    public void setNextHandiwork(Handiwork nextHandiwork) {
        this.nextHandiwork = new EnrichedHandiwork(nextHandiwork);
    }

}
