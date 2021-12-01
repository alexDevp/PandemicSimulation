//Autor: Alexandre Pereira
//Numero: 17678
package pt.ipbeja.estig.po2.pandemic.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class GuiStart extends Application {

    /**
     * Dá start ao programa e recebe um ficheiro caso seja chamado com argumentos a partir de um ficheiro jar
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
    Application.Parameters parameters = getParameters();

    List<String> param = parameters.getRaw();
    ContagiousBoard board = null;

    if(param.size()>0){
        System.out.println(param);
        File file = new File(param.get(0));

        board = new ContagiousBoard(primaryStage, file);
    }else {

        board = new ContagiousBoard(primaryStage, null);
    }

        Scene scene = new Scene(board);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Pandemic Simulator");
        primaryStage.setOnCloseRequest((e) -> {
            System.exit(0);
        });
        primaryStage.show();
    }

    public static void main(String args) {
        Application.launch(args);
    }
}

//45 Mins - Clonar o repositorio/ Inserir projeto/ Fazer Push
//10 Mins - Adicionar mais classes
//30 Mins - Reler Enunciado
//60 Mins - Restruturando o programa
//150 Mins - Ler de Novo o Enunciado e acrescentar código
//60 Mins - Tentativa de colocar uma grelha com EmptyCells e algumas persons
//180 Mins - Reconstruindo as Classes e métodos e Inicio de preencher a "grelha"
//45 Mins - Correçoes nas classes e Inicio de preencher grelha com sucesso
//30 Mins - Grelha populada com sucesso
//480 Mins - Grelha em movimento com condições
//90 Mins - Testes de Movimento, verificação de valores incorretos
//30 Mins - Verificar se foi infetado, infetar Cell
//180 Mins - Menus e Parametros de numero de Pessoas
//180 Mins - Movimento e mudança de cor dos retangulos (parte gráfica)
//180 Mins - Correção de requisitos prévios e cura das Cells
//30 Mins - Relatório
//30 Mins - Contrução de uma estrutura do que é necessário adicionar
//240 Mins - Implementação do Load File no programa
//30 Mins - Implementação do Save Game no programa
//180 Mins - Implementação de Novas Classes para as "SickPerson" e "ImmunePerson" e implementação delas no código / Alterações nos files para ficar com as novas classes
//120 Mins - Implementação da inserção dos parametros ao iniciar o programa e modificação do programa de acordo com os novos parametros
//180 Mins - Correções ao longo do projeto e escrita de comentários/Documentação / Ficheiro.jar criado e implementado
//30 Mins - Relatório