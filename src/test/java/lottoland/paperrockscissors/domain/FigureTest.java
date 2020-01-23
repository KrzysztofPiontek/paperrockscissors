package lottoland.paperrockscissors.domain;

import org.junit.Assert;
import org.junit.Test;

public class FigureTest {

    @Test
    public void compareRank() {

        //ROCK
        Assert.assertEquals(1, Figure.ROCK.compareRank(Figure.SCISSORS));
        Assert.assertEquals(-1, Figure.ROCK.compareRank(Figure.PAPER));
        Assert.assertEquals(0, Figure.ROCK.compareRank(Figure.ROCK));

        //SCISSORS
        Assert.assertEquals(1, Figure.SCISSORS.compareRank(Figure.PAPER));
        Assert.assertEquals(-1, Figure.SCISSORS.compareRank(Figure.ROCK));
        Assert.assertEquals(0, Figure.SCISSORS.compareRank(Figure.SCISSORS));

        //PAPER
        Assert.assertEquals(1, Figure.PAPER.compareRank(Figure.ROCK));
        Assert.assertEquals(-1, Figure.PAPER.compareRank(Figure.SCISSORS));
        Assert.assertEquals(0, Figure.PAPER.compareRank(Figure.PAPER));
    }
}