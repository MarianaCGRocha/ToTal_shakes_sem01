package armazem;

import ingredientes.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArmazemTest {
    private Armazem armazem;

    @BeforeEach
    void setup() {
        this.armazem = new Armazem();
    }

    @Test
    void testCadastrarIngredienteEmEstoque_properly() {
        Ingrediente ingredienteTest = new Fruta(TipoFruta.Morango);
        armazem.cadastrarIngredienteEmEstoque(ingredienteTest);
        assertTrue(armazem.getEstoque().containsKey(ingredienteTest));
        assertEquals(0, armazem.consultarQuantidadeDoIngredienteEmEstoque(ingredienteTest));
    }

    @Test
    void testCadastrarIngredienteEmEstoque_exception_ingredienteNãoCadastrado() {
        Ingrediente ingredienteTest = new Fruta(TipoFruta.Morango);
        Ingrediente ingredienteDistinto = new Fruta(TipoFruta.Banana);
        try {
            armazem.cadastrarIngredienteEmEstoque(ingredienteTest);
            armazem.consultarQuantidadeDoIngredienteEmEstoque(ingredienteDistinto);
        } catch(Throwable e) {
            assertEquals("Ingrediente não cadastrado.", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    void testCadastrarIngredienteEmEstoque_exception_previamenteCadastrado() {
        Ingrediente ingredienteTest = new Fruta(TipoFruta.Morango);
        Ingrediente ingredienteTest2 = new Fruta(TipoFruta.Morango);
        try {
            armazem.cadastrarIngredienteEmEstoque(ingredienteTest);
            armazem.cadastrarIngredienteEmEstoque(ingredienteTest2);
        } catch(Throwable e) {
            assertEquals("Ingrediente já cadastrado.", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    void testDescadastrarIngredienteEmEstoque_properly() {
        Ingrediente ingredienteTest = new Fruta(TipoFruta.Morango);
        armazem.cadastrarIngredienteEmEstoque(ingredienteTest);
        armazem.descadastrarIngredienteEmEstoque(ingredienteTest);
        assertFalse(armazem.getEstoque().containsKey(ingredienteTest));
    }

    @Test
    void testDescadastrarIngredienteEmEstoque_exception_buscarIngredienteDescadastrado() {
        Ingrediente ingredienteTest = new Fruta(TipoFruta.Morango);
        try {
            armazem.cadastrarIngredienteEmEstoque(ingredienteTest);
            armazem.descadastrarIngredienteEmEstoque(ingredienteTest);
            armazem.consultarQuantidadeDoIngredienteEmEstoque(ingredienteTest);
        } catch(Throwable e) {
            assertEquals("Ingrediente não cadastrado.", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    void testDescadastrarIngredienteEmEstoque_exception_descadastrarIngredienteInexistente() {
        Ingrediente ingredienteTest = new Fruta(TipoFruta.Morango);
        Ingrediente ingredienteDistinto = new Fruta(TipoFruta.Banana);
        try {
            armazem.cadastrarIngredienteEmEstoque(ingredienteTest);
            armazem.descadastrarIngredienteEmEstoque(ingredienteDistinto);
        } catch(Throwable e) {
            assertEquals("Ingrediente não encontrado.", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    void testAdicionarQuantidadeDoIngredienteEmEstoque_properly() {
        Ingrediente ingredienteTest = new Fruta(TipoFruta.Morango);
        armazem.cadastrarIngredienteEmEstoque(ingredienteTest);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingredienteTest, 9);
        assertTrue(armazem.getEstoque().containsKey(ingredienteTest));
        assertEquals(9, armazem.consultarQuantidadeDoIngredienteEmEstoque(ingredienteTest));
    }

    void testAdicionarQuantidadeDoIngredienteEmEstoque_exception_quantidadeInvalida() {
        Ingrediente ingredienteTest = new Fruta(TipoFruta.Morango);
        try {
            armazem.cadastrarIngredienteEmEstoque(ingredienteTest);
            armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingredienteTest, -10);
        } catch (Throwable e) {
            assertEquals("Quantidade inválida.", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    void testAdicionarQuantidadeDoIngredienteEmEstoque_exception_ingredienteInexistente() {
        Ingrediente ingredienteTest = new Fruta(TipoFruta.Morango);
        Ingrediente ingredienteDistinto = new Fruta(TipoFruta.Banana);
        try {
            armazem.cadastrarIngredienteEmEstoque(ingredienteTest);
            armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingredienteDistinto, 10);
        } catch (Throwable e) {
            assertEquals("Quantidade Ingrediente não encontrado.", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    void testReduzirQuantidadeDoIngredienteEmEstoque_properly() {
        Ingrediente ingredienteTest = new Fruta(TipoFruta.Morango);
        armazem.cadastrarIngredienteEmEstoque(ingredienteTest);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingredienteTest, 8);
        armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingredienteTest, 5);
        assertTrue(armazem.getEstoque().containsKey(ingredienteTest));
        assertEquals(3, armazem.consultarQuantidadeDoIngredienteEmEstoque(ingredienteTest));
    }

    @Test
    void testReduzirQuantidadeDoIngredienteEmEstoque_exception_quantidadeInvalida_superiorADisponivel() {
        Ingrediente ingredienteTest = new Fruta(TipoFruta.Morango);
        armazem.cadastrarIngredienteEmEstoque(ingredienteTest);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingredienteTest, 10);
        armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingredienteTest, 15);
        assertFalse(armazem.getEstoque().containsKey(ingredienteTest));
    }

    @Test
    void testReduzirQuantidadeDoIngredienteEmEstoque_exception_quantidadeInvalida_menorQueZero() {
        Ingrediente ingredienteTest = new Fruta(TipoFruta.Morango);
        try {
            armazem.cadastrarIngredienteEmEstoque(ingredienteTest);
            armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingredienteTest, 10);
            armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingredienteTest, -5);
        } catch (Throwable e) {
            assertEquals("Quantidade inválida.", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    void testReduzirQuantidadeDoIngredienteEmEstoque_exception_ingredienteNaoEncontrado() {
        Ingrediente ingredienteTest = new Fruta(TipoFruta.Morango);
        Ingrediente ingredienteDistinto = new Fruta(TipoFruta.Banana);
        try {
            armazem.cadastrarIngredienteEmEstoque(ingredienteTest);
            armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingredienteTest, 10);
            armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingredienteDistinto, 15);
        } catch (Throwable e) {
            assertEquals("Ingrediente não encontrado.", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    void testConsultarQuantidadeDoIngredienteEmEstoque_properly() {
        Ingrediente ingredienteTest = new Fruta(TipoFruta.Morango);
        armazem.cadastrarIngredienteEmEstoque(ingredienteTest);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingredienteTest, 8);
        armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingredienteTest, 2);
        assertTrue(armazem.getEstoque().containsKey(ingredienteTest));
        assertEquals(6, armazem.consultarQuantidadeDoIngredienteEmEstoque(ingredienteTest));
    }

    @Test
    void testConsultarQuantidadeDoIngredienteEmEstoque_exception_ingredienteNaoCadastrado() {
        Ingrediente ingredienteTest = new Fruta(TipoFruta.Morango);
        Ingrediente ingredienteDistinto = new Fruta(TipoFruta.Banana);
        try {
            armazem.cadastrarIngredienteEmEstoque(ingredienteTest);
            armazem.consultarQuantidadeDoIngredienteEmEstoque(ingredienteTest);
        } catch (Throwable e) {
            assertEquals("Ingrediente não cadastrado.", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }
}