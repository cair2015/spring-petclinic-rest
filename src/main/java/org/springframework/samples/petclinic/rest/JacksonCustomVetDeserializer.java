/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.rest;

import java.io.IOException;

import org.springframework.samples.petclinic.model.Vet;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * @author Robin Cai
 * @date 7/14/2018
 *
 */

public class JacksonCustomVetDeserializer extends StdDeserializer<Vet> {

    public JacksonCustomVetDeserializer(){
        this(null);
    }

    public JacksonCustomVetDeserializer(Class<Vet> t) {
        super(t);
    }

    @Override
    public Vet deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        JsonNode node = parser.getCodec().readTree(parser);
        Vet vet = new Vet();
        int id = node.get("id").asInt();
        String firstName = node.get("firstName").asText(null);
        String lastName = node.get("lastName").asText(null);
        if (!(id == 0)) {
            vet.setId(id);
        }
        vet.setFirstName(firstName);
        vet.setLastName(lastName);
        return vet;
    }

}
