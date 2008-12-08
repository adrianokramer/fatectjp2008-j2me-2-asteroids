<?php
class DbSql
{   

	var $DBName;
	var $DBUser;
	var $DBPassword;
	var $DBHost;
	//Variável de conexão
	var $CONN;

   function DbSql()
   {
	  include "conf.inc.php";
	  
	  $this->DBName = $bd;
	  $this->DBUser = $user;
	  $this->DBPassword = $pass; 
	  $this->DBHost = $ender;
      /*
	  print "<script>alert(\"Dados de Conexão: $bd | $user | $pass | $ender\");</script>";
	  */
	  //Variáveis podem ser colocadas em arquivo .inc.php
      $conn=mysql_connect($this->DBHost,$this->DBUser,$this->DBPassword);
      
	  if (!$conn)
        die("<h2>Erro de Conex&atilde;o.</h2>");
      mysql_select_db($this->DBName,$conn); //Selecionando banco de dados
      $this->CONN = $conn;
      return true;
   }
   
   function ler_registros($sql) { 
   	  $conn = $this->CONN;  	
	  $sgl = mysql_query($sql, $conn); // codigo sgl que seleciona a tabela com os dados
	  $count = 1;   	
	   while($dados = mysql_fetch_array($sgl))	{
		   echo "#";
		   echo $count;
		   echo" | ";
		   echo $dados['nome'];
		   echo" | ";
		   echo $dados['pontos'];
		   echo" | ";
		   echo $dados['tempo']."\n";
		   $count++;
	   }
	   mysql_close($conn);   	
   }

   function select($sql="")
   {
      if (empty($sql)) return false;
      if (empty($this->CONN)) return false;
      $conn = $this->CONN;
      $results = mysql_query($sql,$conn);
      if ((!$results) or (empty($results)))
      {
         return false;
      }
      $count = 0;
      $data = array();
      while ($row = mysql_fetch_array($results)) {
         $data[$count] = $row;
         $count++;
      }
      mysql_free_result($results);
      return $data;
   }


   function insert($sql="")
   {
      if (empty($sql)) return false;
      if (empty($this->CONN)) return false;

      $conn = $this->CONN;
      $results = mysql_query($sql,$conn);
      if (!$results) return false;
      $results = mysql_insert_id();
      return $results;
   }


   function update($sql="")
   {
      if(empty($sql)) return false;
      if(empty($this->CONN)) return false;

      $conn = $this->CONN;
      $result = mysql_query($sql,$conn);
      return $result;
   }


   function delete($sql="")
   {
      if(empty($sql)) return false;
      if(empty($this->CONN)) return false;

      $conn = $this->CONN;
      $result = mysql_query($sql,$conn);
      return $result;
   }

   function createtable($sql="")
   {
      if(empty($sql)) return false;
      if(empty($this->CONN)) return false;

      $conn = $this->CONN;
      $result = mysql_query($sql,$conn);
      return $result;
   }

   function droptable($sql="")
   {
      if(empty($sql)) return false;
      if(empty($this->CONN)) return false;

      $conn = $this->CONN;
      $result = mysql_query($sql,$conn);
      return $result;
   }

   function createindex($sql="")
   {
      if(empty($sql)) return false;
      if(empty($this->CONN)) return false;

      $conn = $this->CONN;
      $result = mysql_query($sql,$conn);
      return $result;
   }

   function dropindex($sql="")
   {
      if(empty($sql)) return false;
      if(empty($this->CONN)) return false;

      $conn = $this->CONN;
      $result = mysql_query($sql,$conn);
      return $result;
   }
   
} // Class DbSql

?>