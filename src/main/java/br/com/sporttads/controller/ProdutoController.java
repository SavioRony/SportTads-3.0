package br.com.sporttads.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sporttads.model.ImagemModel;
import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.service.ProdutoService;


@Controller
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;


	@GetMapping("/listaproduto")
	public ModelAndView getAll() {
		List<ProdutoModel> produtos = produtoService.getAll();
		ModelAndView andView = new ModelAndView("Produto/ListaProduto");
		andView.addObject("produtos",produtos);
		return andView;
	}
	
	@GetMapping("**/editarproduto/{idproduto}")
	public ModelAndView editar(@PathVariable("idproduto") Integer idproduto) {
		ProdutoModel produto = produtoService.getById(idproduto);
		ModelAndView andView = new ModelAndView("Produto/AlteraProduto");
		andView.addObject("produtoObj", produto);
		
		return andView;
	}

	@GetMapping("**/visualizarproduto/{idproduto}")
	public ModelAndView visualizarProduto(@PathVariable("idproduto") Integer idproduto) {
		ProdutoModel produto = produtoService.getById(idproduto);
		ModelAndView andView = new ModelAndView("Produto/VisualizarProduto");
		andView.addObject("produtoObj", produto);

		return andView;
	}
	
	@GetMapping("**/inativaativarproduto/{idproduto}")
	public ModelAndView inativaAtiva(@PathVariable("idproduto") Integer idproduto) {
		ProdutoModel produto = produtoService.getById(idproduto);
		ModelAndView andView = new ModelAndView("Produto/InativaAtivaProduto");
		andView.addObject("produtoObj", produto);
		
		return andView;
	}

	@PostMapping("**/salvarproduto")
	public ModelAndView post(ProdutoModel produto) {
		ProdutoModel produtoEditado = produtoService.save(produto);
		ModelAndView andView = new ModelAndView("Produto/AlterarImagemProduto");
		andView.addObject("produto",produtoEditado);
		return andView;
	}

	@PostMapping("**/inativarreativarproduto")
	public ModelAndView ativaReativar(ProdutoModel produto) {
		produtoService.save(produto);
		ModelAndView andView = new ModelAndView("Produto/ListaProduto");
		List<ProdutoModel> produtos = produtoService.getAll();
		andView.addObject("produtos",produtos);
		return andView;
	}

	@PostMapping("**/pesquisarproduto")
	public ModelAndView pesquisarproduto(@RequestParam("nomepesquisa") String nomepesquisa) {
		ModelAndView andView = new ModelAndView("Produto/ListaProduto");
		andView.addObject("produtos", produtoService.findPessoaByName(nomepesquisa.toUpperCase()));

		return andView;
	}
	
	@GetMapping("/cadastro")
	public String cadastro() {
		return "redirect:/produtos/cadastroproduto";
		
	}

	@GetMapping("**/cadastroproduto")
	public ModelAndView telaCadastro() {
		ModelAndView andView = new ModelAndView("Produto/CadastroProduto");
		return andView;
	}
	
	@PostMapping("**/alterarproduto")
	public ModelAndView telaAltera(ProdutoModel produto) {
		ProdutoModel produtoEditado = produtoService.save(produto);
		ModelAndView andView = new ModelAndView("Produto/AlterarImagemProduto");
		andView.addObject("produto",produtoEditado);
		return andView;
	}
	
	@PostMapping("/save")
	public String saveImagem(@ModelAttribute(name = "imagem") ProdutoModel produto,
			@RequestParam("arquivoImagem") MultipartFile multipartfile) throws IOException{

		String nomeArquivo = StringUtils.cleanPath(StringUtils.cleanPath(multipartfile.getOriginalFilename()));
		produto.setLogo(nomeArquivo);
		ProdutoModel produtoSalvo = produtoService.save(produto);
		
		String uploadDiretorio = "./imagem-salvas/"+produtoSalvo.getId();
		
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
	
		return "redirect:/produtos/listaproduto";
	}

}
