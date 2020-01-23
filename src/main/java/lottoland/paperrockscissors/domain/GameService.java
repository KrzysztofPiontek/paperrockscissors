package lottoland.paperrockscissors.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class GameService {

    private StatisticsService statisticsService;

    private static final Figure PLAYER_2_PICK = Figure.ROCK;

    private static final int RANDOM_PICK_BOUND = Figure.values().length;

    private static final Map<Integer, String> RESULT_MAPPING = new HashMap<>();

    GameService(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
        RESULT_MAPPING.put(1, "Player 1 won");
        RESULT_MAPPING.put(-1, "Player 2 won");
        RESULT_MAPPING.put(0, "Draw");
    }

    public void playRound(String playerId) {

        Figure playersPick = Figure.fromId(ThreadLocalRandom.current().nextInt(0, RANDOM_PICK_BOUND));
        statisticsService.storeRoundOutcome(playerId, playersPick, PLAYER_2_PICK);
    }

    public List<SingleGameStats> getPlayerStatistics(String playerId) {
        return statisticsService.getStatsForPlayer(playerId)
                .stream().map(this::mapPlayerStatistics).collect(Collectors.toList());
    }

    private SingleGameStats mapPlayerStatistics(ImmutablePair<Figure, Figure> roundStats) {
        Figure player1 = roundStats.left;
        Figure player2 = roundStats.right;
        int outcome = player1.compareRank(player2);
        return new SingleGameStats(player1, player2, RESULT_MAPPING.get(outcome));
    }

    public GeneralStats getGeneralStatistics() {
        GeneralStatistics generalStats = statisticsService.getGeneralStats();
        return GeneralStats.builder()
                .player1wins(generalStats.getPlayer1wins())
                .player2wins(generalStats.getPlayer2wins())
                .draws(generalStats.getDraws())
                .totalRounds(generalStats.getTotalRounds())
                .build();
    }

    public void resetPlayer(String playerId) {
        statisticsService.resetStatisticsForPlayer(playerId);
    }

    @AllArgsConstructor
    @Getter
    public static class SingleGameStats {

        private Figure player1;
        private Figure player2;
        private String result;
    }

    @Builder
    @Getter
    public static class GeneralStats {

        private int player1wins;
        private int player2wins;
        private int draws;
        private int totalRounds;
    }
}
