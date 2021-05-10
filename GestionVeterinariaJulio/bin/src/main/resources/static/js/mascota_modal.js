function eliminarMascota(id,nombre) {
	swal({
  		title: "¿Desea eliminar esta mascota?",
  		icon: "warning",
  		buttons: ['No','Si'], //personalizamos los botones
  		successMode: true,
	})
	.then((Si)=>{
  		if (Si) {
			$.ajax({
				url:"/mascotas/eliminarMascota/"+id,
				success: function(mascota) {
					console.log(mascota)
				},
			});
    		swal("Mascota "+nombre+" eliminada correctamente", {
      		icon: "success",
    	}).then((si)=>{
			if(si) {
				location.href="/mascotas/listadoMascotas";
			}
		});
  		}
		else {
    		swal("No se ha eliminado a "+nombre);
  		}
	});
}
