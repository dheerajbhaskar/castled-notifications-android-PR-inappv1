package io.castled.notifications.service;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CastledRetrofitClient {

    private static final String BASE_URL = "https://%s.castled.io/backend/v1/push/";

    private static final Map<String, String> clusterMap = new HashMap<String, String>();
    static {
        clusterMap.put("app", "app");
        clusterMap.put("ap", "in");
        clusterMap.put("test", "test");
    };

    private static Retrofit retrofit;

    public static synchronized Retrofit getInstance(String instanceId) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(String.format(BASE_URL, getCluster(instanceId)))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static String getCluster(String instanceId) {
        String[] parts = instanceId.split("-");
        return clusterMap.get(parts[0]);
    }

}
