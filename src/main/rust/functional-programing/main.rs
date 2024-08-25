use zorklib::zork::FunctionalZork;

fn main() {
    let mut command = "".to_string();
    let mut command_iter;
    let mut binding = FunctionalZork::new();
    // let mut game = binding.lock().unwrap();
    while !"quit".eq_ignore_ascii_case(&command) {
        print!(">> ");
        command_iter = binding.lock().unwrap().get_command_iter();
        binding.lock().unwrap().parse_command_iter(command_iter.into_iter());
        command = FunctionalZork::execute_command(binding.clone());
    }
}
