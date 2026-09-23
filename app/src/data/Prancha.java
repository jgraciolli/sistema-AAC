package data;

public class Prancha{
    public String nome;

    public Prancha(String nome){
        this.nome = nome;
    };

    public Prancha empty(){
        return new Prancha(
            nome = ""
        );
    }
}