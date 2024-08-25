use std::cell::{Cell, RefCell};
use std::collections::HashMap;
use std::fs::File;
use std::io;
use std::io::{BufRead, BufReader};
use std::rc::Rc;
use std::sync::{Arc, LazyLock, Mutex};
use regex::{Regex, Split};
use crate::zork::character::Character;
use crate::zork::functional_commands::FunctionalCommands;
use crate::zork::game_elements::{Command, Direction, Item, Location, NPC};

pub static GAME_ELEMENTS: LazyLock<Mutex<GameElements>> = LazyLock::new(|| Mutex::new(GameElements::new()));

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
    character: Arc<Mutex<Character>>,
    fc: FunctionalCommands<'a>,
    commands: HashMap<String, CommandSupplier<'a>>,
    command: Command,
}

pub type CommandSupplier<'a> = Arc<Mutex<dyn FnMut() -> bool + Sync + Send + 'a>>;

impl FunctionalZork<'_> {
    fn drop_command(zork: Arc<Mutex<FunctionalZork>>) -> CommandSupplier {
        Arc::new(Mutex::new(move || {
            let mut zork = zork.lock().unwrap();
            let mut char = zork.character.lock().unwrap();
            char.drop_command(&zork.command)
        }))
    }

    fn pickup_command(zork: Arc<Mutex<FunctionalZork>>) -> CommandSupplier {
        Arc::new(Mutex::new(move || {
            let mut zork = zork.lock().unwrap();
            let mut char = zork.character.lock().unwrap();
            char.pickup(&zork.command)
        }))
    }

    fn walk_command(zork: Arc<Mutex<FunctionalZork>>) -> CommandSupplier {
        Arc::new(Mutex::new(move || {
            let mut zork = zork.lock().unwrap();
            let mut char = zork.character.lock().unwrap();
            char.walk(&zork.command)
        }))
    }

    fn inventory_command(zork: Arc<Mutex<FunctionalZork>>) -> CommandSupplier {
        Arc::new(Mutex::new(move || {
            let mut zork = zork.lock().unwrap();
            let mut char = zork.character.lock().unwrap();
            char.inventory(&zork.command)
        }))
    }

    fn look_command() -> CommandSupplier<'static> {
        Arc::new(Mutex::new(|| {
            let gm = GAME_ELEMENTS.lock().unwrap();
            gm.display_view(&gm.current_location);
            true
        }))
    }

    fn directions_command() -> CommandSupplier<'static> {
        Arc::new(Mutex::new(|| {
            let gm = GAME_ELEMENTS.lock().unwrap();
            gm.current_location.display_paths();
            true
        }))
    }

    fn quit_command() -> CommandSupplier<'static> {
        Arc::new(Mutex::new(|| {
            println!("Thank you for playing!");
            true
        }))
    }

    pub fn new() -> Arc<Mutex<Self>> {
        let gm = GAME_ELEMENTS.lock().unwrap();
        FunctionalZork {
            // scanner: Scanner
            character: Arc::new(Mutex::new(Character::from_location(gm.current_location.clone()))),
            fc: FunctionalCommands::new(),
            commands: HashMap::new(),
            command: Command::new(),
        }.initialize_commands()
    }

    pub fn initialize_commands(self) -> Arc<Mutex<Self>> {
        // let mut commands = &mut self.commands;
        let s = Arc::new(Mutex::new(self));
        let binding = s.clone();
        let mut ss = binding.lock().unwrap();
        // ss.commands.insert("drop".to_string(), s.borrow().drop_command().clone());
        ss.commands.insert("drop".to_string(), FunctionalZork::drop_command(s.clone()));
        ss.commands.insert("Drop".to_string(), FunctionalZork::drop_command(s.clone()));
        ss.commands.insert("pickup".to_string(), FunctionalZork::pickup_command(s.clone()));
        ss.commands.insert("Pickup".to_string(), FunctionalZork::pickup_command(s.clone()));
        ss.commands.insert("walk".to_string(), FunctionalZork::walk_command(s.clone()));
        ss.commands.insert("go".to_string(), FunctionalZork::walk_command(s.clone()));
        ss.commands.insert("inventory".to_string(), FunctionalZork::inventory_command(s.clone()));
        ss.commands.insert("inv".to_string(), FunctionalZork::inventory_command(s.clone()));
        ss.commands.insert("look".to_string(), FunctionalZork::look_command());
        ss.commands.insert("directions".to_string(), FunctionalZork::directions_command());
        ss.commands.insert("dir".to_string(), FunctionalZork::directions_command());
        ss.commands.insert("quit".to_string(), FunctionalZork::quit_command());
        s
    }

    pub fn initialize_game(mut self) {
        println!("Welcome to Functional Zork!\n");
        let file = File::open("data.txt")
            .unwrap();
        let mut buf = String::new();
        let mut br = BufferedReader::new(file, buf);
        let mut line = String::new();
        br.read_line();

        while "Location".eq_ignore_ascii_case(&line) {
            let mut location = Location::new()
                .name(br.read_line())
                .name(br.read_line())
                .description(br.read_line());
            line = br.read_line();
            while "Direction".eq_ignore_ascii_case(&line) {
                // Add direction
                location.add_direction(
                    Direction::new()
                        .direction(br.read_line())
                        .location(br.read_line())
                );
                line = br.read_line();
            }
            while "Item".eq_ignore_ascii_case(&line) {
                // Add items
                let item = Item::new()
                    .name(br.read_line())
                    .description(br.read_line());
                location.add_item(item.get_name());
                let mut gm = GAME_ELEMENTS.lock().unwrap();
                gm.items.insert(item.get_name().to_string(), item);
                line = br.read_line();
            }
            while "NPC".eq_ignore_ascii_case(&line) {
                // Add NPC
                let npc = NPC::new()
                    .name(br.read_line())
                    .description(br.read_line());
                location.add_npc(npc.get_name());
                let mut gm = GAME_ELEMENTS.lock().unwrap();
                gm.npcs.insert(npc.get_name().to_string(), npc);
                line = br.read_line();
            }
            let mut gm = GAME_ELEMENTS.lock().unwrap();
            gm.locations.insert(location.get_name().to_string(), location);
        }
        if "StartingLocation".eq_ignore_ascii_case(&line) {
            let mut gm = GAME_ELEMENTS.lock().unwrap();
            gm.current_location = gm.locations.get(&br.read_line()).unwrap().clone(); // are we really need to clone?
            gm.display_view(&gm.current_location)
        } else {
            println!("Missing Starting Location!");
        }
        self.initialize_commands();
    }

    pub fn get_command_iter(&self) -> Vec<String> {
        let mut input = String::new();
        io::stdin().read_line(&mut input).unwrap();

        let to_remove = ["an", "an", "the", "and"];
        let re = Regex::new("\\s+")
            .unwrap();
        re.split(&input)
            .filter(|s| !to_remove.contains(s))
            .map(|s| s.to_string())
            .collect()
    }

    pub fn parse_command_iter<T>(&mut self, tokens: T)
    where
        T: Iterator<Item=String>
    {
        self.command.clear();
        tokens.map(|token| {
            if self.commands.contains_key(&token) {
                self.command.set_command(token.clone());
            } else {
                self.command.add_argument(token.clone());
            }
            token
        })
            .all(|token| {
                !"quit".eq_ignore_ascii_case(&token)
            });
    }

    pub fn execute_command(zork: Arc<Mutex<Self>>) -> String {
        let mut binding = zork.clone();
        let mut zork = binding.lock().unwrap();
        let command = zork.command.get_command();
        let x = match binding.lock().unwrap().commands.get(command).clone() {
            Some(next_command) => {
                zork.fc.add_command(next_command.clone());
                zork.fc.execute_command();
                zork.command.get_command().to_string()
            }
            None => {
                println!("Unknown command");
                "".to_string()
            }
        }; x


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
    pub fn new() -> Self {
        GameElements {
            locations: HashMap::new(),
            items: HashMap::new(),
            npcs: HashMap::new(),
            commands: HashMap::new(),
            current_location: Location::new()
        }
    }

    pub fn display_view(&self, location: &Location) {
        println!("{}", location.get_description());
        self.current_location.display_items();
        self.current_location.display_npcs();
    }
}

struct BufferedReader {
    buf: String,
    br: BufReader<File>
}

impl BufferedReader {
    fn new(file: File, buf: String) -> Self {
        BufferedReader {
            buf,
            br: BufReader::new(file)
        }
    }

    fn read_line(&mut self) -> String {
        self.br.read_line(&mut self.buf).unwrap();
        self.buf.clone()
    }

    fn read_line_with_buf(br: &mut BufReader<File>, buf: &mut String) -> String {
        br.read_line(buf).unwrap();
        buf.clone()
    }
}
