use crate::zork::functional_zork::GAME_ELEMENTS;
use crate::zork::game_elements::{Command, Location};

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
        let mut items = self.location.get_items();
        arguments.iter()
            .filter(|item_name| {
                if items
                    .contains(item_name) {
                    true
                } else {
                    println!("Cannot pickup {}", item_name);
                    false
                }
            })
            .for_each(|item_name| {
                &self.items.push(item_name.into());
                let mut location_items = items.clone();
                let position = location_items.iter().position(|i| *i == item_name).unwrap();
                location_items.swap_remove(position);
                println!("Picking up {}", item_name);
            });
        true
    }

    pub fn drop_command(&mut self, command: &Command) -> bool {
        let arguments = command.get_arguments();
        if arguments.is_empty() {
            println!("Drop what?");
            false
        } else {
            let mut dropped_item = false;
            for item_name in arguments {
                dropped_item = self.items.iter().position(|i| i.eq(item_name))
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
                let mut gm = GAME_ELEMENTS.lock().unwrap();
                let location_name = gm.current_location.get_location(direction)
                    .map(|s| s.to_string());
                println!("{}", location_name
                    .map(|name| {
                        // let mut gm = GAME_ELEMENTS.lock().unwrap();
                        let new_location = gm.locations.get(&name).unwrap();
                        self.location = new_location.clone();
                        gm.current_location = new_location.clone();
                        gm.display_view(
                            &gm.current_location,
                        );
                        return "";
                    })
                    .or(Some(format!("However, you can't go {}\n", direction).as_str())).unwrap()
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
