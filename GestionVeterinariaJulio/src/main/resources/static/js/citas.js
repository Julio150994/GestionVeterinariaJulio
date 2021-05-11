function realizarCita(id) {
	const $checkCita = document.querySelector("#checkCita")

	if($checkCita.checked) {			
		/* Realizamos la cita */
		$.ajax({
			url: "/realizada/"+id,
		});
	}
	else
		$checkCita.checked = true // mantenemos clickado el checkbox
}