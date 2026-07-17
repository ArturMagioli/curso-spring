package com.magioli.jobportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter @Setter
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /*
    Relação opcional para essa lado.
    Dificilmente irá chamar users por role. Evita-se mapping bidirecional
     */
/*    @OneToMany(mappedBy = "role")
      private List<User> users = new ArrayList<>(); */
}
