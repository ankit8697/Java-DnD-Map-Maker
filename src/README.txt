--D&D Combat Organization Tool--
By Daniel Kleber, Danny Maya, Ankit Sanghi and Ginnie White

CODE FUNCTION:
This code will create an editable grid that you can use to make D&D combat maps.

HOW TO RUN THE CODE:
Run the view class in order to get the window to pop up.

HOW TO USE THE MAP:
You can add creatures or terrains to the map by clicking the add button and selecting which type you'd like to add.
After filling out the form that pops up with your desired information (note, for display purposes it is best if your
creature display name is 1 letter max) and then choosing a height (for creatures only),
your new creature/terrain will be added into the appropriate dropdown on the side. All terrains will additionally
appear in the key with their name. From there you can use the apply/move/delete buttons to interact with the board.
You can also highlight and select tiles in various ways from the sidebar. Finally, you can also shift or set the height
of the map from the sidebar and everything on the map will adjust to reflect the change.

OVERVIEW OF CODE STRUCTURE:
We've implemented both the model view controller design pattern and the strategy design pattern. Our View class creates
the visible map by calling on the Creature/TerrainCreatorView and CreatureLocationPopup. View uses the Controller class to have
workable buttons that use the logic from our Model class. The Model class creates a virtual 3d map using the MapModel class,
made up of Cube objects from the Cube class. Both Cube and MapModel use the Terrain and Creature classes to know which
objects they have in their space. The Cube additionally knows about the Tile class so it knows what to display.

KNOWN BUGS AND ISSUES:
At this time we do not know of any bugs keeping the code from working as desired.


