//clicou no modal ele vai pegar pelo id
$('#modalExcluir').on('show.bs.modal', function(event) {
	let btn = $(event.relatedTarget);

	let idBoleto = btn.data('id');
	let descricaoBoleto = btn.data('descricao');

	let modal = $(this);
	let form = modal.find('form');
	let action = form.data('url-base');
	
	if(!action.endsWith('/'))
		action += '/';	
			

	form.attr('action', action + idBoleto);

	modal.find('.modal-body p').html('Você tem certeza que deseja excluir o boleto <strong>' +
				descricaoBoleto + '</strong>?');
});

//Carregou a página ele vai encontrar os elementos abaixo pelas classes
$(()=> {
	//Crirar os tooltips
	/*$('[data-bs-toggle="tooltip"]').tooltip();*/
	$('[rel="tooltip"]').tooltip();
	
	//Formatar os valores
	$('.js-currency').maskMoney({decimal : ',', thousands: '.', allowZero: true});
	
	//Trocar o status de pendente para quitado usando Ajax
	$('.js-atualizar-status').on('click', (event)=>{
		event.preventDefault();
		
		let btnPagar = $(event.currentTarget);
		let urlPagar = btnPagar.attr('href');
		
		let response = $.ajax({
			url: urlPagar,
			type: 'PUT'
		});
		
		response.done((e)=> {
			let idBoleto = btnPagar.data('id');
			$('[data-role=' + idBoleto + ']').html('<span class="badge bg-success">' + e + ' </span>');
			btnPagar.hide();
		});
		
		response.fail((e)=> {
			console.log(e);
			alert("Erro ao tentar pagar boleto.")
		});
		
	});
});