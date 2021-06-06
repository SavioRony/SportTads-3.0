package br.com.sporttads.controller;

import br.com.sporttads.model.ImagemModel;
import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.service.ImagemService;
import br.com.sporttads.service.ProdutoService;
import br.com.sporttads.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/imagens")
public class ImagemController {

    @Autowired
    private ImagemService imagemService;

    @Autowired
    private ProdutoService produtoService;

    private List<ImagemModel> imagens = new ArrayList<>();


    /**
     * Metodo usado para salvar uma ou mais imagens no banco de dados
     * @param produto
     * @param fotos
     * @return retona para a tela de cadastro/alterar imagens para continua a operação.
     * @throws IOException
     */
    @PostMapping("/adicionar")
    public ModelAndView adicionar(@ModelAttribute(name = "produto") ProdutoModel produto, @RequestParam MultipartFile[] fotos) throws IOException {

        //Salvar a imagem no diretorio informado e salva o caminho e o nome da imagem no banco
        for (MultipartFile foto : fotos) {
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

    /**
     * Metodo usado para remover a imagem conforme o nome passado.
     * @param nome
     * @param id
     * @return retorna para a tela de cadastro/alterar imagens com o produto;
     * @throws IOException
     */
    @GetMapping("/remover/{nome}/{id}")
    public ModelAndView remover(@PathVariable("nome") String nome, @PathVariable("id") Integer id) throws IOException {
        //Cria o produto com somente o id para reutilizar na tela de cadastro de imagens
        ProdutoModel produto = new ProdutoModel();
        produto.setId(id);

        //busca pelas imagem para encontrar a imagem que contem o nome passado como parametro para deletar do banco.
        for (ImagemModel imagem : imagens) {
            if (imagem.getNomeImagem().equals(nome)) {
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

    /**
     * Metodo para finalizado o processo de alterar/cadastrar as imagens do produto
     * @return tela de lista de produtos.
     */
    @GetMapping("/salvar")
    public String saveImagem() {
        return "redirect:/produtos/lista-produto";
    }

}
