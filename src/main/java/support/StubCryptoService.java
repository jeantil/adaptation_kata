package support;

import org.springframework.stereotype.Service;
import com.google.common.base.Preconditions;
import persistent.support.CryptoAlgo;
import persistent.support.CryptoHash;

@Service
class StubCryptoService implements CryptoService {

    @Override
    public CryptoHash toCryptoHash(String filename) {
        // super duper hyper secure encryption scheme
        // the point is you "need" an external service for the conversion and can't do it by yourself
        String crypted = crypt(filename, CryptoAlgo.ULTIMATE_42);
        return new CryptoHash(crypted,CryptoAlgo.ULTIMATE_42);
    }

    @Override
    public String fromCryptoHash(CryptoHash hash) {
        // super duper hyper secure encryption scheme
        // the point is you "need" an external service for the conversion and can't do it by yourself
        return crypt(hash.getHash(), hash.getAlgorithm());
    }

    private String crypt(String string, CryptoAlgo algo) {
        // yes this is completely ridiculous, but that's not the point of the exercise :)
        Preconditions.checkNotNull(algo);
        StringBuilder builder=new StringBuilder();
        for (int i = string.length()-1; i > -1; i--) {
            builder.append(string.charAt(i));
        }
        return builder.toString();
    }
}
