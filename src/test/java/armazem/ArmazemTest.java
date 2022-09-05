package armazem;

import ingredientes.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArmazemTest {
    private Armazem armazem;
    Ingrediente ingredienteMorango;
    Ingrediente ingredienteBanana;
    Ingrediente ingredienteAbacate;

    @BeforeAll
    void setup() {
        this.armazem = new Armazem();
        ingredienteMorango = new Fruta(TipoFruta.Morango);
        ingredienteBanana = new Fruta(TipoFruta.Banana);
        ingredienteAbacate = new Fruta(TipoFruta.Abacate);
    }

    @BeforeEach
    void init() {
        armazem.estoque.put(ingredienteAbacate, 0);
        armazem.estoque.put(ingredienteBanana, 8);
    }

    @AfterEach
    void tearDown() {
        if(armazem.estoque.size() > 0);
        armazem.estoque.clear();
    }

    @DisplayName("Cadastro de ingrediente - Success")
    @Test
    void testCadastrarIngredienteEmEstoque_properly() {
        armazem.cadastrarIngredienteEmEstoque(ingredienteMorango);
        assertTrue(armazem.getEstoque().containsKey(ingredienteMorango));
        assertEquals(0, armazem.consultarQuantidadeDoIngredienteEmEstoque(ingredienteMorango));
    }

    @DisplayName("Cadastro de ingrediente - Exception: Ingrediente não cadastrado")
    @Test
    void testCadastrarIngredienteEmEstoque_exception_ingredienteNãoCadastrado() {
        try {
            armazem.cadastrarIngredienteEmEstoque(ingredienteMorango);
            armazem.consultarQuantidadeDoIngredienteEmEstoque(ingredienteMorango);
        } catch(Throwable e) {
            assertEquals("Ingrediente não cadastrado.", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @DisplayName("Cadastro de ingrediente - Exception: Ingrediente já cadastrado")
    @Test
    void testCadastrarIngredienteEmEstoque_exception_previamenteCadastrado() {
        try {
            armazem.cadastrarIngredienteEmEstoque(ingredienteMorango);
            armazem.cadastrarIngredienteEmEstoque(ingredienteMorango);
        } catch(Throwable e) {
            assertEquals("Ingrediente já cadastrado.", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @DisplayName("Descadastro de ingrediente - Success")
    @Test
    void testDescadastrarIngredienteEmEstoque_properly() {
        armazem.descadastrarIngredienteEmEstoque(ingredienteAbacate);
        assertFalse(armazem.getEstoque().containsKey(ingredienteAbacate));
    }

    @DisplayName("Cadastro de ingrediente - Exception: Ingrediente não cadastrado (descadastrado previamente)")
    @Test
    void testDescadastrarIngredienteEmEstoque_exception_buscarIngredienteDescadastrado() {
        try {
            armazem.descadastrarIngredienteEmEstoque(ingredienteAbacate);
            armazem.consultarQuantidadeDoIngredienteEmEstoque(ingredienteAbacate);
        } catch(Throwable e) {
            assertEquals("Ingrediente não cadastrado.", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @DisplayName("Cadastro de ingrediente - Exception: Ingrediente não cadastrado")
    @Test
    void testDescadastrarIngredienteEmEstoque_exception_descadastrarIngredienteInexistente() {
        try {
            armazem.descadastrarIngredienteEmEstoque(ingredienteMorango);
        } catch(Throwable e) {
            assertEquals("Ingrediente não encontrado.", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @DisplayName("Adicionar quantidade de ingrediente - Success")
    @ParameterizedTest
    @CsvSource(value = {"1, 9", "3, 11"})
    void testAdicionarQuantidadeDoIngredienteEmEstoque_properly(int quantidadeAdicionar, int Total) {
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingredienteBanana, quantidadeAdicionar);
        assertEquals(Total, armazem.getEstoque().get(ingredienteBanana));
    }

    @DisplayName("Adicionar quantidade de ingrediente - Exception: quantidade inválida")
    @Test
    void testAdicionarQuantidadeDoIngredienteEmEstoque_exception_quantidadeInvalida() {
        try {
            armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingredienteAbacate, -10);
        } catch (Throwable e) {
            assertEquals("Quantidade inválida.", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @DisplayName("Adicionar quantidade de ingrediente - Exception: ingrediente não encontrado")
    @Test
    void testAdicionarQuantidadeDoIngredienteEmEstoque_exception_ingredienteInexistente() {
        try {
            armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingredienteMorango, 10);
        } catch (Throwable e) {
            assertEquals("Ingrediente não encontrado.", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @DisplayName("Reduzir quantidade de ingrediente - Success")
    @ParameterizedTest
    @CsvSource(value = {"1, 7", "3, 5"})
    void testReduzirQuantidadeDoIngredienteEmEstoque_properly(int quantidadeReduzir, int Total) {
        armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingredienteBanana, quantidadeReduzir);
        assertEquals(Total, armazem.getEstoque().get(ingredienteBanana));
    }

    @DisplayName("Reduzir quantidade de ingrediente - Exception: quantidade superior ao disponível no estoque")
    @Test
    void testReduzirQuantidadeDoIngredienteEmEstoque_exception_quantidadeInvalida_superiorADisponivel() {
        armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingredienteBanana, 15);
        assertFalse(armazem.getEstoque().containsKey(ingredienteBanana));
    }

    @DisplayName("Reduzir quantidade de ingrediente - Exception: quantidade inválida (inferior a 0)")
    @Test
    void testReduzirQuantidadeDoIngredienteEmEstoque_exception_quantidadeInvalida_menorQueZero() {
        try {
            armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingredienteBanana, -5);
        } catch (Throwable e) {
            assertEquals("Quantidade inválida.", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @DisplayName("Reduzir quantidade de ingrediente - Exception: ingrediente não encontrado")
    @Test
    void testReduzirQuantidadeDoIngredienteEmEstoque_exception_ingredienteNaoEncontrado() {
        try {
            armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingredienteMorango, 15);
        } catch (Throwable e) {
            assertEquals("Ingrediente não encontrado.", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @DisplayName("Consultar quantidade de ingrediente em estoque - Success")
    @Test
    void testConsultarQuantidadeDoIngredienteEmEstoque_properly() {
        armazem.cadastrarIngredienteEmEstoque(ingredienteMorango);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingredienteMorango, 8);
        armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingredienteMorango, 2);
        assertTrue(armazem.getEstoque().containsKey(ingredienteMorango));
        assertEquals(6, armazem.consultarQuantidadeDoIngredienteEmEstoque(ingredienteMorango));
    }

    @DisplayName("Consultar quantidade de ingrediente em estoque - Exception: ingrediente não cadastrado")
    @Test
    void testConsultarQuantidadeDoIngredienteEmEstoque_exception_ingredienteNaoCadastrado() {
        try {
            armazem.consultarQuantidadeDoIngredienteEmEstoque(ingredienteMorango);
        } catch (Throwable e) {
            assertEquals("Ingrediente não cadastrado.", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }
}