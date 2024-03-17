package com.game.model;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@EqualsAndHashCode(exclude = "player")
@NoArgsConstructor
@AllArgsConstructor
@Table(catalog = "game", name = "score")
@Entity
public class Score {
    @Id
    @Column(name = "player_id")
    Integer playerId;
    int score;

    @OneToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    @MapsId("player_id")
    private Player player;
}
