package br.com.sporttads.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("enderecos")
public class EnderecoController {

	@GetMapping()
	public ModelAndView formularioCadastro() {
		ModelAndView mv = new ModelAndView("endereco/formularioEndereco");
		return mv;
	}

}
