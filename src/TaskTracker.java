import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TaskTracker {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Por favor, forneça um comando.");
            return;
        }

        String comando = args[0];

        if(comando.equalsIgnoreCase("add")) {
            long id = gerarNovoId();
            Task task = new Task(id, args[1]);
            salvarTarefa(task);
        }
    }

    public static void salvarTarefa(Task task) {
        Path caminho = Paths.get("tasks.json");

        String jsonDaTarefa = task.toJson() + System.lineSeparator();

        try {
            Files.writeString(caminho, jsonDaTarefa,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);

            System.out.println("Task added successfully (ID: " + task.getId() + ")");
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static long gerarNovoId() {
        Path caminho = Paths.get("tasks.json");
        try {
            if (!Files.exists(caminho)) {
                return 1L;
            }
            return Files.readAllLines(caminho).size() + 1;

        } catch (IOException e) {
            return 1L;
        }
    }
}
