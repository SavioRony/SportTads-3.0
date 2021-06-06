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
import java.util.List;


@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ImagemService imagemService;

    /**
     * Metodo para abrir a lista de produtos.
     * @return ModelAndView - tela com todos os produtos cadastro no banco.
     */
    @GetMapping("/lista-produto")
    public ModelAndView getAll() {
        return new ModelAndView("Produto/ListaProduto", "produtos", produtoService.getAll());
    }


    /**
     * Metodo usado para abrir a tela de status do produto.
     * @param idproduto
     * @return ModelAndView - Tela de inativa e ativa produto
     */
    @GetMapping("/inativa-ativar/{idproduto}")
    public ModelAndView preAlterarStatus(@PathVariable("idproduto") Integer idproduto) {
        ProdutoModel produto = produtoService.getById(idproduto);
        return new ModelAndView("Produto/InativarAtivar", "produtoObj", produto);
    }

    /**
     * Metodo usando para alterar o status do produto Ativo ou Inativo no banco de dados.
     * @param idProduto
     * @return redirect - Tela de lista de produto.
     */
    @GetMapping("/alterar-status/{idProduto}")
    public String alterarStatus(@PathVariable Integer idProduto) {
        ProdutoModel produto = this.produtoService.getById(idProduto);
        if (produto.getStatus().equals("Ativo")) {
            produto.setStatus("Inativo");
        } else {
            produto.setStatus("Ativo");
        }
        this.produtoService.save(produto);
        return "redirect:/produtos/lista-produto";
    }

    /**
     * Metodo usando para abrir a tela de cadastro de produto.
     * @return  ModelAndView - Tela de cadastro de produto.
     */
    @GetMapping("/cadastrar")
    public ModelAndView telaCadastro() {
        return new ModelAndView("Produto/CadastroProduto", "produtoObj", new ProdutoModel());
    }

    /**
     * Metodo usado para salvar um novo produto.
     * @param produto
     * @param multipartfile
     * @return ModelAndView - Tela de cadastro de imagens do produto.
     * @throws IOException
     */
    @PostMapping("/salvar")
    public ModelAndView saveProduto(@ModelAttribute(name = "produto") ProdutoModel produto,
                                    @RequestParam("arquivoImagem") MultipartFile multipartfile) throws IOException {
        //Verifica se contem arquivo de imagem setar o nome no produto
        String nomeArquivo = "NotFile";
        if (!multipartfile.isEmpty()) {
            nomeArquivo = StringUtils.cleanPath(StringUtils.cleanPath(multipartfile.getOriginalFilename()));
        }
        produto.setLogo(nomeArquivo);

        ProdutoModel produtoSalvo = produtoService.save(produto);

        if (!multipartfile.isEmpty()) {
            String uploadDiretorio = "./imagem-principal/" + produtoSalvo.getId();
            FileUploadUtil.saveFile(uploadDiretorio, multipartfile, nomeArquivo);
        }
         return new ModelAndView("Produto/Imagens", "produto", produtoSalvo);
    }

    /**
     * Metodo usando para abrir a tela de Alterar produto.
     * @param idproduto
     * @return ModelAndView - Tela de alteração de produto.
     */
    @GetMapping("/editar/{idproduto}")
    public ModelAndView preEditar(@PathVariable("idproduto") Integer idproduto) {
        return new ModelAndView("Produto/CadastroProduto", "produtoObj", produtoService.getById(idproduto));
    }

    /**
     * Metodo acessado somente pelo usuario adiministrado para realizar alteração no cadastro de produtos.
     * @param produto
     * @param multipartfile
     * @return ModelAndView - Tela de alterar imagens com a imagens que ja estão salva no produto.
     * @throws IOException
     */
    @PostMapping("/editar")
    public ModelAndView editar(@ModelAttribute(name = "produto") ProdutoModel produto,
                                       @RequestParam("arquivoImagem") MultipartFile multipartfile) throws IOException {
        ProdutoModel p = produtoService.getById(produto.getId());

        //Usando para atualizar ou manter a imagem padrão verificando se contem algum arquivo novo multipartfile
        String nomeArquivo = p.getLogo();
        if (!multipartfile.isEmpty()) {
            nomeArquivo = StringUtils.cleanPath(StringUtils.cleanPath(multipartfile.getOriginalFilename()));
        }
        produto.setLogo(nomeArquivo);
        ProdutoModel produtoSalvo = produtoService.save(produto);

        //Salvar a imagem no diretorio dentro do projeto
        if (!multipartfile.isEmpty()) {
            String uploadDiretorio = "./imagem-principal/" + produtoSalvo.getId();
            FileUploadUtil.saveFile(uploadDiretorio, multipartfile, nomeArquivo);
        }
        //Buscar imagens salvas para que seja alteradas.
        List<ImagemModel> imagens = imagemService.findByIdProduto(produtoSalvo.getId());
        ModelAndView andView = new ModelAndView("Produto/Imagens", "produto", produto);
        andView.addObject("imagens", imagens);
        return andView;
    }

    /**
     * Metodo para abrir a tela com o detalhes de produtos para o usuario cliente adicionar no carrinho.
     * @param idproduto
     * @return ModelAndView - Tela de detalhes de produtos com os dados do produto.
     */
    @GetMapping("/comprar-produto/{idproduto}")
    public ModelAndView consultaCompra(@PathVariable("idproduto") Integer idproduto) {
        ProdutoModel produto = produtoService.getById(idproduto);
        List<ImagemModel> imagens = imagemService.findByIdProduto(idproduto);
        ModelAndView andView = new ModelAndView("Produto/DetalhesProdutoCompra","produto", produto);
        andView.addObject("imagens", imagens);
        return andView;
    }


    /**
     * Metodo usando para abrir a tela de visualização de produtos pelo funcionarios
     * @param idproduto
     * @return ModelAndView - Tela VisualizarProduto e o ProdutoModel e imagens conforme passado idProduto.
     */
    @GetMapping("/consultar-produto/{idproduto}")
    public ModelAndView visualizarProduto(@PathVariable("idproduto") Integer idproduto) {
        ProdutoModel produto = produtoService.getById(idproduto);
        List<ImagemModel> imagens = imagemService.findByIdProduto(idproduto);
        ModelAndView andView = new ModelAndView("Produto/VisualizarProduto","produto", produto);
        andView.addObject("imagens", imagens);
        return andView;
    }


    /**
     * Metodo usando para lista produtos que estão com o estoque baixo de 5 unidades.
     * @return ModelAndView - Tela ListaProduto
     */
    @GetMapping("/lista-estoque-minimo")
    public ModelAndView listaPorEstoqueMinimo() {
        List<ProdutoModel> produtos = produtoService.buscaQtdeProdutos();
        return new ModelAndView("Produto/ListaProduto","produtos", produtos);
    }

    /**
     * Metodo usado para abrir a tela de alteração de quantidade de produtos acessado somente pelo estoquista
     * @param idProduto
     * @return ModelAndView - Tela AlterarQuantidade com o objeto ProdutoModel
     */
    @GetMapping("/alterar-quantidade/{idProduto}")
    public ModelAndView abrirTelaQuantidade(@PathVariable("idProduto") Integer idProduto) {
        ProdutoModel produto = produtoService.getById(idProduto);
        return new ModelAndView("Produto/AlterarQuantidade", "produto", produto);
    }


    /**
     * Metodo usando para alterar no banco a quantidade em estoque pelo usuario estoquista
     * @param idProduto
     * @param quantidade
     * @return redirect - Tela de Lista de produtos
     */
    @GetMapping("/alterar-qtde/{idProduto}/{quantidade}")
    public String alterarQtde(@PathVariable("idProduto") Integer idProduto,
                              @PathVariable("quantidade") Integer quantidade) {
        ProdutoModel produto = produtoService.getById(idProduto);
        produto.setQuantidade(quantidade);
        produtoService.save(produto);
        return "redirect:/produtos/lista-produto";
    }

}

