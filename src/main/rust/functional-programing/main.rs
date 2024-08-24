use zorklib::zork::FunctionalZork;

fn main() {
    let mut command = "";
    let mut command_iter;
    let game = FunctionalZork::new();
    while "quit".eq_ignore_ascii_case(command) {
        print!(">> ");
        command_iter = game.get_command_iter();
        game.parse_command_iter(command_iter);
        command = game.execute_command();
    }
}
