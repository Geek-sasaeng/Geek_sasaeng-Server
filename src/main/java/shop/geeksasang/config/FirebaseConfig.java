package shop.geeksasang.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Arrays;

@Configuration
public class FirebaseConfig {

    @Bean
    public GoogleCredentials getGoogleCredentials() throws IOException {
        return GoogleCredentials
                .fromStream(new ClassPathResource("firebase/firebase_service_key.json").getInputStream())
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
    }

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(getGoogleCredentials())
                .build();

        return FirebaseApp.initializeApp(options);
    }

    //비동기 통신을 위함
    @Bean
    public ListeningExecutorService firebaseAppExecutor() {
        return MoreExecutors.newDirectExecutorService();
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
}