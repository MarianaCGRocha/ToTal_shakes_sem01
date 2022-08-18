package pedido;

import ingredientes.Adicional;
import produto.Shake;
import produto.TipoTamanho;

import java.util.ArrayList;

public class Pedido{

    private int id;
    private  ArrayList<ItemPedido> itens;
    private Cliente cliente;

    public Pedido(int id, ArrayList<ItemPedido> itens,Cliente cliente){
        this.id = id;
        this.itens=itens;
        this.cliente=cliente;
    }

    public ArrayList<ItemPedido> getItens() {
        return itens;
    }

    public int getId(){
        return this.id;
    }

    public Cliente getCliente(){
        return this.cliente;
    }

    public double calcularTotal(Cardapio cardapio) {
        double total= 0;
        //TODO
        for(ItemPedido item : itens) {
            double valorShake = 0;
            Shake shake = item.getShake();
            // TipoTamanho tipoTamanho = shake.getTipoTamanho();
            valorShake += (cardapio.buscarPreco((shake.getBase())) * (shake.getTipoTamanho().getMultiplicador()));
            // total += cardapio.buscarPreco(shake.getFruta());
            // total += cardapio.buscarPreco(shake.getTopping());
            if(shake.getAdicionais()!= null) {
                for (Adicional adicional : shake.getAdicionais()) {
                    valorShake += cardapio.buscarPreco(adicional);
                }
            }
            // total *= tipoTamanho.getMultiplicador();
            valorShake *= item.getQuantidade();
            total += valorShake;
        }
        return total;
    }

    public void adicionarItemPedido(ItemPedido itemPedidoAdicionado){
        //TODO
        boolean duplicatedItem = false;
        for (ItemPedido item : itens ) {
            if (item.getShake().equals(itemPedidoAdicionado.getShake())) {
                item.setQuantidade(item.getQuantidade() + itemPedidoAdicionado.getQuantidade());
                duplicatedItem = true;
                break;
            }
        }
        if(!duplicatedItem) {
            itens.add(itemPedidoAdicionado);
        }
    }

    public void removeItemPedido(ItemPedido itemPedidoRemovido){
        //substitua o true por uma condição
        if(itens.stream().anyMatch(item -> item.getShake().toString().equals(itemPedidoRemovido.getShake().toString()))) {
            for (ItemPedido item : itens ) {
               if (item.getShake().toString().equals(itemPedidoRemovido.getShake().toString()) && item.getQuantidade() > 0) {
                   item.setQuantidade(item.getQuantidade() - 1);
               }
            }
            itens.removeIf(item -> item.getQuantidade() <= 0);
        } else throw new IllegalArgumentException("Item nao existe no pedido.");
    }

    @Override
    public String toString() {
        return this.itens + " - " + this.cliente;
    }
}
