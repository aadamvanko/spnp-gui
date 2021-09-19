package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.models.PetriNet;
import cz.muni.fi.spnp.core.transformators.spnp.SPNPTransformator;
import cz.muni.fi.spnp.gui.components.menu.view.UIWindowComponent;
import cz.muni.fi.spnp.gui.mappers.DiagramMapper;
import cz.muni.fi.spnp.gui.mappers.MappingException;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ViewModelCopyFactory;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class CodePreviewView extends UIWindowComponent {

    private static final String OUTPUTS_TEMP_FOLDER = "outputs_temp";
    private static final String DIAGRAM_CSPL_TEMP_FILE = "diagram_temp_cspl";
    private static final String RUN_SPNP_SCRIPT_FILE = "run_spnp_script";

    private final Model model;
    private final DiagramViewModel diagramViewModel;
    private TextArea textArea;
    private Button buttonRun;

    public CodePreviewView(Model model, DiagramViewModel diagramViewModel) {
        this.model = model;
        this.diagramViewModel = diagramViewModel;

        createView();
    }

    private void createView() {
        var topPane = new VBox(new Label("Code:"));
        topPane.setPadding(new Insets(5));

        textArea = new TextArea();
        var centerPane = new VBox(textArea);
        centerPane.setPadding(new Insets(5));
        HBox.setHgrow(textArea, Priority.ALWAYS);
        VBox.setVgrow(textArea, Priority.ALWAYS);

        var buttonGenerateCode = new Button("Generate code");
        buttonGenerateCode.setOnAction(this::onButtonGenerateCodeHandler);

        var buttonSaveCode = new Button("Export code");
        buttonSaveCode.setOnAction(this::onButtonSaveCodeHandler);

        buttonRun = new Button("Run");
        buttonRun.setOnAction(this::onButtonRunHandler);

        var paneSpacer = new Pane();
        HBox.setHgrow(paneSpacer, Priority.ALWAYS);

        var buttonClose = new Button("Close");
        buttonClose.setOnAction(this::onButtonCloseHandler);

        var buttonsPane = new HBox(buttonGenerateCode, buttonSaveCode, buttonRun, paneSpacer, buttonClose);
        buttonsPane.setPadding(new Insets(5));
        buttonsPane.setSpacing(5);

        var borderPane = new BorderPane();
        borderPane.setTop(topPane);
        borderPane.setCenter(centerPane);
        borderPane.setBottom(buttonsPane);

        var scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Code preview");
        stage.setWidth(1000);
        stage.setHeight(600);

        generateCode();
    }

    private void onButtonGenerateCodeHandler(ActionEvent actionEvent) {
        generateCode();
    }

    private void generateCode() {
        var acFinal = diagramViewModel.getFunctionByName("ac_final");
        var viewModelCopyFactory = new ViewModelCopyFactory();
        var acFinalCopy = viewModelCopyFactory.createCopy(acFinal);
        var outputOptionsResult = new OutputOptionsResult(findViableLoopTimeVariableName(diagramViewModel));
        model.getOutputOptions().forEach(outputOption -> {
            outputOption.addToResult(outputOptionsResult, diagramViewModel);
            outputOptionsResult.getLines().add("");
        });
        var outputOptionsCode = String.join(System.lineSeparator(), outputOptionsResult.getLines());
        acFinal.bodyProperty().set(loopVariableDefinition(outputOptionsResult) + System.lineSeparator()
                + acFinal.getBody() + System.lineSeparator() + outputOptionsCode);

        diagramViewModel.getFunctions().addAll(outputOptionsResult.getFunctions());

        var diagramMapper = new DiagramMapper(model, diagramViewModel);
        PetriNet petriNet = null;
        try {
            petriNet = diagramMapper.createPetriNet();
        } catch (MappingException e) {
            var errorMessage = String.format("Cannot generate CSPL source code because there is at least 1 error:%nElement: %s%nError: %s", e.getElementName(), e.getMessage().split(":")[1]);
            textArea.setText(errorMessage);
            textArea.setStyle("-fx-text-fill: red;");
            buttonRun.setDisable(true);
            return;
        }
        var spnpCode = diagramMapper.createSPNPCode();
        var spnpOptions = diagramMapper.createSPNPOptions();
        var transformator = new SPNPTransformator(spnpCode, spnpOptions);
        var sourceCode = transformator.transform(petriNet);
        textArea.setStyle("-fx-text-fill: black;");
        textArea.setText(sourceCode);
        buttonRun.setDisable(false);

        acFinal.bodyProperty().set(acFinalCopy.getBody());
        diagramViewModel.getFunctions().removeAll(outputOptionsResult.getFunctions());
    }

    private String loopVariableDefinition(OutputOptionsResult outputOptionsResult) {
        return String.format("double %s;", outputOptionsResult.getLoopTimeVariableName());
    }

    private String findViableLoopTimeVariableName(DiagramViewModel diagramViewModel) {
        var name = "loop_time";
        var id = 0;
        var variableName = name;
        while (codeContainsName(diagramViewModel, name)) {
            variableName = name + id;
        }
        return variableName;
    }

    private boolean codeContainsName(DiagramViewModel diagramViewModel, String variableName) {
        if (diagramViewModel.getVariableByName(variableName) != null) {
            return true;
        }

        if (diagramViewModel.getDefineByName(variableName) != null) {
            return true;
        }

        if (diagramViewModel.getInputParameterByName(variableName) != null) {
            return true;
        }

        if (diagramViewModel.getFunctionByName(variableName) != null) {
            return true;
        }

        var acFinal = diagramViewModel.getFunctionByName("ac_final");
        return acFinal.getBody().contains(variableName);
    }

    private void onButtonSaveCodeHandler(ActionEvent actionEvent) {
        var fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Source Code Files", "*.c"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File selectedFile = fileChooser.showSaveDialog(stage);
        if (selectedFile != null) {
            var sourceCode = textArea.getText();
            try {
                Files.writeString(selectedFile.getAbsoluteFile().toPath(), sourceCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onButtonRunHandler(ActionEvent actionEvent) {
        System.out.println("running");
        prepareOutputsTempFolder();
        prepareCSPLFile();
        prepareRunSPNPScript();
        try {
            buttonRun.setDisable(true);
            buttonRun.setText("Running...");
            var result = Processes.run("cmd.exe", "/c", RUN_SPNP_SCRIPT_FILE);
            model.outputContentProperty().set("");
            model.outputContentProperty().set(result);
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prepareOutputsTempFolder() {
        new File(OUTPUTS_TEMP_FOLDER).mkdirs();
    }

    private void prepareCSPLFile() {
        try {
            Files.writeString(Path.of(OUTPUTS_TEMP_FOLDER, String.format("%s.c", DIAGRAM_CSPL_TEMP_FILE)), textArea.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prepareRunSPNPScript() {
        var lines = new ArrayList<String>();
        lines.add(String.format("SET SPNP_DIRECTORY=%s", model.getPathSPNP()));
        lines.add("PATH=%SPNP_DIRECTORY%\\bin;%PATH%");
        lines.add(String.format("cd %s", OUTPUTS_TEMP_FOLDER));
        lines.add(String.format("spnp %s", DIAGRAM_CSPL_TEMP_FILE));
        var scriptCode = String.join(System.lineSeparator(), lines);

        var runSPNPScriptFilename = String.format("%s.bat", RUN_SPNP_SCRIPT_FILE);
        try {
            Files.writeString(Path.of(runSPNPScriptFilename), scriptCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onButtonCloseHandler(ActionEvent actionEvent) {
        stage.close();
    }

}
