package lottoland.paperrockscissors.domain;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

@Getter(AccessLevel.PACKAGE)
class Statistics {

    private Cache<String, List<ImmutablePair<Figure, Figure>>> roundsOutcomes = Caffeine.newBuilder()
            .maximumSize(10_000)
            .build();
}
