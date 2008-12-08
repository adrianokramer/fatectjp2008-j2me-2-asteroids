<?php

include "Record.class.php";
				
$record = new Record($id,"","","");

$result = $record->limpar_records();

		//Resultado
		if($result==0){
			echo "ERROR!";		
		}
		else{
			echo "All Records Deleted successfully.";		}

?>