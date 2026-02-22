package project3;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {

    private String title;
    private String description;
    private String priority;
    private LocalDate dueDate;
    private String status;

    public Task(String title, String description, String priority, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.status = "Pending";
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public LocalDate getDueDate() { return dueDate; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public boolean isOverdue() {
        return LocalDate.now().isAfter(dueDate) && status.equals("Pending");
    }
}