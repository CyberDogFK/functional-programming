use crate::zork::functional_zork::CommandSupplier;

pub struct FunctionalCommands<'a> {
    commands: Vec<CommandSupplier<'a>>
}

impl FunctionalCommands<'_> {
    pub fn new() -> Self {
        Self {
            commands: vec![],
        }
    }

    pub fn add_command(&mut self, command: CommandSupplier) {
        self.commands.push(command)
    }

    pub fn execute_command(&mut self) {
        self.commands.iter().for_each(|it| { it(); });
        self.commands.clear();
    }
}
