# June Engine

June Engine is a lightweight and modular Java game engine designed to help developers create 2D games with ease. Built with simplicity and flexibility in mind, June Engine provides the core tools needed to develop games while allowing for customization and scalability.

## Features

- **Rendering**: Efficient 2D and 3D rendering capabilities.
- **Physics**: Integrated physics engine for realistic movement and collisions.
- **Input Handling**: Simplified input system for keyboard, mouse, and controllers.
- **Audio**: Support for sound effects and background music.
- **Modularity**: Component-based architecture for easy extension and customization.
- **Platform Support**: Cross-platform compatibility with support for Windows, macOS, and Linux.

## Getting Started

### Prerequisites

Before using June Engine, ensure you have the following installed:

- Java Development Kit (JDK) 17 or later
- A modern IDE such as IntelliJ IDEA or Eclipse
- Gradle (optional, for building the project)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/NathanColbath/June-Engine.git
   cd June-Engine
   ```

2. Import the project into your IDE.
3. Build the project using your IDE's build tools or Gradle.

### Running the Demo

1. Navigate to the `demo` directory.
2. Run the demo application to see the engine in action:

   ```bash
   java -jar demo/JuneEngineDemo.jar
   ```

## Usage

### Setting Up a Project

1. Create a new Java project.
2. Add the June Engine library to your project's dependencies.
3. Start building your game by creating a main game loop and using the engine's features:

   ```java
   import june.core.Core;

   public class Main {
       public static void main(String[] args) {
         Core core = new Core();
         core.initCore();
         core.run();
       }
   }
   ```

### Using the June Editor

The June Editor is a powerful tool bundled with June Engine to help developers create and manage game assets, levels, and settings visually.

1. Launch the June Editor:

   ```bash
   java -jar editor/JuneEditor.jar
   ```

2. Key features of the June Editor:
   - **Scene Editor**: Design levels and scenes with a drag-and-drop interface.
   - **Asset Management**: Import, organize, and manage textures, models, and other assets.
   - **Physics Setup**: Configure colliders and physics properties for game objects.
   - **Game Settings**: Adjust global settings like resolution, controls, and audio preferences.

3. Save your progress and export scenes directly for use in your project.

Refer to the `docs/editor-guide.md` file for a detailed tutorial on using the June Editor.


### Documentation

Comprehensive documentation is available in the `docs` directory. It covers:

- API reference
- Tutorials
- Best practices

## Contributing

Contributions are welcome! If you'd like to contribute:

1. Fork the repository.
2. Create a feature branch:
   ```bash
   git checkout -b feature/my-feature
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add my feature"
   ```
4. Push to your branch:
   ```bash
   git push origin feature/my-feature
   ```
5. Open a pull request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## Contact

For questions or feedback, feel free to open an issue or contact the maintainer:

- **Name**: Nathan Colbath
- **Email**: colbathnathanschool@gmail.com
- **GitHub**: [NathanColbath](https://github.com/NathanColbath)

---

## Screenshots
![Screenshot 2024-11-20 220758](https://github.com/user-attachments/assets/a0db13bc-88cf-4f7b-be9a-747a040aa6f5)

![Screenshot 2024-12-12 175751](https://github.com/user-attachments/assets/cdb0c084-8a51-4b6c-86fb-5fe012a89fad)

![Screenshot 2024-12-12 175807](https://github.com/user-attachments/assets/27594d1a-4392-4a31-ba61-146e6bf666e0)

![Screenshot 2024-12-12 175817](https://github.com/user-attachments/assets/8b0b4e92-89be-4c22-8f79-4b0a7e53679b)



Thank you for using June Engine! We look forward to seeing what you create.

