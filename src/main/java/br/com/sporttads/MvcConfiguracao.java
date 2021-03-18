package br.com.sporttads;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguracao implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		Path imagemUploadDiretorio = Paths.get("./imagem-salvas");
		String imaUploadPath = imagemUploadDiretorio.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/imagem-salvas/**").addResourceLocations("file:/" + imaUploadPath + "/");
		
	}
	
}
