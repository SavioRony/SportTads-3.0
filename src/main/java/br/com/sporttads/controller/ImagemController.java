package br.com.sporttads.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sporttads.model.ImagemModel;
import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.service.ImagemService;
import br.com.sporttads.service.ProdutoService;
import br.com.sporttads.utils.DiscoUtils;

@Controller
@RequestMapping("/imagens")
public class ImagemController {

	@Autowired
	private ImagemService imagemService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private DiscoUtils disc;

	@PostMapping("/{idProduto}")
	public ResponseEntity<?> save(@PathVariable Integer idProduto, @RequestParam MultipartFile foto) {
		ProdutoModel produto = produtoService.getById(idProduto);
		String caminho = disc.salvar(foto);
		return new ResponseEntity<>(imagemService.salvar(caminho, produto), HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(imagemService.listar(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		return new ResponseEntity<>(imagemService.consultar(id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		imagemService.deletar(id);
		return ResponseEntity.ok("Imagem imagemService com sucesso!");
	}
	
	@PostMapping("/save")
	public String saveImagem(@ModelAttribute(name = "imagem") ProdutoModel produtoImagem, RedirectAttributes ra, 
			@RequestParam("arquivoImagem") MultipartFile multipartfile) throws IOException{
		ImagemModel imagem = new ImagemModel();
		imagem.setIdProduto(produtoImagem.getId());
		System.out.println("Produto ID: " + imagem.getIdProduto());
		String nomeArquivo = StringUtils.cleanPath(StringUtils.cleanPath(multipartfile.getOriginalFilename()));
		imagem.setLogo(nomeArquivo);
		ImagemModel imagemSalva = imagemService.save(imagem);
		
		String uploadDiretorio = "/imagem-salvas/"+imagemSalva.getId();
		
		Path uploadPath = Paths.get(uploadDiretorio);
		
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try(InputStream inputStream = multipartfile.getInputStream()){
			Path filePath = uploadPath.resolve(nomeArquivo);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		}catch(IOException e){
			throw new IOException("Não foi possivel salvar o arquivo: " + nomeArquivo);
		}
		ra.addFlashAttribute("mensagem", "Produto alterado com sucesso.");
		
		return "redirect:/produtos/listaproduto";
	}

}
