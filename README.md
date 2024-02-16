# Media Player
### Team Members: Arden Yan, Davel Randindra, Thang Nguyen

### Contributions
In the development of the project proposal, presentation, and code, all of our team members - Arden Yan, Davel Radindra, and Thang Nguyen - worked collaboratively, ensuring a harmonious and integrated approach. Each member brought their unique strengths and perspectives to the table, resulting in a well-rounded and cohesive proposal. Similarly, for the presentation, we united our skills to create a compelling and informative showcase of our project, with each member actively participating in the design, content, and delivery of the presentation. Regarding the development of the code, there were not many divisions of labor as **all of us tried to implement features one by one collectively**, filling out the gaps of other team members. However, there are some things that each team member focused on more in the code development process.

#### Code Contributions
- Arden Yan: Created the base layouts for the file select and media player fxml, implemented the logic for the file selection window, and contributed to the functionality of playback controls.
- Davel Radindra: Integrated subtitle support and assisted in adding functionalities of playback controls.
- Thang Nguyen: Improved button functionalities. Executed the design and implementation of a graphical user interface.

#### Presentation Contributions [(Presentation Slides)](diagram/presentation-slides.pdf)
- Arden Yan: Class diagrams, sequence diagram, Scene Builder, project demo, questions
- Davel Randindra: Project approach, use case diagram, state diagram, testing, challenges, subtitles
- Thang Nguyen: Project introduction, UI/UX design approach, controlling the application during demo

#### Project Proposal Contributions
- Arden Yan: assumptions, operations, solution
- Davel Randindra: Approach and functionalities
- Thang Nguyen: Described the problem statements and set up files on Github



### Problem/Issue
Nowadays, multimedia consumption has been an integral part of a lot of people’s lives. However, there are a lot of media players that might be too complex for some and lacks certain tools for advanced users. Our goal is to create a media player that includes additional features to provide a more user-friendly and versatile experience.

### Assumptions / Operating Environments / Intended Usage 
- Users understand basic media player functionalities such as play, pause, volume, and playback operations.
- Users understand what a .srt file is.
- Users will have the matching .srt file and .mp4 file. If they do not have the matching files, the subtitles will display the incorrect text.
- Operating environment: Windows, Mac OS X, Linux
- Targets a wide range of users

### Diagrams
- [Class Diagrams](diagram/class-diagram.png)
- [Use Case Diagram](diagram/usecase-diagram.png)
- [State Diagram](diagram/state-diagram.png)
- [Sequence Diagram](diagram/sequence-diagram.png)


### Operations
- Choose media (.mp4) file
- Choose subtitle (.srt) file
- Play, pause, and replay button
- Volume slider and mute/unmute
- Seek forward or backward using video progress bar
- Skip forward or backwards 10 seconds
- Enter and exit fullscreen mode
- Adjust playback speed
- Closed captioning
- Return to file selection button

### Solution
We intend to develop a Java-based media player utilizing JavaFX and cross-platform compatibility of Java. Our approach includes: 
1. Creating the basic outline of the file selection and media player using FXML and Scene Builder.
1. Building a bar in the media player that houses play, pause, replay, playback speed, skip, fullscreen buttons, volume slider, exit, and video progress slider.
1. Designing the outline with user-centric JavaFX GUI features and styling.
1. Implementing functionality for the FXML files and buttons using JavaFX by creating controllers for the respective FXML files.
1. Binding the current time of the video to the video progress slider and the current time text
1. Adding support for subtitles using a parser which extracts subtitle text and their timings from a .srt file.
1. Using Java listeners to display the subtitles during their timing windows.

### Steps to Run Code
1. Have a .mp4 file and the corresponding .srt file ready
1. Run the application using MediaPlayerApp.java
1. You are taken to the file select window
1. Choose the .mp4 file you want to watch
1. Choose the corresponding .srt file for the selected .mp4 file (You can ignore this if you do not want subtitles or do not have a .srt file)
1. Click the watch video button to move to the media player window
1. You can use any of the media player’s playback controls as desired
1. When done using the media player, you click the exit button which will take you back to the file select window
1. Repeat steps 4-8 to watch another video

### Snippet of Working Program
[Link to YouTube video of our demo](https://youtu.be/f-wv9uhuLlU)
