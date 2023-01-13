package br.pro.hashi.sdx.rest.gson.transform;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.function.Consumer;

import com.google.gson.Gson;

import br.pro.hashi.sdx.rest.transform.Serializer;
import br.pro.hashi.sdx.rest.transform.extension.Plumber;

public class GsonSerializer implements Serializer {
	private final Plumber plumber;
	private final Gson gson;

	public GsonSerializer(Gson gson) {
		this.plumber = new Plumber();
		this.gson = gson;
	}

	@Override
	public <T> Reader toReader(T body, Class<T> type) {
		Reader reader;
		Consumer<Writer> consumer = (writer) -> {
			gson.toJson(body, type, writer);
		};
		try {
			reader = plumber.connect(consumer);
		} catch (IOException exception) {
			throw new UncheckedIOException(exception);
		}
		return reader;
	}
}
