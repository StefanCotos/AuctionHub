package org.example.pa_project.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "auctions", schema = "pa_project", catalog = "")
public class Auction {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "initial_price")
    private int initialPrice;
    @Basic
    @Column(name = "deadline")
    private Date deadline;

    //@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @Temporal(TemporalType.TIME)
    @Column(name = "time")
    private LocalTime time;
    @Basic
    @Column(name = "users_id")
    private int usersId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(int initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

//    public void setTime(String receivedTime) throws ParseException {
//        System.out.println("Functia setTime");
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//        long ms = sdf.parse(receivedTime).getTime();
//        this.time = new Time(ms);
//    }

    public int getUsersId() {
        return usersId;
    }

    public void setUsersId(int usersId) {
        this.usersId = usersId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Auction auction = (Auction) object;
        return id == auction.id && initialPrice == auction.initialPrice && usersId == auction.usersId && Objects.equals(title, auction.title) && Objects.equals(description, auction.description) && Objects.equals(deadline, auction.deadline) && Objects.equals(time, auction.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, initialPrice, deadline, time, usersId);
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", initialPrice=" + initialPrice +
                ", deadline=" + deadline +
                ", time=" + time +
                ", usersId=" + usersId +
                '}';
    }

}
