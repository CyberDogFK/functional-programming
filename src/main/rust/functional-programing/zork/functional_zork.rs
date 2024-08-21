use std::cell::RefCell;
use std::collections::HashMap;
use std::pin::Pin;
use std::rc::Rc;
use std::sync::{LazyLock, Mutex, Once};
use crate::zork::character::Character;
use crate::zork::functional_commands::FunctionalCommands;
use crate::zork::game_elements::{Command, Item, Location, NPC};

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

    pub fn new() -> Self {
        FunctionalZork {
            // scanner: Scanner
            character: Character::from_location(GAME_ELEMENTS.current_location.clone()),
            fc: FunctionalCommands::new(),
            commands: HashMap::new(),
            command: Command::new(),
        }.initialize_commands()
    }

    fn initialize_commands(mut self) -> Self {
        // let mut commands = &mut self.commands;
        let s = Rc::new(RefCell::new(self));
        let mut ss = s.borrow_mut();
        ss.commands.insert("drop".to_string(), s.borrow().drop_command().clone());

        self
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
