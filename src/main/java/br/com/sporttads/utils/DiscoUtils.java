package br.com.sporttads.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DiscoUtils {

	private static final Logger LOG = LoggerFactory.getLogger(DiscoUtils.class);

	private static final String DIRETORIO_IMAGENS = "C:\\imagens";

	public String salvar(MultipartFile arquivo) {

		Path diretorioPath = Paths.get(DIRETORIO_IMAGENS);

		Path arquivoPath = diretorioPath.resolve(arquivo.getOriginalFilename());

		String caminho = String.valueOf(arquivoPath);
		
		LOG.info(caminho);
		try {

			Files.createDirectories(diretorioPath);
			arquivo.transferTo(arquivoPath.toFile());
			LOG.info("Foto salva com sucesso!");
		} catch (IOException e) {
			LOG.info("Erro na tentativa de salvar o arquivo: ", e.getMessage());
			throw new RuntimeException(e);
		}
		return caminho;
	}
}