package com.example.carros.domain.model;

import lombok.Data;

import javax.persistence.*;

@Entity()
@Data
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String tipo;
    private String descricao;
    private String urlFoto;
    private String urlVideo;
    private String latitude;
    private String longitude;

    public static Carro init() {
        return new Carro();
    }

    public Carro withId(Integer id) {
        this.id = id;
        return this;
    }

    public Carro withNome(String nome) {
        this.nome = nome;
        return this;
    }

    public Carro withTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public Carro withDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public Carro withUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
        return this;
    }

    public Carro withUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
        return this;
    }

    public Carro withLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public Carro withLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

}
