# SPNP GUI

This is a replacement for the old GUI tool for modelling of the Petri Nets with extensions as defined by the spnp
package. The old GUI is not working properly and was built with very old java as well as GUI library. This new
open-source application offers the same capabilities with better user experience and possibilities for further
development by anyone interested in this project.

### Main features

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

### Supported keyboard and mouse operations

- Ctrl + A for selecting all elements in the diagram
- Ctrl + C for copying selected elements
- Ctrl + X for cutting selected elements
- Ctrl + V for pasting selected elements
- Delete for deleting selected elements
- right click context menu
- right click panning with the mouse
- zooming via Ctrl + MouseWheel

### How to run

This application depends on another project https://github.com/aadamvanko/spnp-core, which contains
model and transformation code for converting the model to the CSPL (C source code runnable by spnp package). Showcase release can be found here https://github.com/aadamvanko/spnp-gui/releases/tag/v0.1-pre-release-showcase.

Old GUI:
![Alt text](screenshots/old_gui.png?raw=true "Old GUI")

This GUI:
![Alt text](screenshots/new_gui.png?raw=true "This GUI")
