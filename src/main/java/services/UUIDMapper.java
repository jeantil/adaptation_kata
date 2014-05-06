/*
 * Copyright 2013  Séven Le Mesle
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import persistent.support.CryptoHash;
import support.CryptoService;

import java.util.UUID;

/**
 * Created by slemesle on 28/04/2014.
 */
@Component
public class UUIDMapper {

    private final CryptoService cryptoService;

    public UUIDMapper(){
        cryptoService = null;
    }

    @Autowired
    public UUIDMapper(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    public UUID asUUID(UUID in) {

        return new UUID(in.getMostSignificantBits(), in.getLeastSignificantBits());
    }


    public String asString(CryptoHash hash) {
        return cryptoService.fromCryptoHash(hash);
    }

    public CryptoHash asString(String hash) {
        return cryptoService.toCryptoHash(hash);
    }


}
