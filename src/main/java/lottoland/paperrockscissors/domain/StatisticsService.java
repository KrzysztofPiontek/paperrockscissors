package lottoland.paperrockscissors.domain;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
class StatisticsService {

    private final PlayerStatistics playerStatistics = new PlayerStatistics();

    private final GeneralStatistics generalStatistics = new GeneralStatistics();

    void storeRoundOutcome(String playerId, Figure player1, Figure player2) {

        playerStatistics.getRoundsOutcomes().get(playerId, (id) -> new ArrayList<>()).add(ImmutablePair.of(player1, player2));
        generalStatistics.updateGeneralStats(player1.compareRank(player2));
    }

    List<ImmutablePair<Figure, Figure>> getStatsForPlayer(String playerId) {
        return Optional.ofNullable(playerStatistics.getRoundsOutcomes().getIfPresent(playerId)).orElse(new ArrayList<>());
    }

    GeneralStatistics getGeneralStats() {
        return generalStatistics;
    }

    void resetStatisticsForPlayer(String playerId) {

        playerStatistics.getRoundsOutcomes().invalidate(playerId);
    }
}
