import io.getunleash.DefaultUnleash;
import io.getunleash.Unleash;
import io.getunleash.util.UnleashConfig;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        var flag = "example-flag";
        var url = "http://localhost:4242/api";
//        var url = "http://localhost:3063/api";
        var token = "default:development.unleash-insecure-api-token";

        UnleashConfig config = UnleashConfig.builder()
                .appName("default")
                .unleashAPI(url)
                .apiKey(token)
                .sendMetricsInterval(5)
                .build();

        Unleash unleash = new DefaultUnleash(config);


        Scanner sc = new Scanner(System.in);
        var count = 0;
        while (count ++ < 10) {
            boolean featureEnabled = unleash.isEnabled(flag);
            System.out.println("Feature enabled: " + featureEnabled);
            String input = sc.nextLine().trim(); // poor debugging
            if (input.equals("q") || input.equals("Q") || input.equals("quit")) {
                break;
            }
        }
    }
}


//docker run -it -p 3063:3063 -e STRICT=true -e UPSTREAM_URL=http://localhost:4242/api -e TOKENS='*:development.unleash-insecure-api-token' unleashorg/unleash-edge:v19.6.2 edge