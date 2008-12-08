<?
include "Record.class.php";

		$nome   = $_GET['nome'];
   		$pontos = $_GET['pontos'];
    	$tempo  = $_GET['tempo'];	

		$record = new Record("",$nome,$pontos,$tempo);
		
    	$result = $record->inserir_record();
		
		//Resultado
		if($result==0){
			echo "ERROR!";
			//print "<script>alert(\"ERRO NO PROCESSO!\")</script>";		
		}
		else{
			echo "High Score saved successfully!";
			//print "<script>alert(\"PROCESSO REALIZADO COM ÊXITO!\")</script>";			
		}			        
		
?>