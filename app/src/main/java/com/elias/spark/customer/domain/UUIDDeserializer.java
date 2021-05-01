package com.elias.spark.customer.domain;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;

public class UUIDDeserializer extends StdDeserializer<UUID> {

	private static final long serialVersionUID = -1417558577484567705L;

	public UUIDDeserializer() {
		this(null);
	}

	public UUIDDeserializer(Class<UUID> t) {
		super(t);
	}

	@Override
	public UUID deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

		JsonNode node = p.getCodec().readTree(p);
		return UUID.fromString(node.asText());
	}

	@Override
	public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer)
	        throws IOException {
		return deserialize(p, ctxt);
	}

}
