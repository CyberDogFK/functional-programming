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

    pub fn execute_command(&mut self) {
        self.commands.iter().for_each(|it| {
            let mut a = it.lock().unwrap();
            a();
        });
        self.commands.clear();
    }
}
