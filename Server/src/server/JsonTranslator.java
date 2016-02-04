package server;

import com.google.gson.Gson;

/**
 * Wrapper around gson Translator
 *
 * Created by rodriggl on 2/3/2016.
 */
public class JsonTranslator {
    private static Gson gson;

    public static Gson getGson() {
        if (gson == null)
            initializeGson();
        return gson;
    }

    private static void initializeGson() {
        gson = new Gson();
    }
}
