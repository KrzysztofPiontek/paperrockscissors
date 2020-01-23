package lottoland.paperrockscissors.domain;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StatisticsServiceTest {

    private StatisticsService testedService;

    private static final String PLAYER_1_ID = "testId_1";
    private static final String PLAYER_2_ID = "testId_2";
    private static final String PLAYER_3_ID = "testId_3";

    @Before
    public void setUp() {

        testedService = new StatisticsService();
    }

    @Test
    public void storeRoundOutcome() {

        List<ImmutablePair<Figure, Figure>> statsForPlayer = testedService.getStatsForPlayer(PLAYER_1_ID);
        Assert.assertTrue(statsForPlayer.isEmpty());

        testedService.storeRoundOutcome(PLAYER_1_ID, Figure.ROCK, Figure.PAPER);
        statsForPlayer = testedService.getStatsForPlayer(PLAYER_1_ID);
        Assert.assertEquals(1, statsForPlayer.size());
        Assert.assertEquals(Figure.ROCK, statsForPlayer.get(0).left);
        Assert.assertEquals(Figure.PAPER, statsForPlayer.get(0).right);

        GeneralStatistics generalStats = testedService.getGeneralStats();
        Assert.assertEquals(1, generalStats.getTotalRounds());
        Assert.assertEquals(1, generalStats.getPlayer2wins());
        Assert.assertEquals(0, generalStats.getPlayer1wins());
        Assert.assertEquals(0, generalStats.getDraws());

        testedService.storeRoundOutcome(PLAYER_1_ID, Figure.SCISSORS, Figure.SCISSORS);
        statsForPlayer = testedService.getStatsForPlayer(PLAYER_1_ID);
        Assert.assertEquals(2, statsForPlayer.size());
        Assert.assertEquals(Figure.SCISSORS, statsForPlayer.get(1).left);
        Assert.assertEquals(Figure.SCISSORS, statsForPlayer.get(1).right);

        generalStats = testedService.getGeneralStats();
        Assert.assertEquals(2, generalStats.getTotalRounds());
        Assert.assertEquals(0, generalStats.getPlayer1wins());
        Assert.assertEquals(1, generalStats.getPlayer2wins());
        Assert.assertEquals(1, generalStats.getDraws());

        testedService.storeRoundOutcome(PLAYER_2_ID, Figure.PAPER, Figure.ROCK);
        statsForPlayer = testedService.getStatsForPlayer(PLAYER_2_ID);
        Assert.assertEquals(1, statsForPlayer.size());

        statsForPlayer = testedService.getStatsForPlayer(PLAYER_1_ID);
        Assert.assertEquals(2, statsForPlayer.size());

        generalStats = testedService.getGeneralStats();
        Assert.assertEquals(3, generalStats.getTotalRounds());
        Assert.assertEquals(1, generalStats.getPlayer1wins());
        Assert.assertEquals(1, generalStats.getPlayer2wins());
        Assert.assertEquals(1, generalStats.getDraws());
    }

    @Test
    public void getStatsForPlayer() {

        testedService.storeRoundOutcome(PLAYER_1_ID, Figure.ROCK, Figure.ROCK);
        testedService.storeRoundOutcome(PLAYER_2_ID, Figure.PAPER, Figure.PAPER);
        testedService.storeRoundOutcome(PLAYER_2_ID, Figure.SCISSORS, Figure.SCISSORS);
        testedService.storeRoundOutcome(PLAYER_3_ID, Figure.ROCK, Figure.SCISSORS);
        testedService.storeRoundOutcome(PLAYER_3_ID, Figure.PAPER, Figure.ROCK);
        testedService.storeRoundOutcome(PLAYER_3_ID, Figure.SCISSORS, Figure.PAPER);

        List<ImmutablePair<Figure, Figure>> statsForPlayer1 = testedService.getStatsForPlayer(PLAYER_1_ID);
        Assert.assertEquals(1, statsForPlayer1.size());
        Assert.assertEquals(Figure.ROCK, statsForPlayer1.get(0).left);
        Assert.assertEquals(Figure.ROCK, statsForPlayer1.get(0).right);

        List<ImmutablePair<Figure, Figure>> statsForPlayer2 = testedService.getStatsForPlayer(PLAYER_2_ID);
        Assert.assertEquals(2, statsForPlayer2.size());
        Assert.assertEquals(Figure.PAPER, statsForPlayer2.get(0).left);
        Assert.assertEquals(Figure.PAPER, statsForPlayer2.get(0).right);
        Assert.assertEquals(Figure.SCISSORS, statsForPlayer2.get(1).left);
        Assert.assertEquals(Figure.SCISSORS, statsForPlayer2.get(1).right);

        List<ImmutablePair<Figure, Figure>> statsForPlayer3 = testedService.getStatsForPlayer(PLAYER_3_ID);
        Assert.assertEquals(3, statsForPlayer3.size());
        Assert.assertEquals(Figure.ROCK, statsForPlayer3.get(0).left);
        Assert.assertEquals(Figure.SCISSORS, statsForPlayer3.get(0).right);
        Assert.assertEquals(Figure.PAPER, statsForPlayer3.get(1).left);
        Assert.assertEquals(Figure.ROCK, statsForPlayer3.get(1).right);
        Assert.assertEquals(Figure.SCISSORS, statsForPlayer3.get(2).left);
        Assert.assertEquals(Figure.PAPER, statsForPlayer3.get(2).right);
    }

    @Test
    public void resetStatisticsForPlayer() {

        testedService.storeRoundOutcome(PLAYER_1_ID, Figure.ROCK, Figure.ROCK);
        testedService.storeRoundOutcome(PLAYER_2_ID, Figure.PAPER, Figure.PAPER);
        testedService.storeRoundOutcome(PLAYER_2_ID, Figure.SCISSORS, Figure.SCISSORS);
        testedService.storeRoundOutcome(PLAYER_3_ID, Figure.ROCK, Figure.SCISSORS);
        testedService.storeRoundOutcome(PLAYER_3_ID, Figure.PAPER, Figure.ROCK);
        testedService.storeRoundOutcome(PLAYER_3_ID, Figure.SCISSORS, Figure.PAPER);

        Assert.assertEquals(1, testedService.getStatsForPlayer(PLAYER_1_ID).size());
        Assert.assertEquals(2, testedService.getStatsForPlayer(PLAYER_2_ID).size());
        Assert.assertEquals(3, testedService.getStatsForPlayer(PLAYER_3_ID).size());

        testedService.resetStatisticsForPlayer(PLAYER_1_ID);
        Assert.assertTrue(testedService.getStatsForPlayer(PLAYER_1_ID).isEmpty());
        Assert.assertEquals(2, testedService.getStatsForPlayer(PLAYER_2_ID).size());
        Assert.assertEquals(3, testedService.getStatsForPlayer(PLAYER_3_ID).size());

        testedService.resetStatisticsForPlayer(PLAYER_2_ID);
        Assert.assertTrue(testedService.getStatsForPlayer(PLAYER_1_ID).isEmpty());
        Assert.assertTrue(testedService.getStatsForPlayer(PLAYER_2_ID).isEmpty());
        Assert.assertEquals(3, testedService.getStatsForPlayer(PLAYER_3_ID).size());

        testedService.resetStatisticsForPlayer(PLAYER_3_ID);
        Assert.assertTrue(testedService.getStatsForPlayer(PLAYER_1_ID).isEmpty());
        Assert.assertTrue(testedService.getStatsForPlayer(PLAYER_2_ID).isEmpty());
        Assert.assertTrue(testedService.getStatsForPlayer(PLAYER_3_ID).isEmpty());
    }

    @Test
    public void storeRoundOutcome_concurrent() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(8);
        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 0; i < 500_000; i++) {
            tasks.add(() -> {
                testedService.storeRoundOutcome(PLAYER_1_ID, Figure.ROCK, Figure.ROCK);
                return "executed";
            });
        }
        executor.invokeAll(tasks);
        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);
        GeneralStatistics generalStats = testedService.getGeneralStats();
        Assert.assertEquals(500_000, generalStats.getDraws());
    }
}