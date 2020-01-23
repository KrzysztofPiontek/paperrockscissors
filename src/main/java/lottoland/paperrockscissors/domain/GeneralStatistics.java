package lottoland.paperrockscissors.domain;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PACKAGE)
class GeneralStatistics {

    private int player1wins;
    private int player2wins;
    private int draws;
    private int totalRounds;

    synchronized void updateGeneralStats(int roundOutcome) {
        totalRounds++;
        if (roundOutcome == 0) {
            draws++;
        } else if (roundOutcome > 0) {
            player1wins++;
        } else {
            player2wins++;
        }
    }
}
