import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskTracker {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Por favor, forneça um comando.");
            return;
        }

        String comando = args[0];

        if (comando.equalsIgnoreCase("add")) {

            List<Task> tarefas = carregarTarefas();

            long novoId = tarefas.isEmpty() ? 1L : tarefas.get(tarefas.size() - 1).getId() + 1;

            Task novaTarefa = new Task(novoId, args[1]);
            tarefas.add(novaTarefa);

            salvarTodasTarefas(tarefas);

        } else if (comando.equalsIgnoreCase("list")) {
            List<Task> tarefas = carregarTarefas();

            if (args.length == 1) {
                exibirTarefas(tarefas);
            } else {
                String filtro = args[1];
                List<Task> filtradas = new ArrayList<>();
                for (Task t : tarefas) {
                    if (t.getStatus().name().equalsIgnoreCase(filtro.replace("-", "_"))) {
                        filtradas.add(t);
                    }
                }
                exibirTarefas(filtradas);
            }
        } else if (comando.equalsIgnoreCase("delete")) {
            if (args.length < 2) {
                System.out.println("Erro: Informe o ID da tarefa que deseja deletar.");
                return;
            }

            List<Task> tarefas = carregarTarefas();
            long idParaDeletar = Long.parseLong(args[1]);

            boolean removido = tarefas.removeIf(t -> t.getId() == idParaDeletar);

            if (removido) {
                salvarTodasTarefas(tarefas);
                System.out.println("Task deleted successfully");
            } else {
                System.out.println("Erro: Tarefa com ID " + idParaDeletar + " não encontrada.");
            }
        } else if (comando.equalsIgnoreCase("update")) {
            if (args.length < 3) {
                System.out.println("Erro: Use: update [id] [nova descrição]");
                return;
            }
            List<Task> tarefas = carregarTarefas();
            long id = Long.parseLong(args[1]);
            String novaDesc = args[2];

            for (Task t : tarefas) {
                if (t.getId() == id) {
                    t.setDescription(novaDesc);
                    t.setUpdatedAt(LocalDateTime.now());
                    salvarTodasTarefas(tarefas);
                    System.out.println("Tarefa atualizada com sucesso!");
                    return;
                }
            }
            System.out.println("Erro: Tarefa não encontrada.");
        } else if (comando.startsWith("mark-")) {
            List<Task> tarefas = carregarTarefas();
            long id = Long.parseLong(args[1]);

            Status novoStatus = comando.contains("done") ? Status.DONE : Status.IN_PROGRESS;

            for (Task t : tarefas) {
                if (t.getId() == id) {
                    t.setStatus(novoStatus);
                    salvarTodasTarefas(tarefas);
                    System.out.println("Status atualizado!");
                    return;
                }
            }
            System.out.println("Erro: Tarefa não encontrada.");
        }
    }

    private static List<Task> carregarTarefas() {
        List<Task> tarefas = new ArrayList<>();
        Path caminho = Paths.get("tasks.json");

        if (!Files.exists(caminho)) return tarefas;

        try {
            List<String> linhas = Files.readAllLines(caminho);
            for (String linha : linhas) {
                if (linha.trim().isEmpty()) continue;

                long id = Long.parseLong(extrairValor(linha, "id"));
                String desc = extrairValor(linha, "description");
                Status status = Status.valueOf(extrairValor(linha, "status"));

                tarefas.add(new Task(id, desc, status, LocalDateTime.now(), LocalDateTime.now()));
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar: " + e.getMessage());
        }
        return tarefas;
    }

    private static String extrairValor(String linha, String chave) {
        String busca = "\"" + chave + "\":";
        int inicio = linha.indexOf(busca) + busca.length();

        if (linha.charAt(inicio) == '\"') inicio++;

        int fim = linha.indexOf(",", inicio);
        if (fim == -1) fim = linha.indexOf("}", inicio);

        String resultado = linha.substring(inicio, fim).replace("\"", "");
        return resultado.trim();
    }

    private static void salvarTodasTarefas(List<Task> tarefas) {
        Path caminho = Paths.get("tasks.json");
        StringBuilder sb = new StringBuilder();

        for (Task t : tarefas) {
            sb.append(t.toJson()).append(System.lineSeparator());
        }

        try {
            Files.writeString(caminho, sb.toString());
            System.out.println("Mudanças salvas com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    private static void exibirTarefas(List<Task> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }

        System.out.printf("%-3s | %-12s | %s%n", "ID", "STATUS", "DESCRIÇÃO");
        System.out.println("-------------------------------------------------");

        for (Task t : lista) {
            System.out.printf("%-3d | %-12s | %s%n",
                    t.getId(),
                    t.getStatus(),
                    t.getDescription());
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
