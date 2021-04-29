package br.com.sporttads.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sporttads.model.EnderecoModel;
import br.com.sporttads.service.EnderecoService;

@Controller
@RequestMapping("/enderecos")
public class EnderecoController {

	@Autowired
	private EnderecoService enderecoService;

	@GetMapping("/formulario")
	public ModelAndView getFormulario() {
		return new ModelAndView("endereco/formularioEndereco", "endereco", new EnderecoModel());
	}

	@PostMapping("/save")
	public ModelAndView save(@ModelAttribute("endereco") EnderecoModel endereco, RedirectAttributes attr,
			@AuthenticationPrincipal User user) {
		this.enderecoService.create(endereco, user);
		ModelAndView mv = new ModelAndView("endereco/formularioEndereco", "endereco", new EnderecoModel());
		mv.addObject("sucesso", "Endereço cadastrado com sucesso!");
		return mv;
	}

	@GetMapping
	public ModelAndView getAllClienteId(@AuthenticationPrincipal User user) {
		List<EnderecoModel> enderecos = this.enderecoService.getByClienteId(user);
		return new ModelAndView("endereco/listaEnderecos", "enderecos", enderecos);
	}

	@GetMapping("/update/{id}")
	public ModelAndView getFormularioUpdate(@PathVariable int id) {
		EnderecoModel endereco = this.enderecoService.getById(id);
		return new ModelAndView("endereco/formularioEndereco", "endereco", endereco);
	}

	@PostMapping("/update")
	public String update(@ModelAttribute("endereco") EnderecoModel enderecoEdit, @AuthenticationPrincipal User user) {
		EnderecoModel endereco = this.enderecoService.update(enderecoEdit, user);
		ModelAndView mv = new ModelAndView("endereco/formularioEndereco", "endereco", endereco);
		mv.addObject("sucesso", "Endereço alterado com sucesso!");
		return "redirect:/enderecos";
	}

	@GetMapping("delete/{id}")
	public String delete(@PathVariable Integer id) {
		this.enderecoService.delete(id);
		ModelAndView mv = new ModelAndView("endereco/formularioEndereco");
		mv.addObject("sucesso", "Endereço excluído com sucesso!");
		return "redirect:/enderecos";
	}

}
