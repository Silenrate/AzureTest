package edu.eci.tacs.controllers.dtos;

public class GetFood {
    private long id;
    private String name;
    private GetUser user;

    public GetFood() {
    }

    public GetFood(long id, String name, GetUser user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GetUser getUser() {
        return user;
    }

    public void setUser(GetUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "GetFood{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user=" + user +
                '}';
    }
}
