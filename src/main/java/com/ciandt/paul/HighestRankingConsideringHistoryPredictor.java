package com.ciandt.paul;

import com.ciandt.paul.context.Context;
import com.ciandt.paul.entity.Match;
import com.ciandt.paul.entity.Prediction;

public class HighestRankingConsideringHistoryPredictor implements Predictor {


    public static final int DIFFERENCE_POINTS = 30;

    @Override
    public Prediction predict(Match match, Context context) {
        Prediction prediction = new Prediction(match);
        String winner = "";

        if ((context.getAwayFifaRank().getPoints() - context.getHomeFifaRank().getPoints()) > DIFFERENCE_POINTS) {
            winner = match.getAwayTeam();

            prediction.setAwayScore(predictScoreUsingHistory(winner, context));
            prediction.setHomeScore(prediction.getAwayScore() - predictScoreDifferenceUsingHistory(winner, context));
        } else if ((context.getHomeFifaRank().getPoints() - context.getAwayFifaRank().getPoints()) > DIFFERENCE_POINTS) {
            winner = match.getHomeTeam();

            prediction.setHomeScore(predictScoreUsingHistory(winner, context));
            prediction.setAwayScore(prediction.getHomeScore() - predictScoreDifferenceUsingHistory(winner, context));
        } else {
            prediction.setAwayScore(1);
            prediction.setHomeScore(1);
        }
        return prediction;
    }


    private int predictScoreUsingHistory(String winner, Context context) {

        return  (int)Math.round( context.getHistoricalMatches()
                .stream()
                .filter(
                    historicalMatch ->
                        (historicalMatch.getHomeScore() > historicalMatch.getAwayScore() && historicalMatch.getHomeTeam().equals(winner)) ||
                        (historicalMatch.getAwayScore() > historicalMatch.getHomeScore() && historicalMatch.getAwayTeam().equals(winner))
                ).mapToInt(historicalMatch -> historicalMatch.getHomeTeam().equals(winner) ? historicalMatch.getHomeScore() : historicalMatch.getAwayScore())
                .filter(i -> i <= 3)
                .average().orElse(1D) );

    }

    private int predictScoreDifferenceUsingHistory(String winner, Context context) {
         //return 1;
        return  (int)Math.round( context.getHistoricalMatches()
                .stream()
                .filter(
                        historicalMatch ->
                                (historicalMatch.getHomeScore() > historicalMatch.getAwayScore() && historicalMatch.getHomeTeam().equals(winner)) ||
                                (historicalMatch.getAwayScore() > historicalMatch.getHomeScore() && historicalMatch.getAwayTeam().equals(winner))
                ).mapToInt(historicalMatch -> Math.abs(historicalMatch.getHomeScore() - historicalMatch.getAwayScore()))
                .filter(i -> i < 3)
                .average().orElse(1D) );

    }
}
