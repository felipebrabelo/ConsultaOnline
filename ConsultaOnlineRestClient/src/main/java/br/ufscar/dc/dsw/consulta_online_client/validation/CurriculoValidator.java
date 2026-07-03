package br.ufscar.dc.dsw.consulta_online_client.validation;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import br.ufscar.dc.dsw.consulta_online_client.domain.FileEntity;

@Component
public class CurriculoValidator {

    public FileEntity criarCurriculo(MultipartFile arquivo, BindingResult result, boolean obrigatorio) {
        if (arquivo == null || arquivo.isEmpty()) {
            if (obrigatorio) {
                result.rejectValue("curriculo", "profissional.curriculo.required");
            }
            return null;
        }

        String nomeOriginal = arquivo.getOriginalFilename();
        String nomeArquivo = nomeOriginal == null ? "" : Paths.get(nomeOriginal).getFileName().toString();

        if (nomeArquivo.isBlank() || !nomeArquivo.toLowerCase().endsWith(".pdf")) {
            result.rejectValue("curriculo", "profissional.curriculo.pdf");
            return null;
        }

        try {
            return new FileEntity(nomeArquivo, "application/pdf", arquivo.getBytes());
        } catch (IOException e) {
            result.rejectValue("curriculo", "profissional.curriculo.read");
            return null;
        }
    }
}
