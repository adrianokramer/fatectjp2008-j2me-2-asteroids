package br.com.fatecjp.core;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.sourceforge.floggy.persistence.ObjectSet;
import net.sourceforge.floggy.persistence.Persistable;
import net.sourceforge.floggy.persistence.PersistableManager;

/**
 *
 * @author Adriano Kramer / Daniel Viana
 */

public class Recorde implements Persistable{
    
    public int id;
    public String nome;
    public int pontos;
    public int tempo;
    //public String senha;
    
    public Recorde(int id, String nome, int pontos, int tempo) {

        this.id = id;
        this.nome = nome;
        this.pontos = pontos;
        this.tempo = tempo;

    }
    
    public Recorde() {}
	
    //Getters and Setters
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getPontos() {
		return pontos;
	}
	
	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	
	public int getTempo() {
		return tempo;
	}
	
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	
    public int salvarRecorde() {
    	int res = 0;

        this.id = novoId();
        res = this.id;
        
        System.out.println("Salvando recorde... Id = "+this.id);
        
        try {
            // Salva a instância do objeto criado
            PersistableManager.getInstance().save(this);
        } catch (Exception e) {
            System.out.println("Erro no processo!\nDescrição: ");
            e.printStackTrace();
        	res = 0;
        }
        
        return res; //Retorno

    } //salvarRecorde
       
    public Recorde[] listarRecordes() {
    	System.out.println("Listando recordes...");
        Recorde[] itens = null; //Coleção de objetos Recorde
        ObjectSet recorde = null;
        try {
            recorde = PersistableManager.getInstance().find(Recorde.class, null, null);
            itens = new Recorde[recorde.size()];
            System.out.println("Adicionando objetos...");
            for (int i = 0; i < recorde.size(); i++) {
                Recorde obj = (Recorde) recorde.get(i);
                itens[i] = obj;
            }
        } catch (Exception e) {
            System.out.println("Erro no processo de listagem dos recordes!");
        }
        return itens;
    }
    
    public int novoId() {
        int newId = 0;
        
        try {
            ObjectSet recorde = PersistableManager.getInstance().find(Recorde.class, null, null);
            for (int i = 0; i < recorde.size(); i++) {
                Recorde obj = (Recorde) recorde.get(i);
                newId = obj.getId();
            //...
            }
        } catch (Exception e) {
            System.out.println("Erro no processo de listagem dos recordes!");
        }
        return newId+1;
    }

    public int apagarRecorde(int id) {
        try {
            // Carregando objeto a partir do id
            PersistableManager.getInstance().load(this, id);

            // Apagando o objeto.
            PersistableManager.getInstance().delete(this);
        } catch (Exception e) {
            System.out.println("Impossível excluir!");
            //e.printStackTrace();
            return 0; //Fracasso
        }
        return 1;
    } //apagarRecorde
    
    public void apagarTodos(){
        try {
        // Delete all objetcs.
        PersistableManager.getInstance().deleteAll();
        } catch (Exception e) {
        System.out.println("Erro no Processo!");
        }
    }
    
	public boolean existeRecorde(int id) {
	        boolean existe = false;
	        try {
	            // Carregando um objeto a partir de seu id
	            PersistableManager.getInstance().load(this, id);
	
	            //Se existir retorna verdadeiro
	            existe = PersistableManager.getInstance().isPersisted(this);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return existe;
	    }    
	
	public byte[] retBytes(){
	    byte[] dados = null;
	       ByteArrayOutputStream baos = new ByteArrayOutputStream();
	       DataOutputStream dos = new DataOutputStream(baos);
	       try {
	           dos.writeInt(this.id);
	           dos.writeChars(this.nome);
	           dos.writeInt(this.pontos);
	           dos.writeInt(this.tempo);
	           dados = baos.toByteArray();
	       } catch (Exception e) {
	           e.printStackTrace();
	       } finally {
	           try {
	               dos.close();
	           } catch (Exception e) {
	               e.printStackTrace();
	           }
	       }
	       return dados;
	}
	
	public void exibeRanking(){
	    //Lista Recordes
	}

}//Recorde