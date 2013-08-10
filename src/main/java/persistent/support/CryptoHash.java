package persistent.support;

import javax.validation.constraints.NotNull;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import com.google.common.base.Preconditions;

public class CryptoHash {

    private final String hash;
    private final CryptoAlgo algorithm;

    @JsonCreator
    public CryptoHash(@NotNull @JsonProperty("hash") String string, @NotNull @JsonProperty("algorithm") CryptoAlgo algorithm) {
        this.algorithm = algorithm;
        Preconditions.checkNotNull(string);
        this.hash = string;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public String toString() {
        return "CryptoHash(" + hash + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CryptoHash that = (CryptoHash) o;

        return hash.equals(that.hash);

    }

    @Override
    public int hashCode() {
        return hash.hashCode();
    }

    public CryptoAlgo getAlgorithm() {
        return algorithm;
    }
}
