<?php
################################################################################
## @author: Daniel Viana                                                      ##
## @date:   26/11/2008                                                        ##
## @update: --/--/----                                                        ##
## @descriчуo: Classe para manipulaчуo de objetos do tipo Record.             ##
################################################################################
    
    include_once("DbSql.class.php");
	
    // Cria a classe Usuario herdando as propriedades e mщtodos da classe DbSql
	class Record extends DbSql
	{	
		var $id; 			// Id do recorde
		var $nome;   		// Nome do Jogador
		var $pontos;   		// Pontuaчуo
		var $tempo;			// Tempo
		var $db;			// Variсvel de conexуo com o BD		
		
	//Implementar mщtodos GET e SET (Orientaчуo do acesso aos atributos de classe)
	    
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
        ### Mщtodo para inserir Record ###
        ##################################
        function inserir_record()	
        {
			$sql = "INSERT INTO record(nome, pontos, tempo)
			VALUES('$this->nome', '$this->pontos', '$this->tempo')";
			if($id=$this->db->insert($sql)){
				return $id;
			}
			else {
				return 0;
			}	
        } // inserir_record()
            
       
	    ##################################
        ### Mщtodo para excluir record ###
        ##################################
   	
        function excluir_record() //Usar o construtor apenas com o 1К atributo - logo
        {
            if($this->db->delete("DELETE FROM record WHERE id=$this->id"))
       			return 1;
			else 
				return 0;
		} // excluir_record()
		
		
} // Class Record
?>