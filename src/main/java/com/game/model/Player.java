package com.game.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(catalog = "game", name = "player")
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String name;

    @OneToOne(mappedBy = "player",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    private Score score;
}
