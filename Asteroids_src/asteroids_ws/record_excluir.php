<?php

include "Record.class.php";

//Recebendo os valores do form
$idadm = $_GET['id'];
				
$record = new Record($id,"","","");

$result = $record->excluir_record();

		//Resultado
		if($result==0){
			//print "<script>alert(\"ERROR!\")</script>";
			echo "ERROR!";		
		}
		else{
			echo "Record Deleted successfully.";
			//print "<script>alert(\"High Score saved successfully!\")</script>";			
		}

?>