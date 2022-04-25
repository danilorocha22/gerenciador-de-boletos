package com.danilo.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danilo.cobranca.model.Boleto;
import com.danilo.cobranca.model.BoletoView;
import com.danilo.cobranca.model.Status;
import com.danilo.cobranca.repository.BoletoRepository;
import com.danilo.cobranca.service.BoletoService;

@Controller
@RequestMapping("boletos")
public class BoletoController {

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private BoletoRepository boletoRepository;

	@GetMapping
	public ModelAndView boletos(ModelMap modelMap) {
		modelMap.addAttribute("boletos", boletoRepository.findAll());
		return new ModelAndView("PesquisaBoleto", modelMap);
	}// listarBoletos

	@GetMapping("/cadastro")
	public ModelAndView cadastrar(Boleto boleto) {
		ModelAndView modelView = new ModelAndView("CadastroBoleto");
		modelView.addObject("boleto", boleto);
		return modelView;
	}// cadastro

	@PostMapping("/salvar")
	public ModelAndView salvarOuAtualizar(@Validated Boleto boleto, BindingResult result, RedirectAttributes attributes,
			ModelMap modelMap) {
		if (result.hasErrors()) {
			modelMap.addAttribute(result);
			modelMap.addAttribute("boleto", boleto);
			return new ModelAndView("CadastroBoleto", modelMap);
		}

		if (boleto.getId() == null) {
			try {
				boletoService.salvar(boleto);
				attributes.addFlashAttribute("mensagem", "Boleto salvo com sucesso!");
				System.out.println("ENTROU AQUI = 2");
				return new ModelAndView("redirect:/boletos/cadastro");
			} catch (IllegalArgumentException e) {
				result.rejectValue("dataVencimento", null, e.getMessage());
				return cadastrar(boleto);
			}
		} else {
			try {
				boletoRepository.update(boleto);
				attributes.addFlashAttribute("mensagem", "Boleto atualizado com sucesso!");
				return new ModelAndView("redirect:/boletos/cadastro");
			} catch (IllegalArgumentException e) {
				result.rejectValue("dataVencimento", null, e.getMessage());
				return cadastrar(boleto);
			}
		}

	}// salvar

	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id, ModelMap modelMap) {
		BoletoView boletoView = boletoService.boletoView(boletoService.findById(id));
		modelMap.addAttribute("boleto", boletoView);
		return new ModelAndView("CadastroBoleto", modelMap);
	}// editar

	@DeleteMapping("{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attributes) {
		boletoService.excluir(id);
		attributes.addFlashAttribute("mensagem", "Boleto excluído com sucesso!");
		return "redirect:/boletos";
	}// excluir

	// @ResponseBody retorna diretamente uma String e não uma View
	@PutMapping("/{id}/pagar")
	public @ResponseBody String pagar(@PathVariable Long id) {
		return boletoService.pagar(id);
	}// pagarBoleto

	@GetMapping("/pesquisar")
	public ModelAndView pesquisar(@RequestParam String descricao, ModelMap modelMap) {
		modelMap.addAttribute("boletos", boletoService.pesquisar(descricao));
		return new ModelAndView("PesquisaBoleto", modelMap);
	}// pesquisarBoleto

	@ModelAttribute("todosStatus")
	public List<Status> statusList() {
		return Arrays.asList(Status.values());
	}

}
