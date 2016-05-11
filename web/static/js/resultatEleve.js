$(document).ready(function() {
			
			
			var donnees2;
			
			jQuery.ajax({ // Recuperation de Données
				type:'GET', 
				dataType:'json',
				url:'resultats.js',
				success:function (data,textStatus,jqXHR){
				console.log(data);
				donnees2= data;
				
					var nbcreneau=0;  //calcul le nombre de creneau horaires;
					for(i=0; i<data.length;i++){
						if(data[i].heure>nbcreneau){
							nbcreneau++;
							}              	
					}
				var nbchoix;		
				for(i=0; i<data.length;i++){
					var creneau=data[i].heure;  // recuperation de l'heure
					var jour= data[i].Jour;  // recuperation de la date
					
					nbchoix=i;
					
					if ((data[i].acccordProf==1) && (data[i].confirmation==true)){
					
						$("#"+creneau).children("."+jour).append(" <p>"+data[i].NomUE +"	/	"+data[i].nomProf+" </p>"); 
					};
				}
						
				
				
				
						
					
					
				},
				
				error:function(jqXHR,textStatus,errorThrown){}
				
				});
				
				
				
				$('.alert').hide();
			
			 
				
			});
				
				
				
			
			// });