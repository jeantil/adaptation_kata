package support;

import persistent.support.CryptoHash;

public interface CryptoService {
    CryptoHash toCryptoHash(String filename);

    String fromCryptoHash(CryptoHash hash);
}
