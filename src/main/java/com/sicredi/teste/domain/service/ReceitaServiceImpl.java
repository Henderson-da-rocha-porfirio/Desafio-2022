package com.sicredi.teste.domain.service;

import com.sicredi.teste.domain.response.SocioResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;

@Service
public class ReceitaCsvService {

    @Autowired
    private ReceitaService receitaService;

    public void exportarCsv(Writer writer) {
        String line = "";
        String splitBy = ";";
        try {
            BufferedReader br = new BufferedReader(new FileReader("arquivo.csv"));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
            while ((line = br.readLine()) != null) {

                String[] socio = line.split(splitBy);
                SocioResponse socioResponse = new SocioResponse(socio[0], socio[1], socio[2], socio[3], receitaService.atualizarConta(socio[0], socio[1], socio[2], socio[3]));
                csvPrinter.printRecord(socioResponse.getCodSocio(), socioResponse.getNuConta(), socioResponse.getSaldo(), socioResponse.getStatus(), socioResponse.isResposta());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
