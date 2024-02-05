package objectInfrasructure;

import annotations.Column;
import annotations.Table;

import java.util.UUID;

@Table(name = "users")
public class Employee {

    @Column(name = "id", primaryKey = true)
    private UUID userID;
    @Column(name = "name")
    private String userName;
    @Column(name = "eMail")
    private String userEMail;

    public Employee(String userName, String userEMail) {
        this.userID = UUID.randomUUID();
        this.userName = userName;
        this.userEMail = userEMail;
    }
}

