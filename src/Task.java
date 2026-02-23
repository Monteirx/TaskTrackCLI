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

    public Task(long id, String description, Status status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setStatus(Status status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();

    }
}
