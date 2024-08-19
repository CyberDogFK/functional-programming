use std::collections::HashMap;
use crate::zork::character::Character;
use crate::zork::functional_commands::FunctionalCommands;
use crate::zork::game_elements::Command;

pub struct FunctionalZork<'a> {
    // scanner: Scanner
    character: Character,
    fc: FunctionalCommands,
    commands: HashMap<String, CommandSupplier<'a>>,
    command: Command,
}

type CommandSupplier<'a> = Box<dyn FnMut() -> bool + 'a>;

impl FunctionalZork<'_> {
    fn drop_command(&mut self) -> CommandSupplier {
        Box::new(|| self.character.drop(&self.command))
    }
    
    fn pickup_command(&mut self) -> CommandSupplier {
        Box::new(|| self.character.pickup(&self.command))
    }
    
    fn walk_command(&mut self) -> CommandSupplier {
        Box::new(|| self.character.walk(&self.command))
    }
    
    fn inventory_command(&mut self) -> CommandSupplier {
        Box::new(|| self.character.inventory(&self.command))
    }
    
    fn look_command(&mut self) -> CommandSupplier {
        Box::new(|| {
            GameElements.displayView(GameElements.currentLocation);
            true
        })
    }
    
    fn directions_command(&mut self) -> CommandSupplier {
        Box::new(|| {
            GameElements.currentLocation.displayPaths();
            true
        })
    }
    
    fn quit_command(&mut self) -> CommandSupplier {
        Box::new(|| {
            println!("Thank you for playing!");
            true
        })
    
    }
    
    pub fn new() -> Self {
        let mut zork = FunctionalZork {
            // scanner: Scanner
            character: Character::from_location(GameElements.currentLocation),
            fc: FunctionalCommands::new(),
            commands: HashMap::new(),
            command: Command::new(),
        };
        zork.initialize_commands();
        zork
    }
    
    fn initialize_commands(&mut self) {
        let mut commands = &mut self.commands;
        commands.insert("drop".to_string(), self.drop_command());
        
    }
}