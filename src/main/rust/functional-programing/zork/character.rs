use crate::zork::game_elements::{Command, GameElements, Location};

pub struct Character {
    items: Vec<String>,
    location: Location,
}

impl Character {
    pub fn from_location(location: Location) -> Self {
        Self {
            items: vec![],
            location,
        }
    }

    // todo: change bool to Result
    pub fn pickup(&mut self, command: &Command) -> bool {
        let arguments = command.get_arguments();
        arguments.iter()
            .filter(|item_name| {
                if self.location.get_items()
                    .contains(item_name) {
                    true
                } else {
                    println!("Cannot pickup {}", item_name);
                    false
                }
            })
            .for_each(|item_name| {
                self.items.push(item_name.into());
                let location_items = self.location.get_items_mut();
                let position = location_items.iter().position(|i| *i.eq(item_name)).unwrap();
                location_items.swap_remove(position);
                println!("Picking up {}", item_name);
            });
        true
    }
    
    pub fn drop(&mut self, command: &Command) -> bool {
        let arguments = command.get_arguments();
        if arguments.is_empty() {
            println!("Drop what?");
            false
        } else {
            let mut dropped_item = false;
            for item_name in arguments {
                dropped_item = self.items.iter().position(|i| *i.eq(item_name))
                    .map(|p| {
                        self.items.swap_remove(p)
                    })
                    .is_some();
                if dropped_item {
                    self.location.add_item(item_name);
                    println!("Dropping {}", item_name);
                } else {
                    println!("Cannot drop {}", item_name)
                }
            }
            dropped_item
        }
    }
    
    pub fn walk(&mut self, command: &Command) -> bool{
        let directions = command.get_arguments();
        if directions.is_empty() {
            println!("Go where?");
            false
        } else {
            directions.iter().for_each(|direction| {
                let location_name: Option<String> = 
                    GameElements::current_location.get_location(direction);
                println!("{}", location_name
                    .map(|name| {
                        let new_location: Location = GameElements::locations.get(name);
                        self.location = new_location;
                        GameElements::current_location = new_location;
                        GameElements::display_view(
                            GameElements::current_location,
                        );
                        return "";
                    })
                    .or_else("However, you can't go " + direction + "\n")
                );
            });
            true
        }
    }
    
    pub fn inventory(&self, command: &Command) -> bool {
        let arguments = command.get_arguments();
        if self.items.is_empty() {
            println!("You are holding nothing");
        } else {
            println!("You are holding:");
            self.items.iter().for_each(|item| {
                println!(" {}", item);
            });
            println!();
        }
        true
    }
}
