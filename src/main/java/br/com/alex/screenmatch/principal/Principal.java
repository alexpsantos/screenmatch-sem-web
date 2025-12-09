package br.com.alex.screenmatch.principal;

import br.com.alex.screenmatch.model.DadosEpisodios;
import br.com.alex.screenmatch.model.DadosSerie;
import br.com.alex.screenmatch.model.DadosTemporada;
import br.com.alex.screenmatch.service.ConsumoAPI;
import br.com.alex.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {


    private final String ENDERECO = "https://omdbapi.com/?t=";
    private final String API_KAY = "&apikey=77e94eaa";

    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();


    private Scanner scanner = new Scanner(System.in);

    public void exibirMenu() {
        System.out.println("Digite o nome da Serie para busca");
        String nomeSerie = scanner.nextLine();

        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KAY);

        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") +"&season="+i+ API_KAY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

        for (int i = 0; i < dadosSerie.totalTemporadas(); i++) {
            List<DadosEpisodios> episodioTemporada = temporadas.get(i).episodios();
            for (int j = 0; j < episodioTemporada.size(); j++) {
                System.out.println(episodioTemporada.get(j).titulo());

            }
        }
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }
}
