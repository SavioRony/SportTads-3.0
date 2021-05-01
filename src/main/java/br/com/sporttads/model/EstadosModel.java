package br.com.sporttads.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "estados")
@NoArgsConstructor
public class EstadosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String estado;
    private double percentual;

    public EstadosModel(String estado, double percentual) {
        this.estado = estado;
        this.percentual = percentual;
    }
}
