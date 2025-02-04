package csd230.lab2.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class Magazine extends Publication {

    @Column(name = "order_qty", nullable = true)
    private int orderQty;

    @Column(name = "curr_issue")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Ensures proper date formatting
    private LocalDate currIssue;

    // Constructors
    public Magazine() {}

    public Magazine(double price, int quantity, String description, String title, int copies, int orderQty, LocalDate currIssue) {
        super(price, quantity, description, title, copies);
        this.orderQty = orderQty;
        this.currIssue = currIssue;
    }

    // Getters and Setters
    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public LocalDate getCurrIssue() {
        return currIssue;
    }

    public void setCurrIssue(LocalDate currIssue) {
        this.currIssue = currIssue;
    }

    // toString, equals, hashCode (if necessary)
}
