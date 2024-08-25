use crate::zork::functional_zork::CommandSupplier;
use crate::zork::game_elements::Command;

pub struct FunctionalCommands<'a> {
    commands: Vec<CommandSupplier<'a>>
}

impl <'a> FunctionalCommands<'a> {
    pub fn new() -> Self {
        Self {
            commands: vec![],
        }
    }

    pub fn add_command(&mut self, command: CommandSupplier<'a>) {
        self.commands.push(command)
    }

    pub fn get_commands(&self) -> Vec<CommandSupplier<'a>> {
        self.commands.iter()
            .map(|c| c.clone())
            .collect()
    }
    
    pub fn clear_commands(&mut self) {
        self.commands.clear()
    }
    
    pub fn execute_command(&mut self) {
        self.commands.iter().for_each(|it| {
            let mut a = it.lock().unwrap();
            a();
        });
        self.commands.clear();
    }
}
