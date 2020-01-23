package lottoland.paperrockscissors.domain;

import java.util.HashMap;
import java.util.Map;

public enum Figure {

    PAPER, ROCK, SCISSORS;

    private static final Map<Figure, Figure> ranksMap = new HashMap<>();

    static {
        ranksMap.put(PAPER, ROCK);
        ranksMap.put(ROCK, SCISSORS);
        ranksMap.put(SCISSORS, PAPER);
    }

    public int compareRank(Figure other) {
        if (this == other) {
            return 0;
        }
        return ranksMap.get(this) == other ? 1 : -1;
    }

    public static Figure fromId(int id) {
        return values()[id];
    }
}


