package com.ciandt.paul;

import com.ciandt.paul.context.Context;
import com.ciandt.paul.entity.Match;
import com.ciandt.paul.entity.Prediction;

/**
 * Dummy predictor that predicts that all games will be 1x0
 */
public class HighestRankingPredictor implements Predictor {


    @Override
    public Prediction predict(Match match, Context context) {
        Prediction prediction = new Prediction(match);

        if (context.getAwayFifaRank().getPoints() - context.getHomeFifaRank().getPoints() > 5) {
            prediction.setAwayScore(1);
            prediction.setHomeScore(0);
        } else if (context.getHomeFifaRank().getPoints() - context.getAwayFifaRank().getPoints() > 5) {
            prediction.setAwayScore(0);
            prediction.setHomeScore(1);
        } else {
            prediction.setAwayScore(0);
            prediction.setHomeScore(0);
        }
        return prediction;
    }
}
