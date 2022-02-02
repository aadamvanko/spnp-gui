# SPNP GUIv2

This is a replacement for the old GUI tool for modelling of the Petri Nets with the extensions as defined by the SPNP
package. The old GUI is aged and has usability issues. This new open-source application offers the same basic
functionality with the better user experience and possibilities for further development by anyone interested in this
project. This application is the output of my Master's
thesis (https://is.muni.cz/th/trvza/?fakulta=1451;lang=en;id=239803), which covers the whole development process in
detail.

### Main Features

- free and open-source for everyone
- built using modern GUI library JavaFX
- built using modern Java
- can load files in old format used by old GUI
- two state create buttons (left click - create one element, right click - create multiple elements)
- run simulation or analysis with the specified options
- zooming
- panning
- real time editing in the properties panel (no right clicking needed)
- smooth graphical editor
- CSPL code preview
- diagram screenshots

### Supported Keyboard and Mouse Operations

- Ctrl + A for selecting all elements in the diagram
- Ctrl + C for copying selected elements
- Ctrl + X for cutting selected elements
- Ctrl + V for pasting selected elements
- Delete for deleting selected elements
- right click context menu
- right click panning with the mouse
- zooming via Ctrl + MouseWheel
- Ctrl + left click for selection of the arc without creating the drag point

### Technologies

- Java 15 SE
- JavaFX 15
- Apache Maven

### Requirements To Run

- Java 15 SE

### Dependencies

This application depends on package **SPNP Core**, located at https://github.com/aadamvanko/spnp-core, which contains
the general SRN model representation and transformation code for converting it to the CSPL (C source code runnable by
the SPNP package).

The **SPNP package** for the model solution can be obtained from the authors at *kst@ee.duke.edu*, but it is not needed
in order to run the application.

### How To Run

#### Option A - Run JAR File

Release can be found here https://github.com/aadamvanko/spnp-gui/releases/download/v1.0/spnp-gui-1.0.jar.

```
java -jar NAME_OF_THE_FILE.jar
```

#### Option B - Build the Current Source Code

Just copy and paste into terminal (make sure to have Maven and Git installed).

##### Windows

```
rmdir /s /q spnp-core
rmdir /s /q spnp-gui
git clone https://github.com/aadamvanko/spnp-core
cd spnp-core
call mvn clean install
cd ..
git clone https://github.com/aadamvanko/spnp-gui
cd spnp-gui
call mvn clean javafx:run
```

##### Linux

```
rm -r spnp-core
rm -r spnp-gui
git clone https://github.com/aadamvanko/spnp-core
cd spnp-core
mvn clean install
cd ..
git clone https://github.com/aadamvanko/spnp-gui
cd spnp-gui
mvn clean javafx:run
```

### Screenshots

Old GUI:
![Alt text](screenshots/old_gui.png?raw=true "Old GUI")

SPNP GUIv2:
![Alt text](screenshots/new_gui.png?raw=true "This GUI")
