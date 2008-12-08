<?
	include "Record.class.php";	

	$limit = 10; //Quantidade Máxima de Records Exibidos

	$record = new Record("",$nome,$pontos,$tempo);
	
	echo "RANKING | NAME | POINTS | TIME \n";
	echo "__________________________ \n\n";
	$record->getRecords($limit);		        
		
?>