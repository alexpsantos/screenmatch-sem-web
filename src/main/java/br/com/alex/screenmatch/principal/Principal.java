package br.com.alex.screenmatch.principal;

import br.com.alex.screenmatch.model.DadosEpisodios;
import br.com.alex.screenmatch.model.DadosSerie;
import br.com.alex.screenmatch.model.DadosTemporada;
import br.com.alex.screenmatch.model.Episodio;
import br.com.alex.screenmatch.service.ConsumoAPI;
import br.com.alex.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

//        for (int i = 0; i < dadosSerie.totalTemporadas(); i++) {
//            List<DadosEpisodios> episodioTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodioTemporada.size(); j++) {
//                System.out.println(episodioTemporada.get(j).titulo());
//
//            }
//        }
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));



        List<DadosEpisodios> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());


//        System.out.println("\nTop 10 episodios");
//        dadosEpisodios.stream()
//                .filter(e -> !e.avalicao().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primeiro filtro (N/A) " + e))
//                .sorted(Comparator.comparing(DadosEpisodios::avalicao).reversed())
//                .peek(e -> System.out.println("ordenacao " + e))
//                .limit(10)
//                .peek(e -> System.out.println("Limite " + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("Ordenacao " + e))
//                .forEach(System.out::println);
//
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
        ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

//        System.out.println("Digite um trecho do titulo do episódio");
//        String trechoDoTitulo = scanner.nextLine();
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoDoTitulo.toUpperCase()))
//                .findFirst();
//
//        if (episodioBuscado.isPresent()) {
//            System.out.println("Episódio encontrado!");
//            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
//        } else {
//            System.out.println("Episódio não encontrado!");
//        }

//
//
//        System.out.println("A partir de que ano você gostaria de ver os episódios ? ");
//        Integer ano = scanner.nextInt();
//        scanner.nextLine();
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null &&  e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                        " Episódio: " + e.getTitulo() +
//                                " Data de lançamento: " + e.getDataLancamento().format(formatador)
//                ));



        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(avaliacoesPorTemporada);


        DoubleSummaryStatistics est = episodios.stream()
                .filter(e ->e.getAvaliacao()> 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println("Media: " + est.getAverage());
        System.out.println("Melhor Epi: " + est.getMax());
        System.out.println("Pior Epi: " + est.getMin());
        System.out.println("Quantidade: " + est.getCount());





    }
}
