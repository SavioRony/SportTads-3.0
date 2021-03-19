package br.com.sporttads.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

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
import br.com.sporttads.utils.FileUploadUtil;

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
	public String saveImagem(@ModelAttribute(name = "produto") ProdutoModel produto,
			@RequestParam("arquivoImagem01") MultipartFile multipartfiles01,
			@RequestParam("arquivoImagem02") MultipartFile multipartfiles02,
			@RequestParam("arquivoImagem03") MultipartFile multipartfiles03,
			@RequestParam("arquivoImagem04") MultipartFile multipartfiles04) throws IOException {
		
		ImagemModel imagem = new ImagemModel();
		imagem.setIdProduto(produto.getId());
		ArrayList<MultipartFile> multipartfiles = new ArrayList<>();

		if (!multipartfiles01.isEmpty()) {
			imagem.setImg01(StringUtils.cleanPath(StringUtils.cleanPath(multipartfiles01.getOriginalFilename())));
			multipartfiles.add(multipartfiles01);
		}else {
			imagem.setImg01("NotFile");
		}
		
		if (!multipartfiles02.isEmpty()) {
			imagem.setImg02(StringUtils.cleanPath(StringUtils.cleanPath(multipartfiles02.getOriginalFilename())));
			multipartfiles.add(multipartfiles02);
		}else {
			imagem.setImg02("NotFile");
		}
		
		if (!multipartfiles03.isEmpty()) {
			imagem.setImg03(StringUtils.cleanPath(StringUtils.cleanPath(multipartfiles03.getOriginalFilename())));
			multipartfiles.add(multipartfiles03);
		}else {
			imagem.setImg03("NotFile");
		}
		
		if (!multipartfiles04.isEmpty()) {
			imagem.setImg04(StringUtils.cleanPath(StringUtils.cleanPath(multipartfiles04.getOriginalFilename())));
			multipartfiles.add(multipartfiles04);
		}else {
			imagem.setImg04("NotFile");
		}

		ImagemModel imagemSalva = imagemService.save(imagem);

		String uploadDiretorio = "./imagens-produto/" + imagemSalva.getIdProduto();

		for (MultipartFile multipartfile : multipartfiles) {
			String imagemNome = StringUtils.cleanPath(StringUtils.cleanPath(multipartfile.getOriginalFilename()));
			FileUploadUtil.saveFile(uploadDiretorio, multipartfile, imagemNome);
		}

		return "redirect:/produtos/listaproduto";
	}
	
	@PostMapping("/alterar")
	public String alterarImagem(@ModelAttribute(name = "imagem") ImagemModel imagem,
			@RequestParam("arquivoImagem01") MultipartFile multipartfiles01,
			@RequestParam("arquivoImagem02") MultipartFile multipartfiles02,
			@RequestParam("arquivoImagem03") MultipartFile multipartfiles03,
			@RequestParam("arquivoImagem04") MultipartFile multipartfiles04) throws IOException {
		
			ImagemModel i = imagemService.consultar(imagem.getId());
			ArrayList<MultipartFile> multipartfiles = new ArrayList<>();

			if (!multipartfiles01.isEmpty()) {
				imagem.setImg01(StringUtils.cleanPath(StringUtils.cleanPath(multipartfiles01.getOriginalFilename())));
				multipartfiles.add(multipartfiles01);
			}else {
				imagem.setImg01(i.getImg01());
			}
			
			if (!multipartfiles02.isEmpty()) {
				imagem.setImg02(StringUtils.cleanPath(StringUtils.cleanPath(multipartfiles02.getOriginalFilename())));
				multipartfiles.add(multipartfiles02);
			}else {
				imagem.setImg02(i.getImg02());
			}
			
			if (!multipartfiles03.isEmpty()) {
				imagem.setImg03(StringUtils.cleanPath(StringUtils.cleanPath(multipartfiles03.getOriginalFilename())));
				multipartfiles.add(multipartfiles03);
			}else {
				imagem.setImg03(i.getImg03());
			}
			
			if (!multipartfiles04.isEmpty()) {
				imagem.setImg04(StringUtils.cleanPath(StringUtils.cleanPath(multipartfiles04.getOriginalFilename())));
				multipartfiles.add(multipartfiles04);
			}else {
				imagem.setImg04(i.getImg04());
			}


		ImagemModel imagemSalva = imagemService.save(imagem);

		String uploadDiretorio = "./imagens-produto/" + imagemSalva.getIdProduto();

		for (MultipartFile multipartfile : multipartfiles) {
			String imagemNome = StringUtils.cleanPath(StringUtils.cleanPath(multipartfile.getOriginalFilename()));
			FileUploadUtil.saveFile(uploadDiretorio, multipartfile, imagemNome);
		}

		return "redirect:/produtos/listaproduto";
	}
}
