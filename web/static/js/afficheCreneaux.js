$(document).ready(function() {
			
			
			var donnees2;
			var nbchoisi=0; // nombres de cases cochées
			
			$("#choix").append(" <span class='badge'>"+nbchoisi+"</span>");
			
			jQuery.ajax({ // 1ERE ETAPE: RECUPERATION DE DONNÉES JSON
				type:'GET', 
				dataType:'json',
				url:'GetCreneaux',
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
                                
                                
                                	
				// 2EME ETAPE: AFFICHAGE DS CRENEAUX DISPONIBLE DANS LE TABLEAU
				for(i=0; i<data.length;i++){
					var creneau=data[i].heure;  // recuperation de l'heure
					var jour= data[i].jour;  // recuperation de la date
					
					nbchoix=i;
						
					$("#"+creneau).children("."+jour).append(" <p><INPUT class='"+creneau+"et"+jour+"' type='checkbox' name='"+nbchoix+"' value='"+nbchoix+ "  /'> "+data[i].matiere +"	/	"+data[i].enseignant+"</p>"); 
					};
					
					
				// caclul du nombre de choix par case
				
					
					
				$(function() { // Quand ma page est chargée
					$('input[type="checkbox"]').click(function() { // Quand on clique sur un case à cocher
					
					var cclass=$(this).attr('class'); // class de la case cochée
					var etat=true;
					var jour=$(this).parent().parent().attr('class');
					var heur=$(this).parent().parent().parent().attr('id');
					
					var nom = $(this).attr('name');
					var b=parseInt(nom);
					if (donnees2[b].confirmation==true){
							$('.'+cclass+'').prop( 'checked', false );
							$(this).prop( 'checked', false );
							
							for( i=0; i<data.length;i++){
								if (donnees2[i].jour==jour && donnees2[i].heure==heur && donnees2[i].confirmation==true){
								donnees2[i].confirmation=false;
								nbchoisi--;
								}
								
							}
							donnees2[b].confirmation=false;
								
						}
						else{
						$('.'+cclass+'').prop( 'checked', false );
					 
						$(this).prop( 'checked', true );
						
						for( i=0; i<data.length;i++){
								if (donnees2[i].jour==jour && donnees2[i].heure==heur && donnees2[i].confirmation==true){
								donnees2[i].confirmation=false;
								nbchoisi--;
								}
								
						}
						donnees2[b].confirmation=true;
						nbchoisi++;
						}	
						
						$(" .badge").text (nbchoisi);
					
					});
				});
				
				
						
					
					
				},
				
				error:function(jqXHR,textStatus,errorThrown){}
				
				});
				
				
				
				$('.alert').hide();

				$('#valid').click(function(event) {
				
				if (nbchoisi>0){
				$('.alert-warning').hide();
				$('.alert-success').show(); // affichage de l'alert
				$('#inscription').hide();
				$('#validez').hide();
				$('legend').hide();
				
				//console.log(donnees2);
				
				
				var nbcreneau=0;  //calcul le nombre de creneau horaires;
					for(i=0; i<donnees2.length;i++){
						if(donnees2[i].heure>nbcreneau){
							nbcreneau++;
							}              	
					}
 
				
				
				for(i=0; i<donnees2.length;i++){
					var creneau=donnees2[i].heure;  // recuperation de l'heure
					var jour= donnees2[i].jour;  // recuperation de la date
					
					
					$("#"+creneau).children("."+jour).children('p').hide();
				}	
					
					
				for(i=0; i<donnees2.length;i++){
					var creneau=donnees2[i].heure;  // recuperation de l'heure
					var jour= donnees2[i].jour;  // recuperation de la date
					
					//nbchoix;
					
					if( donnees2[i].confirmation==true){
					$("#"+creneau).children("."+jour).append(" <p>"+donnees2[i].matiere +"	/	"+donnees2[i].enseignant+" <a href='#' class='moins'> <span class='glyphicon glyphicon-ok'> </span></a> </p>"); 
					}
					else{
					$("#"+creneau).children("."+jour).append(" <p>"+donnees2[i].matiere +"	/	"+donnees2[i].enseignant+" </p>"); 
					};}
				
				}
				else{
					$('.alert-warning').show();
				}
				
				});
			
			 
				
			});
				
				
				
			
			// });