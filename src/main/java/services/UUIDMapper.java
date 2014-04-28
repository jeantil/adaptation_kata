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

import persistent.support.CryptoHash;
import support.ApplicationContextProvider;
import support.CryptoService;

import java.util.UUID;

/**
 * Created by slemesle on 28/04/2014.
 */
public class UUIDMapper {


    public UUID asUUID(UUID in){

        return new UUID(in.getMostSignificantBits(),in.getLeastSignificantBits());
    }


    public String asString(CryptoHash hash){
        CryptoService cryptoService = ApplicationContextProvider.getCtx().getBean(CryptoService.class);
        return cryptoService.fromCryptoHash(hash);
    }

    public CryptoHash asString(String hash){
        CryptoService cryptoService = ApplicationContextProvider.getCtx().getBean(CryptoService.class);
        return cryptoService.toCryptoHash(hash);
    }


}
