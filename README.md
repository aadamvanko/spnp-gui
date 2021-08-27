# SPNP GUI

This is a replacement for the old GUI tool for modelling of the Petri Nets with extensions as defined by the spnp
package. The old GUI is not working properly and was built with very old java as well as GUI library. This new
open-source application will offer same capabilities with better user experience and possibilities for further
development by anyone interested in this project. This is currently work in progress and all sources as well as all
needed repositories will be made public.

### Current state

WIP (Work In Progress), about 80% finished

### Main features

- free and open-source for everyone
- built using modern GUI library JavaFX
- built using modern Java
- can load files in old format used by old GUI (saving is supported as well)
- two state create buttons (left click - one time operation, right click - select operation)
- zooming
- panning

### Supported keyboard and mouse operations

- Ctrl + A for selecting all elements in the diagram
- Ctrl + C for copying selected elements
- Ctrl + X for cutting selected elements
- Ctrl + V for pasting selected elements
- right click context menu
- right click panning with mouse
- zooming Ctrl + MouseWheel

### How to run

This application depends on another project https://github.com/10ondr/spnp-model (currently private), which contains
model and transformation code for converting the model to the CSPL (C source code runnable by spnp package). Will be
fixed in the near future, you can try .jar found
here https://github.com/aadamvanko/spnp-gui/releases/tag/v0.1-pre-release-showcase for now.

Old GUI:
![Alt text](screenshots/old_gui.png?raw=true "Old GUI")

This GUI:
![Alt text](screenshots/new_gui.png?raw=true "This GUI")