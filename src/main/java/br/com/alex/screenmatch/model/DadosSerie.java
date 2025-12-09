package br.com.alex.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String avaliacao) {

   /* @JsonProperty("Title")
    private String titulo;
    @JsonProperty("totalSeasons")
    private Integer totalTemporadas;
    @JsonProperty("imdbRating")
    private String avaliacao;*/


}
