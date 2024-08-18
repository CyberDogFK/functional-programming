pub struct FunctionalCommands {
    commands: Vec<Box<fn() -> bool>>
}

impl FunctionalCommands {
    pub fn new() -> Self {
        Self {
            commands: vec![],
        }
    }
    
    pub fn add_command(&mut self, command: fn() -> bool) {
        self.commands.push(Box::new(command))
    }
    
    pub fn execute_command(&mut self) {
        self.commands.iter().for_each(|it| { it(); });
        self.commands.clear();
    }
}