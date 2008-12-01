<?php
include "Record.class.php";

//Recebendo os valores do form
$idadm = $_GET['id'];
				
$record = new Record($id,"","","");

$result = $record->excluir_record();

		//Resultado
		if($result==0){
			print "<script>alert(\"ERRO NO PROCESSO!\")</script>";		
		}
		else{
			print "<script>alert(\"PROCESSO REALIZADO COM ÊXITO!\")</script>";			
		}

?>