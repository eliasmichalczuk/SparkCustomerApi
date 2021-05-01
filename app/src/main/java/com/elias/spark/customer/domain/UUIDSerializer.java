package com.elias.spark.customer.domain;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class UUIDSerializer extends StdSerializer<UUID> {

	private static final long serialVersionUID = -1417558577484567705L;

	public UUIDSerializer() {
		this(null);
	}

	public UUIDSerializer(Class<UUID> t) {
		super(t);
	}

	@Override
	public void serialize(UUID value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeString(value.toString());
	}

	@Override
	public void serializeWithType(UUID value, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSer)
	        throws IOException {

		serialize(value, gen, provider);
	}

}
