use std::fmt::{Display, Formatter};
use std::collections::HashMap;

pub struct GameElements {
    location: HashMap<String, Location>,
    items: HashMap<String, Item>,
    npcs: HashMap<String, NPC>,
    commands: HashMap<String, Box<fn() -> bool>>,
    current_location: Location,
}

impl GameElements {
    pub fn display_view(&self, location: &Location) {
        println!("{}", location.get_description());
        self.current_location.display_items();
        self.current_location.display_npcs();
    }
}

pub struct Location {
    name: String,
    description: String,
    items: Vec<String>,
    npcs: Vec<String>,
    directions: HashMap<String, Direction>
}

impl Location {
    pub fn name(mut self, name: &str) -> Self {
        self.name = name.into();
        self
    }

    pub fn get_name(&self) -> &str {
        &self.name
    }

    pub fn get_location(&self, direction: &str) -> Option<&str> {
        self.directions.get(direction)
            .map(|d| d.location.as_str())
    }

    pub fn description(mut self, description: &str) -> Self {
        self.description = description.into();
        self
    }

    pub fn get_description(&self) -> &str {
        &self.description
    }

    pub fn get_items(&self) -> &[String] {
        &self.items
    }
    
    pub fn get_items_mut(&mut self) -> &mut Vec<String> {
        &mut self.items
    }

    pub fn add_item(&mut self, item: &str) {
        self.items.push(item.to_string())
    }

    pub fn display_items(&self) {
        if self.items.is_empty() {

        } else {
            println!("On the ground you see:");
            self.items.iter()
                .for_each(|item| print!(" {}", item));
            println!()
        }
    }

    pub fn add_npc(&mut self, npc: &str) {
        self.npcs.push(npc.into())
    }

    pub fn get_npcs(&self) -> &[String] {
        &self.npcs
    }

    pub fn display_npcs(&self) {
        if self.npcs.is_empty() {

        } else {
            self.npcs.iter()
                .for_each(|npc| print!(" {}", npc));
        }
    }

    pub fn add_direction(&mut self, direction: Direction) {
        self.directions.insert(direction.get_direction().into(), direction);
    }
    
    pub fn add_direction_str(&mut self, direction: &str, location: &str) {
        let new_direction = Direction::new_from(direction.into(), location.into());
        self.directions.insert(
            direction.into(),
            new_direction
        );
    }
    
    pub fn display_paths(&self) {
        self.directions.iter().for_each(|(way, direction)| {
            print!("if you go {}", way);
            println!(" you can get to {}", direction.get_location());
        })
    }
}

pub struct Command {
    arguments: Vec<String>,
    command: String,
}

impl Command {
    pub fn new() -> Self {
        Self {
            arguments: vec![],
            command: "".into(),
        }
    }

    pub fn arguments(mut self, arguments: &[String]) -> Self {
        self.arguments = arguments.into_vec();
        self
    }

    pub fn get_arguments(&self) -> &[String] {
        &self.arguments
    }

    pub fn add_argument(mut self, argument: String) -> Self {
        self.arguments.push(argument);
        self
    }

    pub fn command(mut self, command: String) -> Self {
        self.command = command;
        self
    }

    pub fn get_command(&self) -> &str {
        &self.command
    }

    pub fn set_command(mut self, command: String) {
        self.command = command;
    }

    pub fn clear(mut self) -> Self {
        self.arguments.clear();
        self.command = "".into();
        self
    }
}

pub struct Direction {
    direction: String,
    location: String,
}

impl Direction {
    pub fn new() -> Self {
        Self {
            direction: "".into(),
            location: "".into(),
        }
    }
    
    pub fn new_from(direction: String, location: String) -> Self {
        Self {
            direction,
            location
        }
    }

    pub fn direction(mut self, direction: &str) -> Self {
        self.direction = direction.into();
        self
    }

    pub fn get_direction(&self) -> &str {
        &self.direction
    }

    pub fn location(mut self, location: &str) -> Self {
        self.location = location.into();
        self
    }

    pub fn get_location(&self) -> &str {
        &self.location
    }
}

impl Display for Direction {
    fn fmt(&self, f: &mut Formatter<'_>) -> std::fmt::Result {
        write!(f, "Direction: {}, Location: {}", self.direction, self.location)
    }
}

pub struct Item {
    name: String,
    description: String,
}

impl Item {
    pub fn name(mut self, name: &str) -> Self {
        self.name = name.into();
        self
    }

    pub fn get_name(&self) -> &str {
        &self.name
    }

    pub fn description(mut self, description: &str) -> Self {
        self.description = description.into();
        self
    }

    pub fn get_description(&self) -> &str {
        &self.description
    }
}

impl Display for Item {
    fn fmt(&self, f: &mut Formatter<'_>) -> std::fmt::Result {
        write!(f, "Name: {}, Description: {}", self.name, self.description)
    }
}

pub struct NPC {
    name: String,
    description: String,
}

impl NPC {
    pub fn name(mut self, name: &str) -> Self {
        self.name = name.into();
        self
    }

    pub fn get_name(&self) -> &str {
        &self.name
    }

    pub fn description(mut self, description: &str) -> Self {
        self.description = description.into();
        self
    }

    pub fn get_description(&self) -> &str {
        &self.description
    }
}

impl Display for NPC {
    fn fmt(&self, f: &mut Formatter<'_>) -> std::fmt::Result {
        write!(f, "Name: {}, Description: {}", self.name, self.description)
    }
}
