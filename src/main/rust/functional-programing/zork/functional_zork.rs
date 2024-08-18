use std::collections::HashMap;
use crate::zork::character::Character;
use crate::zork::functional_commands::FunctionalCommands;
use crate::zork::game_elements::Command;

pub struct FunctionalZork {
    // scanner: Scanner
    character: Character,
    fc: FunctionalCommands,
    commands: HashMap<String, CommandSupplier>,
    command: Command,
}

type CommandSupplier = Box<dyn FnMut() -> bool>;

impl FunctionalZork {
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
        
    }
}