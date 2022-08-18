package armazem;

import ingredientes.Ingrediente;

import java.util.TreeMap;

public class Armazem {
    private TreeMap<Ingrediente, Integer> estoque;

    public Armazem(){ this.estoque = new TreeMap<>(); }

    public TreeMap<Ingrediente, Integer> getEstoque(){
            return this.estoque;
        }

    public void setEstoque(TreeMap<Ingrediente, Integer> estoque) {
        this.estoque = estoque;
    }
    private int getQuantidade(Ingrediente ingrediente) { return this.estoque.get(ingrediente); }

    public void cadastrarIngredienteEmEstoque(Ingrediente ingrediente) {
        if(!estoque.containsValue(ingrediente)) {
            estoque.put(ingrediente, 0);
        } else throw new IllegalArgumentException("Ingrediente já cadastrado");
    }

    public void descadastrarIngredienteEmEstoque(Ingrediente ingrediente) {
        if(estoque.containsValue(ingrediente)) {
            estoque.remove(ingrediente);
        } else throw new IllegalArgumentException("Ingrediente não cadastrado");
    }

    public void adicionarQuantidadeDoIngredienteEmEstoque(Ingrediente ingrediente, int quantidade) {
         if(quantidade > 0) {
             if (estoque.containsValue(ingrediente)) {
                 estoque.put(ingrediente, getQuantidade(ingrediente) + quantidade);
             } else throw new IllegalArgumentException("Ingrediente nao existe no estoque.")
         } else throw new IllegalArgumentException("Quantidade invalida");
    }

    public void reduzirQuantidadeDoIngredienteEmEstoque(Ingrediente ingrediente, int quantidade) {
        if(estoque.containsValue(ingrediente)) {
            if (quantidade > 0 && getQuantidade(ingrediente) >= quantidade) {
                estoque.put(ingrediente, getQuantidade(ingrediente) + quantidade);
            }
        }
    }

    public int consultarQuantidadeDoIngredienteEmEstoque(Ingrediente ingrediente) {
        if(estoque.containsValue(ingrediente)) {
            return getQuantidade(ingrediente);
        } else throw new IllegalArgumentException("Ingrediente não cadastrado");
    }

}
