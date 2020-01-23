package lottoland.paperrockscissors.domain;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
class StatisticsService {

    private final Statistics statistics = new Statistics();

    void storeRoundOutcome(String playerId, Figure player1, Figure player2) {

        statistics.getRoundsOutcomes().get(playerId, (id) -> new ArrayList<>()).add(ImmutablePair.of(player1, player2));
    }

    List<ImmutablePair<Figure, Figure>> getStatsForPlayer(String playerId) {
        return Optional.ofNullable(statistics.getRoundsOutcomes().getIfPresent(playerId)).orElse(new ArrayList<>());
    }

    void resetStatisticsForPlayer(String playerId) {

        statistics.getRoundsOutcomes().invalidate(playerId);
    }
}
