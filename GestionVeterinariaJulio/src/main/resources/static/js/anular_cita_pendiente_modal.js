function anularCitaPendiente(id) {
	swal({
  		title: "¿Desea anular esta cita?",
  		icon: "warning",
  		buttons: ['No','Si'], //personalizamos los botones
  		successMode: true,
	})
	.then((Si)=>{
  		if (Si) {
			$.ajax({
				url:"/citas/anularCita/"+id,
				success: function(cita) {
					console.log(cita)
				},
			});
    		swal("Cita anulada correctamente", {
      		icon: "success",
    	}).then((si)=>{
			if(si) {
				location.href="/citas/citasPendientes";
			}
		});
  		}
		else {
    		swal("No has anulado la cita ");
  		}
	});
}
