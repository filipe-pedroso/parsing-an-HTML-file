import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws Exception {
        File htmlFile = new File("index.html");
        List<String> lines = Files.readAllLines(htmlFile.toPath());

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Runnable> tasks = new ArrayList<>();

        for (String line : lines) {
            tasks.add(() -> processTag(line, "<link", "href"));
            tasks.add(() -> processTag(line, "<img", "src"));
            tasks.add(() -> processTag(line, "<script", "src"));
        }

        for (Runnable task : tasks) {
            executorService.submit(task);
        }

        processSecurityTag();

        executorService.shutdown();
    }

    public static void processTag(String line, String tag, String attribute) {
        String regex = tag + "[^>]*" + attribute + "=\"([^\"]+)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String filePath = matcher.group(1);
            File file = new File(filePath);
            if (file.exists()) {
                System.out.println("Arquivo: " + filePath + " | Tamanho: " + file.length() + " bytes");
            } else {
                System.out.println("Arquivo não encontrado: " + filePath);
            }
        }
    }

    public static void processSecurityTag() {
        String entityUrl = "https://letsencrypt.org/";

        try {
            InetAddress address = InetAddress.getByName(new URL(entityUrl).getHost());
            System.out.println("<security entity=\"" + entityUrl + "\"/>");
            System.out.println("IP da entidade certificadora: " + address.getHostAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
