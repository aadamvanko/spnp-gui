//package cz.muni.fi.spnp.gui.storing.savers;
//
//import cz.muni.fi.spnp.gui.storing.oldmodels.ProjectOldFormat;
//import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
//import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
//
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//
//public class OldFileSaver {
//
//    private final ProjectConverter projectConverter;
//
//    public OldFileSaver() {
//        this.projectConverter = new ProjectConverter();
//    }
//
//    public void saveProject(String filePath, ProjectViewModel project) {
//        writeProject(filePath, project);
//        saveSubmodels(filePath, project);
//    }
//
//    private void writeProject(String filePath, ProjectViewModel project) {
//        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of(filePath))) {
//            var oldProject = projectConverter.convert(project);
//            writeProjectInfo(oldProject, bufferedWriter);
//            writeSubmodelsNames(oldProject, bufferedWriter);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void writeSubmodelsNames(ProjectOldFormat oldProject, BufferedWriter bufferedWriter) {
//        for (int i = 0; i < oldProject.submodelsNames.size(); i++) {
//            write(bufferedWriter, System.lineSeparator());
//            writePair(bufferedWriter, "Submodel", oldProject.submodelsNames.get(i));
//            writePair(bufferedWriter, "Type", "SRN Model");
//        }
//    }
//
//    private void saveSubmodels(String filePath, ProjectViewModel project) {
//        for (var diagram : project.getDiagrams()) {
////            var path = new Path()
//        }
//    }
//
//    private void saveSubmodel(DiagramViewModel diagramViewModel, BufferedWriter bufferedWriter) {
//
//    }
//
//    private void writeProjectInfo(ProjectOldFormat project, BufferedWriter bufferedWriter) {
//        writePair(bufferedWriter, "Model Name", project.modelName);
//        writePair(bufferedWriter, "Owner", project.owner);
//        writePair(bufferedWriter, "Date Created", project.dateCreated);
//        writeBlock(bufferedWriter, "Comment", project.comment, "CommentEnd");
//    }
//
//    private void writeBlock(BufferedWriter bufferedWriter, String start, String content, String end) {
//        writePair(bufferedWriter, start, content);
//        write(bufferedWriter, end);
//    }
//
//    private void writeBlockExclusive(BufferedWriter bufferedWriter, String start, String content, String end) {
//        writePair(bufferedWriter, start, "");
//        write(bufferedWriter, content);
//        write(bufferedWriter, end);
//    }
//
//    private void writePair(BufferedWriter bufferedWriter, String key, String value) {
//        write(bufferedWriter, String.format("%s: %s%n", key, value));
//    }
//
//    private void write(BufferedWriter bufferedWriter, String text) {
//        try {
//            bufferedWriter.write(String.format("%s%n", text));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
