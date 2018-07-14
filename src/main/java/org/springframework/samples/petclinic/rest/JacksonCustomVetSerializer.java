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
import java.text.Format;
import java.text.SimpleDateFormat;

import org.springframework.samples.petclinic.model.*;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author Robin Cai
 * @Date 7/14/2018
 */

public class JacksonCustomVetSerializer extends StdSerializer<Vet> {

    public JacksonCustomVetSerializer() {
        this(null);
    }

    public JacksonCustomVetSerializer(Class<Vet> t) {
        super(t);
    }

    @Override
    public void serialize(Vet vet, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        Format formatter = new SimpleDateFormat("yyyy/MM/dd");
        jgen.writeStartObject();
        if (vet.getId() == null) {
            jgen.writeNullField("id");
        } else {
            jgen.writeNumberField("id", vet.getId());
        }

        jgen.writeStringField("firstName", vet.getFirstName());
        jgen.writeStringField("lastName", vet.getLastName());
        // write specialty array
        jgen.writeArrayFieldStart("specialties");
        for(Specialty specialty: vet.getSpecialties()){
            jgen.writeStartObject(); // specialty
            jgen.writeStringField("name", specialty.getName());
            jgen.writeEndObject(); //specialty
        }
        jgen.writeEndArray();  // specialties
        // write visits array
        jgen.writeArrayFieldStart("visits");
        for (Visit visit : vet.getVisits()) {
            jgen.writeStartObject(); // visit
            jgen.writeNumberField("id", visit.getId());
            jgen.writeStringField("date", formatter.format(visit.getDate()));
            jgen.writeStringField("description", visit.getDescription());
            jgen.writeNumberField("pet", visit.getPet().getId());
            jgen.writeNumberField("vet", visit.getVet().getId());
            jgen.writeEndObject(); // visit
        }
        jgen.writeEndArray(); // visits
        jgen.writeEndObject(); // vet
    }

}
