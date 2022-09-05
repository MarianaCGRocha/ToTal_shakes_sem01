package armazem;

import ingredientes.Ingrediente;

import java.util.TreeMap;

public class Armazem {
    public TreeMap<Ingrediente, Integer> estoque;

    public Armazem(){ this.estoque = new TreeMap<>(); }

    public TreeMap<Ingrediente, Integer> getEstoque(){
        return this.estoque;
    }

    public void setEstoque(TreeMap<Ingrediente, Integer> estoque) {
        this.estoque = estoque;
    }
    private int getQuantidade(Ingrediente ingrediente) { return this.estoque.get(ingrediente); }

    public void cadastrarIngredienteEmEstoque(Ingrediente ingrediente) {
        if(!estoque.containsKey(ingrediente)) {
            estoque.put(ingrediente, 0);
        } else throw new IllegalArgumentException("Ingrediente já cadastrado.");
    }

    public void descadastrarIngredienteEmEstoque(Ingrediente ingrediente) {
        if(estoque.containsKey(ingrediente)) {
            estoque.remove(ingrediente);
        } else throw new IllegalArgumentException("Ingrediente não encontrado.");
    }

    public void adicionarQuantidadeDoIngredienteEmEstoque(Ingrediente ingrediente, int quantidade) {
         if(quantidade > 0) {
             if (estoque.containsKey(ingrediente)) {
                 estoque.put(ingrediente, getQuantidade(ingrediente) + quantidade);
             } else throw new IllegalArgumentException("Ingrediente não encontrado.");
         } else throw new IllegalArgumentException("Quantidade inválida.");
    }

    public void reduzirQuantidadeDoIngredienteEmEstoque(Ingrediente ingrediente, int quantidade) {
        if(estoque.containsKey(ingrediente)) {
            if (quantidade > 0 && getQuantidade(ingrediente) >= quantidade) {
                estoque.put(ingrediente, getQuantidade(ingrediente) - quantidade);
            } else if (quantidade >= getQuantidade(ingrediente)) { estoque.remove(ingrediente); }
            else throw new IllegalArgumentException("Quantidade inválida.");
        } else throw new IllegalArgumentException("Ingrediente não encontrado.");
    }

    public int consultarQuantidadeDoIngredienteEmEstoque(Ingrediente ingrediente) {
        if(estoque.containsKey(ingrediente)) {
            return getQuantidade(ingrediente);
        } else throw new IllegalArgumentException("Ingrediente não cadastrado.");
    }

}
