package lottoland.paperrockscissors.domain;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class GameServiceTest {

    private static final String PLAYER_1_ID = "testId_1";

    private GameService testedService;

    @Mock
    private StatisticsService statisticsService;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        testedService = new GameService(statisticsService);
    }

    @Test
    public void getPlayerStatistics() {

        //Draw
        Mockito.when(statisticsService.getStatsForPlayer(PLAYER_1_ID)).
                thenReturn(Collections.singletonList(ImmutablePair.of(Figure.ROCK, Figure.ROCK)));
        List<GameService.GameStatistics> playerStatistics = testedService.getPlayerStatistics(PLAYER_1_ID);
        Assert.assertEquals(Figure.ROCK, playerStatistics.get(0).getPlayer1());
        Assert.assertEquals(Figure.ROCK, playerStatistics.get(0).getPlayer2());
        Assert.assertEquals("Draw", playerStatistics.get(0).getResult());

        //Player 1 won
        Mockito.when(statisticsService.getStatsForPlayer(PLAYER_1_ID)).
                thenReturn(Collections.singletonList(ImmutablePair.of(Figure.ROCK, Figure.SCISSORS)));
        playerStatistics = testedService.getPlayerStatistics(PLAYER_1_ID);
        Assert.assertEquals(Figure.ROCK, playerStatistics.get(0).getPlayer1());
        Assert.assertEquals(Figure.SCISSORS, playerStatistics.get(0).getPlayer2());
        Assert.assertEquals("Player 1 won", playerStatistics.get(0).getResult());

        //Player 2 won
        Mockito.when(statisticsService.getStatsForPlayer(PLAYER_1_ID)).
                thenReturn(Collections.singletonList(ImmutablePair.of(Figure.SCISSORS, Figure.ROCK)));
        playerStatistics = testedService.getPlayerStatistics(PLAYER_1_ID);
        Assert.assertEquals(Figure.SCISSORS, playerStatistics.get(0).getPlayer1());
        Assert.assertEquals(Figure.ROCK, playerStatistics.get(0).getPlayer2());
        Assert.assertEquals("Player 2 won", playerStatistics.get(0).getResult());
    }
}