package fi.my.pkg.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

final class Bison {
	private final Gson gson = new Gson();

	private class Items {
		private class Item {
			private class Volume {
				private String title;
			}

			private Volume volumeInfo;
		}

		private List<Item> items;
	}

	String titleFromJson(InputStream response) {
		final InputStreamReader reader = new InputStreamReader(response, Charset.defaultCharset());
		JsonReader jsonReader = new JsonReader(new BufferedReader(reader));
		Items i = gson.fromJson(jsonReader, Items.class);
		return i.items.get(0).volumeInfo.title;
	}

}
