import java.time.LocalDateTime;

public class Task {

    private Long id;

    private Status status;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Task(long id, String description) {
        this.id = id;
        this.description = description;
        this.status = Status.TODO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String toJson() {
        return "{" +
                "\"id\":" + id + "," +
                "\"description\":\"" + description + "\"," +
                "\"status\":\"" + status + "\"," +
                "\"createdAt\":\"" + createdAt + "\"," +
                "\"updatedAt\":\"" + updatedAt + "\"" +
                "}";
    }
    public Long getId() {
        return id; }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();

    }
}
