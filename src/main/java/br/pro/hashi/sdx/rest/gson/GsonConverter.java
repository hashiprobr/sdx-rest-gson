package br.pro.hashi.sdx.rest.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import br.pro.hashi.sdx.rest.transform.extension.Converter;

/**
 * <p>
 * A Gson converter can convert objects of a source type to and from objects of
 * a target type.
 * </p>
 * <p>
 * The idea is that the source type is not supported by Gson, but the target
 * type is (possibly via another converter).
 * </p>
 * 
 * @param <S> the source type
 * @param <T> the target type
 */
public interface GsonConverter<S, T> extends Converter<S, T> {
	/**
	 * <p>
	 * Obtains a {@link JsonSerializer<S>} based on this converter.
	 * </p>
	 * <p>
	 * Classes are encouraged to provide an alternative implementation.
	 * </p>
	 * 
	 * @return the Gson serializer
	 */
	default JsonSerializer<S> getSerializer() {
		return new JsonSerializer<>() {
			@Override
			public JsonElement serialize(S src, Type typeOfSrc, JsonSerializationContext context) {
				return context.serialize(to(src), getTargetType());
			}
		};
	}

	/**
	 * <p>
	 * Obtains a {@link JsonDeserializer<S>} based on this converter.
	 * </p>
	 * <p>
	 * Classes are encouraged to provide an alternative implementation.
	 * </p>
	 * 
	 * @return the Gson deserializer
	 */
	default JsonDeserializer<S> getDeserializer() {
		return new JsonDeserializer<>() {
			@Override
			public S deserialize(JsonElement json, Type typeOfS, JsonDeserializationContext context) {
				return from(context.deserialize(json, getTargetType()));
			}
		};
	}
}
