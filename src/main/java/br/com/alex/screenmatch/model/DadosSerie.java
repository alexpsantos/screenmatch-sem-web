package br.com.alex.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DadosSerie {

    @JsonProperty("Title")
    private String titulo;
    @JsonProperty("totalSeasons")
    private Integer totalTemporadas;
    @JsonProperty("imdbRating")
    private String avaliacao;


}
