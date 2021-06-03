package br.com.sporttads.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.rule.Mode;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sporttads.model.ImagemModel;
import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.service.ImagemService;
import br.com.sporttads.service.ProdutoService;
import br.com.sporttads.utils.DiscoUtils;
import br.com.sporttads.utils.FileUploadUtil;

@Controller
@RequestMapping("/imagens")
public class ImagemController {

	@Autowired
	private ImagemService imagemService;

	@Autowired
	private ProdutoService produtoService;

	private List<ImagemModel> imagens = new ArrayList<>();


	@Autowired
	private DiscoUtils disc;

	@PostMapping("/adicionar")
	public ModelAndView adicionar(@ModelAttribute(name = "produto") ProdutoModel produto, @RequestParam MultipartFile[] fotos) throws IOException {

		for(MultipartFile foto : fotos){
			ImagemModel imagem = new ImagemModel(StringUtils.cleanPath(StringUtils.cleanPath(foto.getOriginalFilename())), produto.getId());
			String uploadDiretorio = "./imagens-produto/" + imagem.getIdProduto();
			FileUploadUtil.saveFile(uploadDiretorio, foto, imagem.getNomeImagem());
			imagemService.save(imagem);
		}

		imagens = imagemService.findByIdProduto(produto.getId());
		ModelAndView andView = new ModelAndView("Produto/imagens");
		andView.addObject("imagens", imagens);
		andView.addObject("produto", produto);
		return andView;
	}

	@GetMapping("/remover/{nome}/{id}")
	public ModelAndView remover(@PathVariable("nome") String nome, @PathVariable("id") Integer id) throws IOException {
		ProdutoModel produto = new ProdutoModel();
		produto.setId(id);

		for (ImagemModel imagem : imagens){
			if(imagem.getNomeImagem().equals(nome)){
				imagemService.deletar(imagem.getId());
				break;
			}
		}

		imagens = imagemService.findByIdProduto(produto.getId());
		ModelAndView andView = new ModelAndView("Produto/Imagens");
		andView.addObject("imagens", imagens);
		andView.addObject("produto", produto);
		return andView;
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

	@GetMapping("/salvar")
	public String saveImagem() {
		return "redirect:/produtos/listaproduto";
	}



}
