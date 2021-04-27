package br.com.sporttads.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.sporttads.model.EnderecoModel;
import br.com.sporttads.service.EnderecoService;

@Controller
@RequestMapping("/enderecos")
public class EnderecoController {

	@Autowired
	private EnderecoService enderecoService;

//	@GetMapping()
//	public ModelAndView formularioCadastro() {
//		ModelAndView mv = new ModelAndView("endereco/formularioEndereco");
//		EnderecoModel endereco = new EnderecoModel("Casa Hugo", "04833001", "5351", "Avenida Senador Teotonio Vilela",
//				"São Paulo", "São José", "SP", "Bloco 6 Apto 03");
//		mv.addObject("endereco", endereco);
//		return mv;
//	}

	@GetMapping("/formulario")
	public ModelAndView getFormulario() {
		ModelAndView mv = new ModelAndView("endereco/formularioEndereco");
		return mv;
	}

	@PostMapping("/save")
	public ModelAndView save(EnderecoModel endereco) {
		this.enderecoService.create(endereco);
		ModelAndView mv = new ModelAndView("endereco/formularioEndereco");
		return mv;
	}

	@GetMapping("/{idCliente}")
	public List<EnderecoModel> getAllClienteId(@PathVariable Long idCliente) {
		return this.enderecoService.getByClienteId(idCliente);
	}

	@PostMapping("update/{id}")
	public ModelAndView update(@PathVariable int id, @RequestBody EnderecoModel enderecoUpdate) {
		ModelAndView mv = new ModelAndView("endereco/formularioEndereco");
		EnderecoModel endereco = this.enderecoService.getById(id);
		mv.addObject("endereco", endereco);
		return mv;
	}

}
