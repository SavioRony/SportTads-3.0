package br.com.sporttads.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguracao implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		Path imagemUploadDiretorio = Paths.get("./imagem-principal");
		String imaUploadPath = imagemUploadDiretorio.toFile().getAbsolutePath();
		registry.addResourceHandler("/imagem-principal/**").addResourceLocations("file:/" + imaUploadPath + "/");
		
		Path imagensProdutoUploadDiretorio= Paths.get("./imagens-produto");
		String imgUploadPath = imagensProdutoUploadDiretorio.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/imagens-produto/**").addResourceLocations("file:/" + imgUploadPath + "/");
		
	}
	
	
}
