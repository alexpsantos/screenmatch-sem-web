package br.com.alex.screenmatch;

import br.com.alex.screenmatch.model.DadosSerie;
import br.com.alex.screenmatch.service.ConsumoAPI;
import br.com.alex.screenmatch.service.ConverteDados;
import br.com.alex.screenmatch.service.IConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

        var consumoAPI = new ConsumoAPI();
        var json = consumoAPI.obterDados("https://omdbapi.com/?t=avenger&apikey=77e94eaa");
        System.out.println(json);

        ConverteDados conversor = new ConverteDados();
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

        System.out.println(dados);


    }
}
