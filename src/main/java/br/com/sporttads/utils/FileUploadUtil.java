package br.com.sporttads.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

	public static void saveFile(String uploadDiretorio, MultipartFile multipartfile, String nomeArquivo) throws IOException {
		Path uploadPath = Paths.get(uploadDiretorio);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		try (InputStream inputStream = multipartfile.getInputStream()) {
			Path filePath = uploadPath.resolve(nomeArquivo);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new IOException("Não foi possivel salvar o arquivo: " + nomeArquivo);
		}
	}
}
