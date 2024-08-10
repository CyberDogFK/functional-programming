use chrono::Local;

fn main() {
    let now = Local::now();
    let string = now.to_rfc3339();
    println!("{}", string)
}
