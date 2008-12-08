<?php
################################################################################
## @author: Daniel Viana & Adriano Kramer                                     ##
## @date:   26/11/2008                                                        ##
## @update: 06/12/2008                                                       ##
## @descri��o: Classe para manipula��o de objetos do tipo Record.             ##
################################################################################
    
    include_once("DbSql.class.php");
	
    // Cria a classe Usuario herdando as propriedades e m�todos da classe DbSql
	class Record extends DbSql
	{	
		var $id; 			// Id do recorde
		var $nome;   		// Nome do Jogador
		var $pontos;   		// Pontua��o
		var $tempo;			// Tempo
		var $db;			// Vari�vel de conex�o com o BD		
		
	//Implementar m�todos GET e SET (Orienta��o do acesso aos atributos de classe)
	    
		##############################
        ### Construtores da classe ###
   		##############################
		function Record($id,$nome,$pontos,$tempo)
		{	
			$this->db = new DbSql();
			$this->id = $id;
			$this->nome = $nome;
			$this->pontos = $pontos;
			$this->tempo = $tempo;
		}
		
	
		##################################
        ### M�todo para inserir Record ###
        ##################################
        function inserir_record()	
        {
			//Teste se o registro existe
			$sql = "Select count(*) from record where nome like '$this->nome' and pontos = '$this->pontos'"
			." and tempo = '$this->tempo'";
			
			$existe = $this->db->select($sql);
			
			if($existe[0][0]==0){
				$sql = "INSERT INTO record(nome, pontos, tempo)
				VALUES('$this->nome', '$this->pontos', '$this->tempo')";
				if($id=$this->db->insert($sql)){
					return $id;
				}
				else {
					return 0;
				}
			}			
			return 0;	
        } // inserir_record()
        
            
	    ###################################
        ### M�todos para ler os records ###
        ###################################      

		function getRecords($limit = NULL) {			
			if($limit != NULL) {
				$query_quant = " LIMIT $limit";
			}
			
			if($limit == NULL) {
				$query_quant = "";
			}
		
			$query = "
				SELECT 
					nome, 
					pontos, 
					tempo
				FROM record
				ORDER BY
						pontos desc,
						tempo,
						nome
				$query_quant					
			";
			
			$this->db->ler_registros($query);
		}  			

       
	    ##################################
        ### M�todo para excluir record ###
        ##################################
   	
        function excluir_record() //Usar o construtor apenas com o 1� atributo - logo
        {
            if($this->db->delete("DELETE FROM record WHERE id=$this->id"))
       			return 1;
			else 
				return 0;
		} // excluir_record()
		
		function limpar_records() // Limpa todos os records
		{
            if($this->db->delete("DELETE FROM record"))
       			return 1;
			else 
				return 0;
		} // limpar_records()
		
		
} // Class Record
?>