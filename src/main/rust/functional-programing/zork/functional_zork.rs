use std::cell::RefCell;
use std::collections::HashMap;
use std::fs::File;
use std::io::{BufRead, BufReader, Read};
use std::path::Path;
use std::pin::Pin;
use std::rc::Rc;
use std::sync::{LazyLock, Mutex, Once};
use crate::zork::character::Character;
use crate::zork::functional_commands::FunctionalCommands;
use crate::zork::game_elements::{Command, Direction, Item, Location, NPC};

static GAME_ELEMENTS: LazyLock<GameElements<'static>> = LazyLock::new(|| GameElements::new());

fn main() {
    mod another_crate {
        pub fn great_question() -> String { "42".to_string() }
    }
    // n.b. static items do not call [`Drop`] on program termination, so this won't be deallocated.
    // this is fine, as the OS can deallocate the terminated program faster than we can free memory
    // but tools like valgrind might report "memory leaks" as it isn't obvious this is intentional.
    static DEEP_THOUGHT: LazyLock<String> = LazyLock::new(|| {
        // M3 Ultra takes about 16 million years in --release config
        another_crate::great_question()
    });

    // The `String` is built, stored in the `LazyLock`, and returned as `&String`.
    let _ = &*DEEP_THOUGHT;
    // The `String` is retrieved from the `LazyLock` and returned as `&String`.
    let _ = &*DEEP_THOUGHT;
}


pub struct FunctionalZork<'a> {
    // scanner: Scanner
    character: Character,
    fc: FunctionalCommands,
    commands: HashMap<String, CommandSupplier<'a>>,
    command: Command,
}

type CommandSupplier<'a> = Rc<RefCell<dyn FnMut() -> bool + 'a>>;

impl FunctionalZork<'_> {
    fn drop_command(&mut self) -> CommandSupplier {
        Rc::new(RefCell::new(|| self.character.drop(&self.command)))
    }

    fn pickup_command(&mut self) -> CommandSupplier {
        Rc::new(RefCell::new(|| self.character.pickup(&self.command)))
    }

    fn walk_command(&mut self) -> CommandSupplier {
        Rc::new(RefCell::new(|| self.character.walk(&self.command)))
    }

    fn inventory_command(&mut self) -> CommandSupplier {
        Rc::new(RefCell::new(|| self.character.inventory(&self.command)))
    }

    fn look_command(&mut self) -> CommandSupplier {
        Rc::new(RefCell::new(|| {
            GAME_ELEMENTS.display_view(&GAME_ELEMENTS.current_location);
            true
        }))
    }

    fn directions_command(&mut self) -> CommandSupplier {
        Rc::new(RefCell::new(|| {
            GAME_ELEMENTS.current_location.display_paths();
            true
        }))
    }

    fn quit_command(&mut self) -> CommandSupplier {
        Rc::new(RefCell::new(|| {
            println!("Thank you for playing!");
            true
        }))
    }

    pub fn new() -> Rc<RefCell<Self>> {
        FunctionalZork {
            // scanner: Scanner
            character: Character::from_location(GAME_ELEMENTS.current_location.clone()),
            fc: FunctionalCommands::new(),
            commands: HashMap::new(),
            command: Command::new(),
        }.initialize_commands()
    }

    fn initialize_commands(mut self) -> Rc<RefCell<Self>> {
        // let mut commands = &mut self.commands;
        let s = Rc::new(RefCell::new(self));
        let mut ss = s.borrow_mut();
        // ss.commands.insert("drop".to_string(), s.borrow().drop_command().clone());
        ss.commands.insert("drop".to_string(), ss.drop_command());
        ss.commands.insert("Drop".to_string(), ss.drop_command());
        ss.commands.insert("pickup".to_string(), ss.pickup_command());
        ss.commands.insert("Pickup".to_string(), ss.pickup_command());
        ss.commands.insert("walk".to_string(), ss.walk_command());
        ss.commands.insert("go".to_string(), ss.walk_command());
        ss.commands.insert("inventory".to_string(), ss.inventory_command());
        ss.commands.insert("inv".to_string(), ss.inventory_command());
        ss.commands.insert("look".to_string(), ss.look_command());
        ss.commands.insert("directions".to_string(), ss.directions_command());
        ss.commands.insert("dir".to_string(), ss.directions_command());
        ss.commands.insert("quit".to_string(), ss.quit_command());
        s
    }
}

fn initialize_game() {
    println!("Welcome to Functional Zork!\n");
    let file = File::open("data.txt")
        .unwrap();
    let mut br = BufReader::new(file);
    let mut line = String::new();
    let mut buf = String::new();
    br.read_line(&mut line).unwrap();

    while ("Direction".eq_ignore_ascii_case(&line)) {
        let mut location = Location::new()
            .name({ // todo: make a function from it
                br.read_line(&mut buf).unwrap();
                buf.as_ref()
            })
            .description({ // todo: think, maybe pass it by value???
                br.read_line(&mut buf).unwrap();
                buf.as_ref()
            });
        br.read_line(&mut line).unwrap();
        while ("Direction".eq_ignore_ascii_case(&line)) {
            // Add direction
            location.add_direction(
                Direction::new()
                    .direction({
                        br.read_line(&mut buf).unwrap();
                        buf.as_ref()
                    })
            )
            br.read_line(&mut line).unwrap();
        }
    }

}

pub struct GameElements<'a> {
    pub locations: HashMap<String, Location>,
    pub items: HashMap<String, Item>,
    pub npcs: HashMap<String, NPC>,
    pub commands: HashMap<String, CommandSupplier<'a>>,
    pub current_location: Location
}

impl GameElements<'_> {
    fn new() -> Self {
        GameElements {
            locations: HashMap::new(),
            items: HashMap::new(),
            npcs: HashMap::new(),
            commands: HashMap::new(),
            current_location: Location::new()
        }
    }

    fn display_view(&self, location: &Location) {
        println!("{}", location.get_description());
        self.current_location.display_items();
        self.current_location.display_npcs();
    }
}
