package com.ciandt.paul;

import com.ciandt.paul.context.Context;
import com.ciandt.paul.entity.HistoricalMatch;
import com.ciandt.paul.entity.Match;
import com.ciandt.paul.entity.Prediction;


import java.util.List;
import java.util.stream.Collectors;

public class HighestRankingPredictor implements Predictor {


    public static final int DIFFERENCE_POINTS = 40;

    @Override
    public Prediction predict(Match match, Context context) {
        Prediction prediction = new Prediction(match);
        if ((context.getAwayFifaRank().getPoints() - context.getHomeFifaRank().getPoints()) > DIFFERENCE_POINTS) {
            prediction.setAwayScore(2);
            prediction.setHomeScore(1);
        } else if ((context.getHomeFifaRank().getPoints() - context.getAwayFifaRank().getPoints()) > DIFFERENCE_POINTS) {
            prediction.setAwayScore(0);
            prediction.setHomeScore(1);
        } else {
            prediction.setAwayScore(0);
            prediction.setHomeScore(0);
        }
        return prediction;
    }



}
