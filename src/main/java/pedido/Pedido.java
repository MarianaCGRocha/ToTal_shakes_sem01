package pedido;

import ingredientes.Adicional;
import produto.Shake;
import produto.TipoTamanho;

import java.util.ArrayList;

public class Pedido {

    private int id;
    private ArrayList<ItemPedido> itens;
    private Cliente cliente;

    public Pedido(int id, ArrayList<ItemPedido> itens, Cliente cliente) {
        this.id = id;
        this.itens = itens;
        this.cliente = cliente;
    }

    public ArrayList<ItemPedido> getItens() {
        return itens;
    }

    public int getId() {
        return this.id;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public double calcularTotal(Cardapio cardapio) {
        double total = 0;
        //TODO
        for (ItemPedido item : itens) {
            Shake shake = item.getShake();
            TipoTamanho tipoTamanho = shake.getTipoTamanho();
            total += cardapio.buscarPreco(shake.getBase());
            // total += cardapio.buscarPreco(shake.getFruta());
            // total += cardapio.buscarPreco(shake.getTopping());
            if (shake.getAdicionais() != null) {
                for (Adicional adicional : shake.getAdicionais()) {
                    total += cardapio.buscarPreco(adicional);
                }
            }
            total *= tipoTamanho.getMultiplicador();
            total *= item.getQuantidade();
        }
        return total;
    }

    public void adicionarItemPedido(ItemPedido itemPedidoAdicionado) {
        //TODO
        boolean itemDuplicado = false;
        ArrayList<Shake> shakesOrder = new ArrayList<Shake>();
        for (ItemPedido item : itens) {
            if (item.getShake().equals(itemPedidoAdicionado.getShake())) {
                item.setQuantidade(item.getQuantidade() + itemPedidoAdicionado.getQuantidade());
                itemDuplicado = true;
            }
        }
        if (!itemDuplicado) {
            itens.add(itemPedidoAdicionado);
        }
    }

    public void removeItemPedido(ItemPedido itemPedidoRemovido) {
        //substitua o true por uma condição
        if (itens.contains(itemPedidoRemovido.getQuantidade() > 1)) {
            itens.remove(itemPedidoRemovido.getQuantidade());
            //TODO
        } else if (itens.contains(itemPedidoRemovido.getQuantidade() == 1)) {
            itens.remove(itemPedidoRemovido);
        } else {
            throw new IllegalArgumentException("Item nao existe no pedido.");
        }
    }

    @Override
    public String toString() {
        return this.itens + " - " + this.cliente;
    }
}
